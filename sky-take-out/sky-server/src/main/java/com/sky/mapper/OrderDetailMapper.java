package com.sky.mapper;

import com.sky.entity.OrderDetail;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author HLxxx
 * @version 1.0
 */
@Mapper
public interface OrderDetailMapper {
    /**
     * データを一括挿入します
     * @param orderDetailList
     */
    void insertBatch(List<OrderDetail> orderDetailList);
}
