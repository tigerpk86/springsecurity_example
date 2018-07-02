package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
public class DsDevConfig {

    @Value("${db.driver}")
    private String driver;
    @Value("${db.jdbcUrl}")
    private String jdbUrl;
    @Value("${db.user}")
    private String user;
    @Value("${db.password}")
    private String password;

    @Bean
    public static PropertySourcesPlaceholderConfigurer properties() {
        PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setLocation(new ClassPathResource("db.properties"));
        return configurer;
    }


//    @Configuration
//    @Profile("dev")
//    public static class DataSourceDev {
        @Bean
        public DataSource dataSource() {
            ComboPooledDataSource ds = new ComboPooledDataSource();
            try {
                ds.setDriverClass(driver);
            } catch (PropertyVetoException e) {
                throw new RuntimeException();
            }

            ds.setJdbcUrl(jdbUrl);
            ds.setUser(user);
            ds.setPassword(password);
            return ds;
        }

//    }

//    @Configuration
//    @Profile("real")
//    public static class DataSourceReal {
//        @Bean
//        public DataSource dataSource() {
//            ComboPooledDataSource ds = new ComboPooledDataSource();
//            try {
//                ds.setDriverClass("com.mysql.jdbc.Driver");
//            } catch (PropertyVetoException e) {
//                throw new RuntimeException();
//            }
//
//            ds.setJdbcUrl("jdbc:mysql://localhost/javaweb?serverTimezone=UTC");
//            ds.setUser("root");
//            ds.setPassword("");
//            return ds;
//        }
//    }

}


