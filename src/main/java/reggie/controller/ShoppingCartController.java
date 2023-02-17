package reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reggie.common.BaseContext;
import reggie.common.R;
import reggie.entity.ShoppingCart;
import reggie.service.ShoppingCartService;

@RestController
@RequestMapping("/shoppingCart")
@Slf4j
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @PostMapping("/add")
    public R<ShoppingCart> add(@RequestBody ShoppingCart shoppingCart){
        log.info("添加购物车:{}",shoppingCart);
        //设置用户id，指定当前菜品是哪个用户的购物车数据
        Long currentId = BaseContext.getCurrentId();
        shoppingCart.setUserId(currentId);
        //查询当前菜品或套餐是否在购物车中
        Long dishId = shoppingCart.getDishId();
        LambdaQueryWrapper<ShoppingCart>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(ShoppingCart::getUserId,currentId);

        if(dishId!=null){
            //添加到购物车的是菜品
            queryWrapper.eq(ShoppingCart::getDishId,dishId);
        }else{
            //添加到购物车的是套餐
            queryWrapper.eq(ShoppingCart::getSetmealId,shoppingCart.getSetmealId());
        }
        ShoppingCart cartServiceone = shoppingCartService.getOne(queryWrapper);

        if(cartServiceone!=null){
            //如果已经存在，在原来的数量上+1
            Integer number = cartServiceone.getNumber();
            cartServiceone.setNumber(number+1);
            shoppingCartService.updateById(cartServiceone);
        }else{
            //如果不存在，则添加到购物车，默认数量是1
          shoppingCart.setNumber(1);
          shoppingCartService.save(shoppingCart);
            cartServiceone=shoppingCart;
        }
        return R.success(cartServiceone);
    }
}
