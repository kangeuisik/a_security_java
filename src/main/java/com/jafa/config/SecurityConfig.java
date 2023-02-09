package com.jafa.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import lombok.extern.log4j.Log4j;
@Configuration
@EnableWebSecurity
@Log4j
@ComponentScan("com.jafa.security")
@EnableGlobalMethodSecurity(prePostEnabled = true,securedEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
	@Autowired
	DataSource dataSource;
	
	@Autowired
	AccessDeniedHandler accessDeniedHandler;

	@Autowired
	AuthenticationFailureHandler authenticationFailureHandler;
	
	@Autowired
	AuthenticationSuccessHandler authenticationSuccessHandler;
	
	@Autowired
	LogoutSuccessHandler logoutSuccessHandler;
	
	@Autowired
	UserDetailsService userDetailsService;
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl jdbcTokenRepository =  new JdbcTokenRepositoryImpl();
		jdbcTokenRepository.setDataSource(dataSource);
		return jdbcTokenRepository;
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
//		http.authorizeRequests()
//			.antMatchers("/member/all").access("permitAll")
//			.antMatchers("/member/mypage")
//				.access("hasAnyRole('ROLE_REGULAR_MEMBER','ROLE_ASSOCIATE_MEMBER')")
//			.antMatchers("/member/admin")
//				.access("hasAnyRole('ROLE_ADMIN','ROLE_SUB_ADMIN')"); 
        http.formLogin(); // /logig으로 요청하면 스프링시큐리티가 제공하는 로그인 페이지로 이동
        
        http.exceptionHandling()
        .accessDeniedHandler(accessDeniedHandler);
        
        //로그인
        http.formLogin()
			.loginPage("/member/login") // 로그인 페이지 , 기본값:/login GET요청
			.loginProcessingUrl("/member/login") // 로그인 처리 ,/login POST요청
			.usernameParameter("loginId") // 아이디 name값 / username
			.passwordParameter("loginPwd") // 비밀번호 name 값 /password
        	.failureHandler(authenticationFailureHandler)// 로그인 실패 핸들러
        	.successHandler(authenticationSuccessHandler);
        
		// 데이터소스를 직접 추가하는 방식이 아니라 
		// PersistentTokenRepository사용
		http.rememberMe()
			.key("kang") // 인증받은 사용자의 정보로 token을 생성하는데 사용되는 임의의 key값 설정 
			.tokenRepository(persistentTokenRepository())
			.rememberMeParameter("remember-me") // 자동로그인 체크박스의 name속성 지정 : 기본값
			.tokenValiditySeconds(86400); // token만료시간 설정
        
        //로그아웃
        http.logout()
			.invalidateHttpSession(true) 
			.logoutUrl("/member/logout") // 로그아웃 처리 URL //기본 POST 요청
//			.logoutSuccessUrl("/") // 로그아웃 성공시 요청시 기본값이 로그인페이지로 설정되어있으나
        		//이 메소드로 인해 메인페이지로 이동하게됨
			.logoutSuccessHandler(logoutSuccessHandler)
			.deleteCookies("remember-me","JSESSION_ID");
        
	}
	
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
 //       auth.inMemoryAuthentication().withUser("admin").password("{noop}1234").roles("ADMIN","MEMBER");
  //      auth.inMemoryAuthentication().withUser("kang").password("{noop}1234").roles("MEMBER");
        
		auth.userDetailsService(userDetailsService)
			.passwordEncoder(passwordEncoder());
    }
    
    
}

