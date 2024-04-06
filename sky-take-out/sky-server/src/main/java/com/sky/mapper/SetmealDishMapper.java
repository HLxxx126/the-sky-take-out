package com.sky.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author HLxxx
 * @version 1.0
 */
@Mapper
public interface SetmealDishMapper {
    /**
     * メニューのIdに基づいて関連のセットメニューのIdを検索する
     * @param dishIds
     * @return
     */
    List<Long> getSetmealIdsByDishId(List<Long> dishIds);
}
