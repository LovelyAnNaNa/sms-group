package com.whtt.smsgroup.entity.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.whtt.smsgroup.base.MenuBase;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("sms_menu")
public class SmsMenu extends MenuBase<SmsMenu> {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父菜单
     */
    private Integer parentId;

    /**
     * 菜单层级
     */
    private Long level;

    /**
     * 父菜单联集
     */
    private String parentIds;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 链接地址
     */
    private String href;

    /**
     * 打开方式
     */
    private String target;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 显示背景色
     */
    private String bgColor;

    /**
     * 是否显示
     */
    private Integer isShow;

    /**
     * 权限标识
     */
    private String permission;

    /**
     * 创建人
     */
    private Integer createId;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 修改人
     */
    private Integer updateId;

    /**
     * 修改时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 备注
     */
    private String remarks;


    public static final String ID = "id";

    public static final String NAME = "name";

    public static final String PARENT_ID = "parent_id";

    public static final String LEVEL = "level";

    public static final String PARENT_IDS = "parent_ids";

    public static final String SORT = "sort";

    public static final String HREF = "href";

    public static final String TARGET = "target";

    public static final String ICON = "icon";

    public static final String BG_COLOR = "bg_color";

    public static final String IS_SHOW = "is_show";

    public static final String PERMISSION = "permission";

    public static final String CREATE_ID = "create_id";

    public static final String CREATE_TIME = "create_time";

    public static final String UPDATE_ID = "update_id";

    public static final String UPDATE_TIME = "update_time";

    public static final String REMARKS = "remarks";

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
