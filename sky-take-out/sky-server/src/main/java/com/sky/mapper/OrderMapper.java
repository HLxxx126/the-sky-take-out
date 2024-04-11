package com.sky.mapper;

import com.sky.entity.Orders;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author HLxxx
 * @version 1.0
 */
@Mapper
public interface OrderMapper {
    /**
     *
     注文データを挿入します。
     * @param orders
     */
    void insert(Orders orders);
}
