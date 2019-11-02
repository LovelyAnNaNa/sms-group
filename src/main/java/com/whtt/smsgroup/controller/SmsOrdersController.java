package com.whtt.smsgroup.controller;


import com.whtt.smsgroup.entity.pojo.SmsOrders;
import com.whtt.smsgroup.service.SmsOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wbh
 * @since 2019-11-01
 */
@RestController
@RequestMapping("/smsOrders")
public class SmsOrdersController {

    @Autowired
    private SmsOrdersService ordersService;

    @ResponseBody
    @PostMapping(value = "/test")
    public Object test(@RequestBody SmsOrders orders){
        orders.validateParams();
        return ordersService.onOrder(orders);
    }

}

