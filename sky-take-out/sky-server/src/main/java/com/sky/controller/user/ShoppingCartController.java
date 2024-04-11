package com.sky.controller.user;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;
import com.sky.result.Result;
import com.sky.service.ShoppingCartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author HLxxx
 * @version 1.0
 */

@RestController
@RequestMapping("/user/shoppingCart")
@Api(tags = "ショッピングカート関連のAPI")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @PostMapping("/add")
    @ApiOperation("カートに追加")
    public Result add(@RequestBody ShoppingCartDTO shoppingCartDTO){
        log.info("カートに追加:{}",shoppingCartDTO);
        shoppingCartService.addShoppingCart(shoppingCartDTO);
        return Result.success();
    }

    /**
     * カートを表示する
     * @return
     */
    @GetMapping("/list")
    @ApiOperation("")
    public Result<List<ShoppingCart>> list(){
        log.info("カートを表示する");
        List<ShoppingCart> list =shoppingCartService.showShoppingCart();
        return Result.success(list);


    }
}

