package com.websocket.game.config;

import com.websocket.game.DAO.UserRepository;
import com.websocket.game.domain.User;
import com.websocket.game.exceptions.UserNotActiveException;
import com.websocket.game.security.AuthFailureHandler;
import com.websocket.game.security.AuthSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableRedisHttpSession
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    private static String LOGIN_PATH = "/user/login";
    private static String LOGOUT_PATH = "/user/logout";

    @Autowired
    private AuthSuccessHandler authSuccessHandler;
    @Autowired
    private AuthFailureHandler authFailureHandler;
    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .formLogin()
                .permitAll()
                .loginProcessingUrl(LOGIN_PATH)
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(authSuccessHandler)
                .failureHandler(authFailureHandler)
                .and()
                .logout()
                .permitAll()
                .logoutUrl(LOGOUT_PATH)
                .and()
                .authorizeRequests()
                .antMatchers("/user/activate").permitAll()
                .antMatchers("/user/register").permitAll()
                .antMatchers("/files/**").authenticated()
                .antMatchers("/admin**").hasRole("ADMIN")
                .antMatchers("/user").authenticated()
                .anyRequest().authenticated();

    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(new AuthenticationProvider() {

            @Override
            public boolean supports(Class<?> authentication) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
            }

            @Override
            public Authentication authenticate(Authentication authentication) throws AuthenticationException {
                UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

                String username = String.valueOf(token.getPrincipal());
                String password = String.valueOf(token.getCredentials());
                User user = userRepository.findUserByUsername(username);
                if (user == null || !user.password.equals(password)) {
                    throw new BadCredentialsException("Invalid credentials");
                }

                if(!user.active)
                    throw new UserNotActiveException("User account is inactive");

                //TODO admin/user authorities

                SimpleGrantedAuthority admin = null;
                List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
                if(username.compareTo("piotr")==0) {
                    admin = new SimpleGrantedAuthority("ROLE_ADMIN");
                    authorities.add(admin);
                }
                admin = new SimpleGrantedAuthority("ROLE_USER");
                authorities.add(admin);

                return new UsernamePasswordAuthenticationToken(token.getName(), token.getCredentials(), authorities);
            }
        });
    }
}
