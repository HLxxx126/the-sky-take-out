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
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return Result.success();
    }
    @PostMapping("/status/{status}")
    @ApiOperation("メニューの有効化/無効化")
    public Result status(Long id,@PathVariable Integer status){
        log.info("メニューの有効化/無効化");
        dishService.startOrStop(id,status);
        return Result.success();
    }

}
