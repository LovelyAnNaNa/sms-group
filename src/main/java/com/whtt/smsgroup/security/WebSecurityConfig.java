package com.whtt.smsgroup.security;

import com.alibaba.fastjson.JSON;
import com.whtt.smsgroup.common.CommonResult;
import com.whtt.smsgroup.common.ResultCode;
import com.whtt.smsgroup.util.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;

/**
 * @Auther: wbh
 * @Date: 2019/10/29 19:56
 * @Description:
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomUserDetailsService userDetailsService;
    @Autowired
    private CustomAuthenticationLoginHandler loginHandler;
    @Autowired
    private DataSource dataSource;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                //自定义登录效验
                .userDetailsService(userDetailsService)
                //配置密码加密规则
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//                .antMatchers().authenticated()
                //允许匿名访问的url
                    .antMatchers("/smsUser/registerPage","/smsUser/register","/smsUser/getCode","/smsUser/regtest").permitAll()
                    .anyRequest().authenticated()
                .and() //自定义403返回异常
                    .exceptionHandling().accessDeniedHandler(accessDeniedHandler())
                .and()
                //设置登录页
                    .formLogin().loginPage("/smsUser/login")
                //设置登录成功页和自定义用户名密码
                    .successHandler(loginHandler)
                    .failureHandler(loginHandler).permitAll()
                    .usernameParameter("userName")
                    .passwordParameter("userPassword")
                .and()
                     //验证码过滤器,登录时有需要可以加上
//                    .addFilterBefore(new VerifyFilter(), UsernamePasswordAuthenticationFilter.class)
                    .logout().permitAll()
                .and()
                    //浏览器自动登录
                    .rememberMe().tokenRepository(persistentTokenRepository())
                    //有效时间,单位S
                    .tokenValiditySeconds(60 * 60 * 24 * 7).userDetailsService(userDetailsService)
                .and()
                    .sessionManagement().invalidSessionUrl("/smsUser/past")
                    .maximumSessions(1).maxSessionsPreventsLogin(false).expiredSessionStrategy(new CustomExpiredSessionStrategy());
        //关闭CSRF跨域
        http.csrf().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        //设置拦截忽略文件夹,可以对静态资源放行
        web.ignoring().antMatchers("/static/**","/css/**","/js/**","/img/**","/myplugs/**","/images/**","/index/**","/index/**","/layui/**");
    }

    /**
     * 注入自定义权限管理
     * @return
     */
    @Bean
    public DefaultWebSecurityExpressionHandler webSecurityExpressionHandler(){
        DefaultWebSecurityExpressionHandler handler = new DefaultWebSecurityExpressionHandler();
        handler.setPermissionEvaluator(SpringContextUtils.getBean(CustomPermissionEvaluator.class));
        return handler;
    }

    //密码加密使用的Bean
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    //浏览器自动登录
    @Bean
    public PersistentTokenRepository persistentTokenRepository(){
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }

    //自定义403校验异常
    @Bean
    public AccessDeniedHandler accessDeniedHandler(){
        return new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                response.setContentType("application/json;charset=UTF-8");
                CommonResult<Object> result = CommonResult.failed(ResultCode.FORBIDDEN);
                response.getWriter().write(JSON.toJSONString(result));
            }
        };
    }

    @Bean("authenticationManagerBean")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
