package com.sky.mapper;

import com.sky.entity.DishFlavor;
import org.apache.ibatis.annotations.Mapper;

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
}
