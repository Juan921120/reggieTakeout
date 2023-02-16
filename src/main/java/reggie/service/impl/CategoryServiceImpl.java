package reggie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reggie.common.CustomException;
import reggie.entity.Category;
import reggie.entity.Dish;
import reggie.entity.Setmeal;
import reggie.mapper.CatergoryMapper;
import reggie.service.CategoryService;
import reggie.service.DishService;
import reggie.service.SetmealService;

@Service
public class CategoryServiceImpl extends ServiceImpl<CatergoryMapper, Category>implements CategoryService {
    /**
     * 根据id删除分类，删除签需要判断分类下是否有关联套餐or菜品
     * @param ids
     * @return
     */
    @Autowired
    private DishService service;
    private SetmealService setmealService;
    @Override
    public void remove(Long ids) {
        //当前分类是否已经关联菜品，抛出业务异常
        LambdaQueryWrapper<Dish> queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Dish::getCategoryId,ids);
        int count = service.count(queryWrapper);
        if(count>0){
            //已经关联菜品，抛出业务异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }
        //当前分类是否已经关联套餐，抛出业务异常
        LambdaQueryWrapper<Setmeal> queryWrapper2=new LambdaQueryWrapper<>();
        queryWrapper2.eq(Setmeal::getCategoryId,ids);
        int count2 = service.count(queryWrapper);
        if(count2>0){
            //已经关联菜品，抛出业务异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        //正常删除
        super.removeById(ids);
    }
}
