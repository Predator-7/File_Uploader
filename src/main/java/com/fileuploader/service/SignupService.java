package com.fileuploader.service;

import com.fileuploader.dto.UserDto;
import com.fileuploader.entity.User;
import com.fileuploader.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
public class SignupService {

    @Autowired
    private UserRepository userRepository;

    public User signup(UserDto userDto){

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        User user1 = userRepository.findByEmailAndPassword(user.getEmail() , user.getPassword());


        if(!Objects.isNull(user1)){
            //
            log.info("User already registered!");
        }

        userRepository.save(user);

        return user;
    }
}
