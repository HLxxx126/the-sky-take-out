package com.sky.service;

import com.sky.dto.ShoppingCartDTO;

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
}
