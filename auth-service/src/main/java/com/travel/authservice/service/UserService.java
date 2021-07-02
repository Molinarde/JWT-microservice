package com.travel.authservice.service;

import com.travel.authservice.model.User;
import com.travel.authservice.repository.UserRepository;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    public boolean addUser(User user){
        User findUser = userRepository.findByEmail(user.getEmail());
        if(findUser == null) {
            try {
                User save = userRepository.save(user);
                return true;
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
        return false;
    }

    public User findByEmailAndPassword(String email, String password){
        User byEmail = userRepository.findByEmail(email);

        if(byEmail != null){
            if(BCrypt.checkpw(password, byEmail.getPassword())){
                return byEmail;
            }
        }
        return null;
    }
}
