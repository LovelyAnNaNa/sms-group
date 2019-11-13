package com.whtt.smsgroup.security;

import com.alibaba.fastjson.JSON;
import com.whtt.smsgroup.common.CommonResult;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Auther: wbh
 * @Date: 2019/11/6 09:07
 * @Description: 处理旧用户被挤下线的逻辑
 */
public class CustomExpiredSessionStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException {
        HttpServletResponse response = event.getResponse();
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(CommonResult.failed("已经在另一台机器登录,您被迫下线: " + event.getSessionInformation().getLastRequest())));
    }
}
