package reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reggie.entity.Setmeal;
import reggie.entity.SetmealDish;
import reggie.mapper.SetMealDishMapper;
import reggie.mapper.SetmealMapper;
import reggie.service.SetMealDishService;
import reggie.service.SetmealService;

@Service
@Slf4j
public class SetmealDishServiceImpl extends ServiceImpl<SetMealDishMapper, SetmealDish>implements SetMealDishService {
}
