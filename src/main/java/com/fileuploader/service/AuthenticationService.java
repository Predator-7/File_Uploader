package com.fileuploader.service;

import com.fileuploader.dto.UserDto;
import com.fileuploader.entity.User;
import com.fileuploader.exception.InternalServerException;
import com.fileuploader.exception.InvalidParameterException;
import com.fileuploader.repository.UserRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@Log4j2
public class AuthenticationService {

    @Autowired
    private UserRepository userRepository;

    public User signup(UserDto userDto) throws InternalServerException{

        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());

        User user1 = userRepository.findByEmailAndPassword(user.getEmail() , user.getPassword());


        if(!Objects.isNull(user1)){
            log.error("User Already Registered!");
            throw new InternalServerException("User Already Registered!");
        }

        userRepository.save(user);
        log.info("User " + user.getUserName() +  " Successfully registered!");

        return user;
    }

    public User login(UserDto userDto) throws InvalidParameterException{
        User user = userRepository.findByEmailAndPassword(userDto.getEmail(), userDto.getPassword());

        if (Objects.isNull(user)) {
            log.error("User Not Registered!");
            throw new InvalidParameterException("User Not Registered!");
        }

        User users1 = new User();
        users1.setUserName(userDto.getUserName());
        users1.setId(user.getId());
        users1.setEmail(userDto.getEmail());

        log.info("User " + user.getUserName() +  " Successfully logged in!");

        return users1;
    }

    public boolean isUserValid(Long id){

        Optional<User> user = userRepository.findById(id);

        if(user.isEmpty()){
            return false;
        }

        return true;
    }





}
