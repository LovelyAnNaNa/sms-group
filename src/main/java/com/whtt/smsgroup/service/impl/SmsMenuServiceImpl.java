package com.whtt.smsgroup.service.impl;

import com.whtt.smsgroup.entity.pojo.SmsMenu;
import com.whtt.smsgroup.mapper.SmsMenuMapper;
import com.whtt.smsgroup.service.SmsMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
@Service
public class SmsMenuServiceImpl extends ServiceImpl<SmsMenuMapper, SmsMenu> implements SmsMenuService {

    @Resource
    private SmsMenuMapper menuMapper;

    @Override
    public List<String> getPermissionByRoleNames(List<String> roleNameList) {
        return menuMapper.getPermissionByRoleNames(roleNameList);
    }

    @Override
    public Collection<SmsMenu> getUserMenu(Integer userId) {
        //获取用户的菜单信息
        List<SmsMenu> userMenuList = menuMapper.getUserMenu(userId);
        //key:父菜单id , value:父菜单对象
        HashMap<Integer, SmsMenu> parentMenuMap = new HashMap<>();
        for (SmsMenu menu : userMenuList) {
            //获取父级菜单放入到map集合中
            if(menu.getLevel() != null && menu.getLevel() == 1){
                parentMenuMap.put(menu.getId(),menu);
            }else{
                Integer parentId = menu.getParentId();
                //获取父级菜单对象
                SmsMenu parentMenu = parentMenuMap.get(parentId);
                //获取父级菜单的子菜单集合
                List<SmsMenu> childrendList = parentMenu.getChildrends();
                if(childrendList == null){
                    childrendList = new LinkedList<SmsMenu>();
                    parentMenu.setChildrends(childrendList);
                }
                childrendList.add(menu);
            }
        }
        Collection<SmsMenu> userMenu = parentMenuMap.values();
        return userMenu;
    }
}
