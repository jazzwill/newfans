package com.r1123.fans.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by helloqdz on 2018/10/21.
 */

@EnableWebMvc
@Order(Ordered.HIGHEST_PRECEDENCE)
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*").allowedMethods("POST,GET,PUT,DELETE")
                .allowedHeaders("Origin, X-Requested-With, Content-Type, Accept, Connection, User-Agent, Cookie").allowCredentials(true);
    }
/*
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(detailsService());
    }*/


   /* @Override
    protected void configure(HttpSecurity http) throws Exception {
        *//*http.headers()
                .and().authorizeRequests()
                .antMatchers("/registry").permitAll()
                .anyRequest().authenticated()
                .and().formLogin().loginPage("/sign_in")
                .loginProcessingUrl("/login").defaultSuccessUrl("/personal_center",true)
                .failureUrl("/sign_in?error").permitAll()
               // .and().sessionManagement().invalidSessionUrl("/sign_in")
               // .and().rememberMe().tokenValiditySeconds(1209600)
                .and().logout().logoutSuccessUrl("/sign_in").permitAll()
                .and().csrf().disable();*//*
        http.sessionManagement()
                .and().authorizeRequests()
                .antMatchers("/login").permitAll()
                .anyRequest().authenticated()
                .and().
                formLogin().loginPage("/login")
                .failureUrl("/loginfail").successForwardUrl("/loginSuccess").and().csrf().disable();
    }*/

}
