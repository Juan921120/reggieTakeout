package reggie.common;

/**
 *  基于ThreadLoacl分装工具类 用户保存和获取当前登录id
 */
public class BaseContext {
   private static ThreadLocal<Long> threadLocal =new ThreadLocal<>();

    /**
     * 设置id
     * @param id
     */
   public static void setCurrentId(Long id){
       threadLocal.set(id);
   }

    /**
     * 获取id
     * @return
     */
   public static Long getCurrentId(){
       return threadLocal.get();
   }
}
