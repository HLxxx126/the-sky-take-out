package com.sky.service;

import com.sky.dto.DishDTO;

/**
 * @author HLxxx
 * @version 1.0
 */
public interface DishService {
    /**
     * 新しいメニューと関連の味情報を増加する
     * @param dishDTO
     */
    public void saveWithFlavor(DishDTO dishDTO);
}
