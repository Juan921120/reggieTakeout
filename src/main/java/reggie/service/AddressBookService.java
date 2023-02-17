package reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import reggie.entity.Category;

import java.io.Serializable;

public interface CategoryService extends IService<Category> {
    public void remove(Long ids);
}
