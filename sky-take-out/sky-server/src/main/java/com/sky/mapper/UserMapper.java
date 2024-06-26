package com.sky.mapper;

import com.sky.entity.User;
import com.sun.javafx.collections.MappingChange;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

/**
 * @author HLxxx
 * @version 1.0
 */
@Mapper
public interface UserMapper {

    /**
     * openidに基づいてユーザーを検索する
     * @param openid
     * @return
     */
    @Select("select * from sky_take_out.user where openid = #{openid}")
    User getByOpenid(String openid);

    /**
     * データ挿入
     * @param user
     */
    void insert(User user);

    @Select("select * from sky_take_out.user where id = #{id} ")
    User getById(Long id);

    /**
     * 根据动态条件统计用户数量
     * @param map
     * @return
     */
    Integer countByMap(Map map);
}
