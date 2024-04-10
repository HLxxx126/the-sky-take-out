package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;

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

    /**
     * Idに基づいてメニューと関連の味付けを検索する
     * @param id
     * @return
     */
    DishVO getByIdWithFlavor(Long id);

    /**
     * Idに基づいてメニューのデータと関連の味付けを修正する
     * @param dishDTO
     */
    void updateWithFlavor(DishDTO dishDTO);

    /**
     * メニューの有効化/無効化
     * @param id
     * @param status
     */
    void startOrStop(Long id, Integer status);

    /**
     * 条件查询菜品和口味
     * @param dish
     * @return
     */
    List<DishVO> listWithFlavor(Dish dish);

    /**
     * カテゴリIdに基づいてメニューを検索する
     * @param categoryId
     * @return
     */
    List<Dish> list(Long categoryId);
}

