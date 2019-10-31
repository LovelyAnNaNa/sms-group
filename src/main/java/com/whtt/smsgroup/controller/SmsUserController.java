package com.whtt.smsgroup.controller;


import com.whtt.smsgroup.common.CommonResult;
import com.whtt.smsgroup.config.Constants;
import com.whtt.smsgroup.entity.pojo.SmsUser;
import com.whtt.smsgroup.service.SmsUserService;
import com.whtt.smsgroup.sms.TencentSMS;
import com.whtt.smsgroup.util.RandomUtil;
import com.whtt.smsgroup.util.SecurityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wbh
 * @since 2019-10-31
 */
@Controller
@RequestMapping("/smsUser")
public class SmsUserController {

    @Autowired
    @Qualifier("authenticationManagerBean")
    protected AuthenticationManager authenticationManager;
    @Autowired
    private SmsUserService userService;

    //用户注册
    @ResponseBody
    @RequestMapping(value = "/regtest")
    public Object regtest(@RequestBody SmsUser newUser, HttpServletRequest request) {
        String oldPassWord = newUser.getUserPassword();
        newUser.setUserPassword(SecurityUtil.encryptPassword(oldPassWord));
        userService.save(newUser);

        return CommonResult.success();
    }


    //用户注册
    @ResponseBody
    @RequestMapping(value = "/register")
    public Object register(@RequestBody SmsUser newUser, HttpServletRequest request) {
        //校验前台传递过来的参数
        newUser.validateParams();
        HttpSession session = request.getSession();
        //获取手机验证码
        String verificationCode = session.getAttribute(Constants.PHONE_CODE).toString();
        //获取用户输入的手机验证码
        String phoneCode = newUser.getPhoneCode();
        if(!phoneCode.equals(verificationCode)){
            return CommonResult.failed("手机验证码不正确!");
        }

        String oldPassword = newUser.getUserPassword();
        //保存用户信息
        Integer result = userService.saveNormalUser(newUser);

        //如果保存成功自动登录
        if(result > 0){
            ModelAndView mav = new ModelAndView();
            try {
                System.out.println(newUser.getUserName() + "     " + newUser.getUserPassword());
                //此处应该传递原始密码!
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(newUser.getUserName(), oldPassword);
                token.setDetails(new WebAuthenticationDetails(request));
                Authentication authenticatedUser = authenticationManager.authenticate(token);
                SecurityContextHolder.getContext().setAuthentication(authenticatedUser);
                int a = 1 / 0 ;
                session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,SecurityContextHolder.getContext());
                //如果自动登录成功跳转首页
                mav.setViewName("home");
            } catch (AuthenticationException e) {
                //自动登录失败跳转登录页
                mav.setViewName("login");
            }
            return mav;
        }

        //返回注册失败信息
        return CommonResult.failed("注册失败");
    }


    /**
     * 检查用户名是否已存在
     * @return
     */
    @RequestMapping(value = "/checkNewName")
    public Object checkNewName(@NotBlank(message = "用户名不能为空!") @RequestParam String username){
        SmsUser userByName = userService.getByName(username);
        if(userByName == null){
            return CommonResult.success();
        }
        return CommonResult.failed("用户名已存在!");
    }

    //用户注册界面
    @RequestMapping(value = "/registerPage")
    public String registerPage() {
        return "register";
    }

    /**
     * 获取验证码
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCode")
    public Object getCode(@RequestParam @NotBlank(message = "手机号码不能为空") String phone,
                          HttpSession session) {
        //验证码
        String randomCode = RandomUtil.getRandmonNumber(6);
        try {
            boolean result = TencentSMS.singleSendCode(phone, Constants.TENECTNT_CODE_TEMPLATE_ID, randomCode, "注册", "", null);
            if (result) {
                //将短信验证码存入到Session中
                session.setAttribute(Constants.PHONE_CODE,randomCode);
                return CommonResult.success("验证码发送成功!");
            }
        } catch (Exception e) {

        }
        return CommonResult.failed("验证码发送失败,请稍后重试");
    }

    @RequestMapping(value = "/home")
    public String home() {
        return "home";
    }

    //登录页
    @RequestMapping(value = "/login")
    public String login() {
        return "login";
    }

    @ResponseBody
    @RequestMapping(value = "/test")
    @PreAuthorize("hasPermission('','123')")
    public Object test(){
        return CommonResult.success("成功");
    }

}

