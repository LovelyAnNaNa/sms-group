package com.whtt.smsgroup.base;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Auther: wbh
 * @Date: 2019/10/31 12:54
 * @Description:
 */
@Getter
@Setter
public class MenuBase<T> extends Model {

    @TableField(exist = false)
    private List<T> childrends;
}
