package com.sky.mapper;

import com.sky.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

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
}
