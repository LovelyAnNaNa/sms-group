package com.whtt.smsgroup.security;

import com.whtt.smsgroup.util.SpringContextUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

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
                .and()
                //设置登录页
                .formLogin().loginPage("/smsUser/login")
                //设置登录成功页
                    .successHandler(loginHandler)
                    .failureHandler(loginHandler).permitAll()
                .usernameParameter("userName")
                .passwordParameter("userPassword")
                .and()
                .logout().permitAll();
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

    @Bean("authenticationManagerBean")
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
