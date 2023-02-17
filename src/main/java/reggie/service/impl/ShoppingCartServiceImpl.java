package reggie.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import reggie.entity.Dish;
import reggie.entity.DishFlavor;
import reggie.mapper.DishFlavorMapper;
import reggie.mapper.DishMapper;
import reggie.service.DishFlavorService;
import reggie.service.DishService;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {

}
