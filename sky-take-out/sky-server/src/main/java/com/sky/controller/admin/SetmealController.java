package com.sky.controller.admin;

import com.sky.dto.SetmealDTO;
import com.sky.dto.SetmealPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.SetmealService;
import com.sky.vo.SetmealVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author HLxxx
 * @version 1.0
 */

@RestController("adminSetmealController")
@RequestMapping("/admin/setmeal")
@Api(tags = "セットメニュー関連のインターフェース")
@Slf4j
public class SetmealController {
    @Autowired
    private SetmealService setmealService;

    /**
     * 新しいセットメニューを増加する
     * @param setmealDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新しいセットメニューを増加する")
    @CacheEvict(cacheNames = "setmealCache",key ="#setmealDTO.categoryId" )
    public Result save(@RequestBody SetmealDTO setmealDTO){
        log.info("新しいセットメニューを増加する:{}",setmealDTO);
        setmealService.saveWithDish(setmealDTO);
        return Result.success();
    }

    /**
     * セットメニューのページングクエリ
     * @param setmealPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("セットメニューのページングクエリ")
    public Result<PageResult> page (SetmealPageQueryDTO setmealPageQueryDTO){
        PageResult pageResult = setmealService.pageQuery(setmealPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * すべてのセットメニューのキャッシュをクリアします
     * @param ids
     * @return
     */
    @DeleteMapping
    @ApiOperation("すべてのセットメニューのキャッシュをクリアします")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result delete(@RequestParam List<Long> ids){
        setmealService.deleteBatch(ids);
        return Result.success();
    }


    /**
     * Idに基づいてセットメニューを検索する
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation("Idに基づいてセットメニューを検索する")
    public Result<SetmealVO> getById(@PathVariable Long id){
       SetmealVO setmealVO= setmealService.getByIdWithDish(id);
       return Result.success(setmealVO);
    }

    /**
     * "セットメニューのデータを修正する"
     * @param setmealDTO
     * @return
     */
    @PutMapping
    @ApiOperation("セットメニューのデータを修正する")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result update(@RequestBody SetmealDTO setmealDTO){
        setmealService.update(setmealDTO);
        return Result.success();
    }

    /**
     * セットメニューの
     * @param status
     * @param id
     * @return
     */
    @PostMapping("/status/{status}")
    @ApiOperation("セットメニューの販売開始と停止")
    @CacheEvict(cacheNames = "setmealCache",allEntries = true)
    public Result startOrStop(@PathVariable Integer status,Long id){
        setmealService.startOrStop(status,id);
        return Result.success();
    }
}
