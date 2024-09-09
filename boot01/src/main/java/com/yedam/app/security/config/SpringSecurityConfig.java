package com.yedam.app.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SpringSecurityConfig {
	@Bean	// 비밀번호 암호화
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean	// 메모리상 인증번호 등록 => 테스트 전용
	InMemoryUserDetailsManager inMemoryUserDetailsService() {
		// UserDetails - Security가 필요한 것을 지닌 class (Security의 VO)
		UserDetails user = User.builder()
				.username("user1")
				.password(passwordEncoder().encode("1234"))
				.roles("USER") // -> ROLE_USER로 인식
				// .authorities("ROLE_USER")  -> ROLE_USER로 인식
				.build();
		
		UserDetails admin = User.builder()
				.username("admin1")
				.password(passwordEncoder().encode("1234"))
				//.roles("ADMIN") // -> ROLE_USER로 인식    앞에 ROLE_ 이 자동으로 붙음 
				 .authorities("ROLE_ADMIN") // -> ROLE_USER로 인식   작성한 문자 그대로 인식
 				.build();
		return new InMemoryUserDetailsManager(user, admin);
	}
	
	//인증 및 인가
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		// 적용될 Security 설정
		// => URI에 적용될 권한 ★
		http
			.authorizeHttpRequests(aythorize ->
				aythorize.requestMatchers("/","/all").permitAll()
				.requestMatchers("/user/**").hasRole("USER")      // hasAnyRole("USER", "ADMIN") 으로하면 두가지 자격 모두 가능
				.requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
				.anyRequest().authenticated() // 나머지 주소에 대한 설정
		)
		.formLogin(formlogin -> formlogin.defaultSuccessUrl("/all"))
		.logout(logout -> logout.logoutSuccessUrl("/all"));
		
		return http.build();
	}
}
