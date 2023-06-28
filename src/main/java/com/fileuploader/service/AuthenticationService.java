package com.fileuploader.service;

import com.fileuploader.dto.UserDto;
import com.fileuploader.entity.User;
import com.fileuploader.repository.UserRepository;
import jakarta.jws.soap.SOAPBinding;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@Log4j2
public class AuthenticationService {

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

    public User login(UserDto userDto) {
        User user = userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());

        if (Objects.isNull(user)) {
        //    throw new InvalidParameterException("User Not found!");

            log.info("User Not Registered!");
            return null;
        }

        User users1 = new User();
        users1.setUserName(userDto.getUserName());
        users1.setId(user.getId());
        users1.setEmail(userDto.getEmail());
        return users1;
    }





}
