package config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import user.bo.UserBO;

@Configuration
public class UserConfig {

    @Bean
    public UserBO userBO(){
        return new UserBO();
    }


}
