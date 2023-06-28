package com.fileuploader.repository;

import com.fileuploader.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User , Long> {

    User findByEmailAndPassword(String email , String password);


}
