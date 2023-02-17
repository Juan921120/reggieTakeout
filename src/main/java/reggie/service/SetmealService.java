package reggie.service;

import com.baomidou.mybatisplus.extension.service.IService;
import reggie.dto.SetmealDto;
import reggie.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    public void saveWithDish(SetmealDto setmealDto);
    public void  removeWithDish(List<Long> ids);

   public SetmealDto getByIdWithDish(Long id);

    void updateWithDish(SetmealDto setmealDto);
}
