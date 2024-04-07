package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author HLxxx
 * @version 1.0
 */
@Mapper
public interface DishFlavorMapper {
    /**
     * 味付けデータの一括挿入
     * @param flavors
     */
    void insertBatch(List<DishFlavor> flavors);

    /**
     * メニューのIdに基づいて関連している味付けのデータを削除する
     * @param dishId
     */
    @Delete("delete from sky_take_out.dish_flavor where dish_id = #{dishId}")
    void deleteByDishId(Long dishId);

    /**
     * メニューのIdの集合に基づいて、関連の味付けを一括で削除します。
     * @param dishIds
     */
    void deleteByDishIds(List<Long> dishIds);

    /**
     *メニューのIdに基づいて関連の味付けのデータを検索する
     * @param dishId
     * @return
     */
    @Select("select * from sky_take_out.dish_flavor where dish_id = #{dishId}")
    List<DishFlavor> getByDishId(Long dishId);
}
