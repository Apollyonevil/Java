package com.example.SPStore.user.infrastructure.config;


import com.example.SPStore.user.application.service.*;
import com.example.SPStore.user.domain.ports.in.UserFindAllInputPort;
import com.example.SPStore.user.domain.ports.in.UserFindByEmailInputPort;
import com.example.SPStore.user.domain.ports.in.UserFindByIdInputPort;
import com.example.SPStore.user.domain.ports.in.UserSaveInputPort;
import com.example.SPStore.user.domain.ports.out.IUserPersistencePort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class UserBeanConfig {


    @Bean
    public UserSaveInputPort userSaveInputPort(IUserPersistencePort userPersistencePort, BCryptPasswordEncoder passwordEncoder) {   
    return new UserSaveService(userPersistencePort, passwordEncoder);
    }

    @Bean
        public UserFindByIdInputPort userFindByIdInputPort(IUserPersistencePort userPersistencePort, BCryptPasswordEncoder passwordEncoder) {   
        return new UserFindByIdService(userPersistencePort, passwordEncoder);
    }

    @Bean
        public UserFindByEmailInputPort userFindByEmailInputPort(IUserPersistencePort userPersistencePort, BCryptPasswordEncoder passwordEncoder) {   
        return new UserFindByEmailService(userPersistencePort, passwordEncoder);
    }

    @Bean
        public UserFindAllInputPort userFindAllInputPort(IUserPersistencePort userPersistencePort, BCryptPasswordEncoder passwordEncoder) {   
        return new UserFindAllService(userPersistencePort, passwordEncoder);
    }

}