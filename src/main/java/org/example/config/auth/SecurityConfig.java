package org.example.config.auth;
import lombok.RequiredArgsConstructor;
import org.example.domain.user.Role;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;


@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeRequests()
                .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile").permitAll()
                .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                .antMatchers("/administer/instanceurl/userUploadGradeAdmin").hasRole(Role.ADMIN.name())
                .antMatchers("/administer/instanceurl/userInfo").hasRole(Role.ADMIN.name())
                .anyRequest().authenticated()

                .and()
                .formLogin()
                .loginPage("/cus-login").permitAll().and()
                .logout()
                .logoutSuccessUrl("/")
                .and()
                .oauth2Login()

                .userInfoEndpoint()
                .userService(customOAuth2UserService);

    }
}