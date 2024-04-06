package com.sky.controller.admin;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;
import com.sky.result.Result;
import com.sky.service.DishService;
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
     * @param dishDTO
     * @return
     */
    @PostMapping
    @ApiOperation("新しいメニューを増加する")
    public Result save(@RequestBody DishDTO dishDTO){
        log.info("新しいメニューを増加する:{}",dishDTO);
        dishService.saveWithFlavor(dishDTO);
        return Result.success();
    }

    /**
     * 料理のページングクエリ
     * @param dishPageQueryDTO
     * @return
     */
    @GetMapping("/page")
    @ApiOperation("料理のページングクエリ")
    public Result<PageResult> page(DishPageQueryDTO dishPageQueryDTO){
        log.info("料理のページングクエリ:{}",dishPageQueryDTO);
        PageResult pageResult = dishService.pageQuery(dishPageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * メニューの一括削除
     * @param ids
     * @return
     */
    @DeleteMapping
    public Result delete(@RequestParam List<Long> ids){
       log.info("メニューの一括削除:{}",ids);
       dishService.deleteBatch(ids);
        return Result.success();
    }
}
