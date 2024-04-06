package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.result.PageResult;

import java.util.List;

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

    /**
     * 料理のページングクエリ
     * @param dishPageQueryDTO
     * @return
     */
    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);

    /**
     * メニューの一括削除
     * @param ids
     */
    void deleteBatch(List<Long> ids);
}
