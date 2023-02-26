package reggie;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.transaction.annotation.EnableTransactionManagement;



@SpringBootApplication
@Slf4j
@ServletComponentScan//扫描使用了 @WebServlet、@WebFilter 和 @WebListener 注解的类，注册相应的 Servlet、Filter 和 Listener 到 Web 容器中
@EnableTransactionManagement
@EnableCaching//开启Spring Cache注解功能缓存
public class ReggieApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReggieApplication.class,args);
        log.info("项目启动成功 O(≧口≦)O");
    }
}
