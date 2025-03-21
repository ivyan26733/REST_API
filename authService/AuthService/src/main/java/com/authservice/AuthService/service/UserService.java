//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.authservice.AuthService.service;

import com.authservice.AuthService.model.User;
import com.authservice.AuthService.repository.UserRepository;
import com.authservice.AuthService.utils.ChangePasswordDTO;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JWTService jwtService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);
    public UserService() {
    }

    @Transactional
    public User registerUser(User user) {
        return (User)this.userRepository.save(user);
    }

    public String verifyCustomer(User user) {

        Authentication authentication = this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));

        if (authentication.isAuthenticated()) {
            // Extract the authenticated UserDetails object
            UserDetails userDetail = (UserDetails) authentication.getPrincipal();

            // Generate the token using authenticated UserDetails
            return this.jwtService.generateToken(user.getEmail(), userDetail);
        }
        return "FAIL";
    }

    public User updateUser(User user) {
        return (User)this.userRepository.save(user);
    }

    public User findUserById(int id) {

        return (User)this.userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User not found for id: " + id));
    }

    public User findByEmailId(String email){
        return userRepository.findByEmail(email).orElse(null);
    }


    public User updatePassword(ChangePasswordDTO changePasswordDTO , User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getEmail() , changePasswordDTO.getPassword()));

        if(authentication.isAuthenticated()){
            user.setPassword(bCryptPasswordEncoder.encode(changePasswordDTO.getNewPassword()));
            userRepository.save(user);
        }else{
            return null;
        }
        return user;
    }
}
