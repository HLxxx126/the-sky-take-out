package com.sky.mapper;

import com.sky.entity.ShoppingCart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * @author HLxxx
 * @version 1.0
 */
@Mapper
public interface ShoppingCartMapper {

    /**
     *动的な条件検索
     * @param shoppingCart
     * @return
     */
    List<ShoppingCart> list(ShoppingCart shoppingCart);

    /**
     *Idに基づいて商品の数を変更する
     * @param shoppingCart
     */
    @Update("update sky_take_out.shopping_cart set number= #{number} where id = #{id}")
    void updateNumberById(ShoppingCart shoppingCart);

    /**
     *カートデータを挿入します。
     * @param shoppingCart
     */
    @Insert("insert into sky_take_out.shopping_cart(name, image, user_id, dish_id, setmeal_id, dish_flavor, amount, create_time) " +
            "values(#{name}, #{image}, #{userId}, #{dishId},#{ setmealId},#{dishFlavor},#{amount}, #{createTime}) ")
    void insert(ShoppingCart shoppingCart);

    /**
     * カートを空にする
     * @param userId
     */
    @Delete("delete from sky_take_out.shopping_cart where user_id = #{userId}")
    void deleteByUserId(Long userId);
}
