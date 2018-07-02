package config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.authentication.dao.ReflectionSaltSource;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.TokenBasedRememberMeServices;
import org.springframework.web.cors.*;
import org.springframework.web.filter.CorsFilter;
import security.*;
import security.loginform.RequestHeaderProcessingFilter;
import security.loginform.SignedUsernamePasswordAuthenticationProvider;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Configuration
@Import(DsDevConfig.class)
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final static String key = "parkminwan";
    @Autowired
    private DataSource dataSource;

    @Bean
    public UserDetailsService userDetailsService() {
        CustomJdbcDaoImpl customJdbcDaoImpl = new CustomJdbcDaoImpl();
        customJdbcDaoImpl.setDataSource(dataSource);
        customJdbcDaoImpl.setEnableAuthorities(false);
        customJdbcDaoImpl.setEnableGroups(true);
        customJdbcDaoImpl.setUsersByUsernameQuery(
                "select username,password,enabled,salt "
                        + "from users " + "where username = ?"
        );
        return customJdbcDaoImpl;
    }

    @Bean
    DaoAuthenticationProvider customAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService());
        daoAuthenticationProvider.setPasswordEncoder(shaPasswordEncoder());
        daoAuthenticationProvider.setSaltSource(saltSource());
        return daoAuthenticationProvider;
    }

    @Bean
    SignedUsernamePasswordAuthenticationProvider signedUsernamePasswordAuthenticationProvider(){
        SignedUsernamePasswordAuthenticationProvider signedUsernamePasswordAuthenticationProvider = new SignedUsernamePasswordAuthenticationProvider();
        signedUsernamePasswordAuthenticationProvider.setSaltSource(saltSource());
        signedUsernamePasswordAuthenticationProvider.setPasswordEncoder(shaPasswordEncoder());
        signedUsernamePasswordAuthenticationProvider.setUserDetailsService(userDetailsService());
        return signedUsernamePasswordAuthenticationProvider;
    }

//    @Bean
//    public SpringAuthenticationProvider springAuthenticationProvider() {
//        return new SpringAuthenticationProvider();
//    }



    @Bean
    ShaPasswordEncoder shaPasswordEncoder() {
        ShaPasswordEncoder shaPasswordEncoder =  new ShaPasswordEncoder();
        return shaPasswordEncoder;
    }

    @Bean
    ReflectionSaltSource saltSource() {
        ReflectionSaltSource reflectionSaltSource = new ReflectionSaltSource();
        reflectionSaltSource.setUserPropertyToUse("salt");
        return reflectionSaltSource;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.authorizeRequests()
//                SPEL의 확장된 표현식.
//                    .antMatchers("/admin/`**").hasRole("ADMIN")
//                	.antMatchers("/db/**").access("hasRole('ADMIN') and hasRole('DBA')")
//                    .antMatchers("/bc/**").access("hasRole('admin' and hasIpAddress('127.0.0.1'))")
                .antMatchers("/user/**").access("hasRole('ROLE_USER') and isFullyAuthenticated()")
                .anyRequest().authenticated()
//                .antMatchers("/*").authenticated()
                .accessDecisionManager(accessDecisionManager())
            .and()
                .formLogin().loginProcessingUrl("/login/login").loginPage("/login/loginPage")
                .usernameParameter("username").passwordParameter("password").permitAll()
            .and()
                .httpBasic()
            .and()
                .logout()
                    .invalidateHttpSession(true)
                    .logoutSuccessUrl("/")
                    .logoutUrl("/login/logout")
            .and().rememberMe().rememberMeServices(tokenBasedRememberMeServices())

            .and()
                .csrf().disable()
            .addFilterBefore(ipRoleAuthenticationFilter(), FilterSecurityInterceptor.class)
            .addFilterBefore(requestHeaderProcessingFilter(), UsernamePasswordAuthenticationFilter.class)
            .addFilter(corsFilter())
            .sessionManagement().sessionFixation().none().maximumSessions(1);

        http.exceptionHandling().accessDeniedPage("/accessDenied");

    }

//    @Bean
//    @Override
//    public AuthenticationManager authenticationManagerBean() throws Exception {
//        AuthenticationManager authenticationManager = super.authenticationManagerBean();
//        return authenticationManager;
//    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(customAuthenticationProvider())
            .authenticationProvider(signedUsernamePasswordAuthenticationProvider())
            .userDetailsService(userDetailsService());
    }


    @Bean
    RequestHeaderProcessingFilter requestHeaderProcessingFilter() throws Exception {
        RequestHeaderProcessingFilter requestHeaderProcessingFilter =
            new RequestHeaderProcessingFilter("/loginHeader");
        requestHeaderProcessingFilter.setAuthenticationManager(authenticationManagerBean());
        return requestHeaderProcessingFilter;
    }

    @Bean
    AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters
                = Arrays.asList(
                webExpressionVoter(),
                roleVoter(),
                authenticatedVoter());
        return new UnanimousBased(decisionVoters);
//        return new ConsensusBased((decisionVoters));
    }

    @Bean
    WebExpressionVoter webExpressionVoter() {
        return new WebExpressionVoter();
    }

    @Bean
    AuthenticatedVoter authenticatedVoter() {
        return new AuthenticatedVoter();
    }

    @Bean
    RoleVoter roleVoter() {
        return new RoleVoter();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedMethods(Arrays.asList(CorsConfiguration.ALL));
        configuration.setAllowedHeaders(Arrays.asList("md"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", null);
        return source;
    }

    @Bean
    CorsFilter corsFilter() {
        CorsFilter corsFilter = new CorsFilter(corsConfigurationSource());
        CustomDefaultCorsProcessor processor = new CustomDefaultCorsProcessor();
        corsFilter.setCorsProcessor(processor);
        return corsFilter;
    }


    @Bean
    TokenBasedRememberMeServices tokenBasedRememberMeServices() {
        IpTokenBasedRememberMeServices ipTokenBasedRememberMeServices= new IpTokenBasedRememberMeServices(key, userDetailsService());
        ipTokenBasedRememberMeServices.setParameter("remember_me");
        return ipTokenBasedRememberMeServices;
    }


//    @Bean
//    PersistentTokenBasedRememberMeServices persistentTokenBasedRememberMeServices() {
//        persistentTokenBasedRememberMeServices().set
//
//    }


    @Bean
    DatabasePasswordSecurerBean databasePasswordSecurerBean() {
        DatabasePasswordSecurerBean databasePasswordSecurerBean =  new DatabasePasswordSecurerBean();
        databasePasswordSecurerBean.setDataSource(dataSource);

        return databasePasswordSecurerBean;
    }

    @Bean
    IpRoleAuthenticationFilter ipRoleAuthenticationFilter() {
        IpRoleAuthenticationFilter ipRoleAuthenticationFilter = new IpRoleAuthenticationFilter();
        ipRoleAuthenticationFilter.setTargetRole("ROLE_ADMIN");
        List<String> allowedIpAddress = new ArrayList<String>();
        allowedIpAddress.add("127.0.0.1");
//        allowedIpAddress.add("1.2.3.4");
        ipRoleAuthenticationFilter.setAllowedIpAddress(allowedIpAddress);
        return ipRoleAuthenticationFilter;
    }
}
