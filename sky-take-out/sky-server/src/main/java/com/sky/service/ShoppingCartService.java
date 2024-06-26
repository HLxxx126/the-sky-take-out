package com.sky.service;

import com.sky.dto.ShoppingCartDTO;
import com.sky.entity.ShoppingCart;

import java.util.List;

/**
 * @author HLxxx
 * @version 1.0
 */
public interface ShoppingCartService {
    /**
     * カートに追加
     * @param shoppingCartDTO
     */
    void addShoppingCart(ShoppingCartDTO shoppingCartDTO);

    /**
     * カートを表示する
     * @return
     */
    List<ShoppingCart> showShoppingCart();

    /**
     * カートを空にする
     */
    void cleanShoppingCart();

    /**
     * 数量を減らす数量を減らす
     * @param shoppingCartDTO
     */
    void subShoppingCart(ShoppingCartDTO shoppingCartDTO);
}
