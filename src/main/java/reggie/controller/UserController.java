package reggie.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reggie.common.R;
import reggie.entity.User;
import reggie.service.UserService;
import reggie.utils.ValidateCodeUtils;

import javax.servlet.http.HttpSession;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 发送短信验证码
     * @param user
     * @return
     */
    @PostMapping("/sendMsg")
    public R<String > sendMsg(@RequestBody User user, HttpSession session){

        //获取手机号
        String phone=user.getPhone();
        if(StringUtils.isNotEmpty(phone)){
            //生成随机4位验证码
            String code = ValidateCodeUtils.generateValidateCode(4).toString();
            log.info("随机生成验证码是"+code);

            //调用阿里云提供短信API发送短信
           // SMSUtils.sendMessage("阿里云短信测试","SMS_154950909",phone,code);

            //将生成的验证码保存到session
            //session.setAttribute(phone,code);

            //将生成的验证码缓存到Redis中，并设置有效期为5分钟。
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);

            return R.success("手机验证码发送成功");
        }
        return  R.error("发送验证码失败");
    }

    /**
     * 登录
     * @param map
     * @param session
     * @return
     */
    @PostMapping("/login")
    public R<User> login(@RequestBody Map map, HttpSession session){
        log.info(map.toString());
        //获取手机号
        String phone = map.get("phone").toString();
        //获取验证码
        String code = map.get("code").toString();
        //获取session
        //Object codeInSession = session.getAttribute(phone);

        //从Redis中获取缓存的验证码
        Object codeInSession = redisTemplate.opsForValue().get(phone);

        //比对输入的验证码和session的验证码
        if(codeInSession!=null&&codeInSession.equals(code)){
            //比对成功，登录
            LambdaQueryWrapper<User> queryWrapper=new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getPhone,phone);
            User user = userService.getOne(queryWrapper);
            //判断当前手机号是否为新用户，如果是就自动注册
            if(user==null){
                user = new User();
                user.setPhone(phone);
                userService.save(user);
            }
            session.setAttribute("user",user.getId());
            //如果用户登录成功，删除Redis中缓存的验证码
            redisTemplate.delete(phone);
            return R.success(user);
        }

        return  R.error("登录失败");
    }
}
