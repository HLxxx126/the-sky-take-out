package com.sky.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.MessageConstant;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author HLxxx
 * @version 1.0
 */
@Service
@Slf4j
public class DishServiceImpl implements DishService {
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    /**
     * 新しいメニューと関連の味情報を増加する
     */
    @Transactional
    public void saveWithFlavor(DishDTO dishDTO) {
        Dish dish = new Dish();
        BeanUtils.copyProperties(dishDTO, dish);

        //メニューを増加する
        dishMapper.insert(dish);
        Long dishId = dish.getId();

        //味情報を増加す
        List<DishFlavor> flavors = dishDTO.getFlavors();
        if (flavors != null && flavors.size() > 0) {
            flavors.forEach(dishFlavor -> {
                dishFlavor.setDishId(dishId);
            });
            //データを挿入
            dishFlavorMapper.insertBatch(flavors);

        }

    }

    /**
     * 料理のページングクエリ
     *
     * @param dishPageQueryDTO
     * @return
     */
    @Override
    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(), dishPageQueryDTO.getPageSize());
        Page<DishVO> page = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(page.getTotal(), page.getResult());
    }

    /**
     * メニューの一括削除
     *
     * @param ids
     */
    @Override
    @Transactional
    public void deleteBatch(List<Long> ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if (dish.getStatus() == StatusConstant.ENABLE) {
                //このメニューは販売中であり、削除できません
                throw new DeletionNotAllowedException(MessageConstant.DISH_ON_SALE);
            }
        }
        //このメニューは削除出来ますかどうかを判断するーーこのメニューはセットメニューに関連していますか？
        List<Long> setmealIds = setmealDishMapper.getSetmealIdsByDishId(ids);
        if (setmealIds!=null && setmealIds.size() > 0){
            //このメニューはセットメニューに関連しています,削除出来ません
            throw  new DeletionNotAllowedException(MessageConstant.DISH_BE_RELATED_BY_SETMEAL);
        }
        //このメニューを削除する
//        for (Long id : ids) {
//            dishMapper.deleteById(id);
//            //このメニューに関連している味付けのデータを削除する
//            dishFlavorMapper.deleteByDishId(id);
//        }
        //メニューのIdの集合に基づいて、メニューデータを一括で削除します。
        dishMapper.deleteByIds(ids);

        //メニューのIdの集合に基づいて、関連の味付けを一括で削除します。
        dishFlavorMapper.deleteByDishIds(ids);



    }
}
