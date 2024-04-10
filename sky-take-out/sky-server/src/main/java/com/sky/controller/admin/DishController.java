package com.sky.controller.admin;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Set;

/**
 * @author HLxxx
 * @version 1.0
 * メニュー管理
 */

@RestController
@RequestMapping("/admin/dish")
@Api(tags = "メニュー関連のインターフェース")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    RedisTemplate redisTemplate;

    /**
     * 新しいメニューを増加する
     *
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新しいメニューを増加する")
    public Result save(@RequestBody DishDTO dishDTO) {
        log.info("新しいメニューを増加する:{}", dishDTO);
        dishService.saveWithFlavor(dishDTO);
        //キャッシュデータをクリアします
        String key = "dish_"+dishDTO.getCategoryId();
//        redisTemplate.delete(key);
        cleanCache(key);
        return Result.success();
    }

    /**
     * 料理のページングクエリ
     *
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("料理のページングクエリ")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO) {
        log.info("料理のページングクエリ:{}", dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * メニューの一括削除
     *
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids) {
        log.info("メニューの一括削除:{}", ids);
        dishService.deleteBatch(ids);
        //べてのメニューのキャッシュをクリアします
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * Idに基づいてメニューを検索する
     *
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("Idに基づいてメニューを検索する")
    public Result<DishVO> getById(@PathVariable Long id) {
        log.info("Idに基づいてメニューを検索する:{}", id);
        DishVO dishVO = dishService.getByIdWithFlavor(id);
        return Result.success(dishVO);
    }

    /**
     *
     * @param dishDTO
     * @return
     */
    @PutMapping
    @ApiOperation("メニューのデータを修正する")
    public Result update(@RequestBody DishDTO dishDTO){
        log.info("メニューのデータを修正する:{}",dishDTO);
        dishService.updateWithFlavor(dishDTO);
        //べてのメニューのキャッシュをクリアします
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * メニューの有効化/無効化
     * @param id
     * @param status
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("メニューの有効化/無効化")
    public Result status(Long id,@PathVariable Integer status){
        log.info("メニューの有効化/無効化");
        dishService.startOrStop(id,status);
        //べてのメニューのキャッシュをクリアします
//        Set keys = redisTemplate.keys("dish_*");
//        redisTemplate.delete(keys);
        cleanCache("dish_*");
        return Result.success();
    }

    /**
     * カテゴリIdに基づいてメニューを検索する
     * @param categoryId
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("カテゴリIdに基づいてメニューを検索する")
    public Result<List<Dish>> list(Long categoryId){
        List<Dish> list = dishService.list(categoryId);
        return Result.success(list);
    }

    /**
     * キャッシュをクリアします
     * @param pattern
     */
    private void cleanCache(String pattern){
        Set keys = redisTemplate.keys(pattern);
        redisTemplate.delete(keys);
    }

}
