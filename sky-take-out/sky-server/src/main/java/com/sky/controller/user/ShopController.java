package com.sky.controller.user;

import com.sky.result.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.*;

/**
 * @author HLxxx
 * @version 1.0
 */
@RestController("userShopController")
@RequestMapping("/user/shop")
@Api("店舗に関連するAPI")
@Slf4j
public class ShopController {
    public static final String KEY = "SHOP_STATUS";

    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 店舗の営業状態を取る
     * @return
     */
    @GetMapping("/status")
    @ApiOperation("店舗の営業状態を取る")
    public Result<Integer> getStatus(){
       Integer shopStatus = (Integer) redisTemplate.opsForValue().get(KEY);
       log.info("店舗の営業状態は:{}",shopStatus == 1 ? "営業中":"準備中");
        return Result.success(shopStatus);
    }
}
