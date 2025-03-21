//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.onlinegrocery.myapp.Controllers;

import com.onlinegrocery.myapp.exceptions.TokenGenerationException;
import com.onlinegrocery.myapp.exceptions.UserNotAuthenticatedException;
import com.onlinegrocery.myapp.exceptions.UserNotFoundException;
import com.onlinegrocery.myapp.model.User;
import com.onlinegrocery.myapp.service.EmailService;
import com.onlinegrocery.myapp.service.UserService;
import com.onlinegrocery.myapp.utils.ApiResponse;
import com.onlinegrocery.myapp.utils.ChangePasswordDTO;
import com.onlinegrocery.myapp.utils.LoginDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping({"/protected/user"})
public class UserController {
    @Autowired
    UserService userService;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public UserController() {
    }

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-email")
    public String sendEmail() {
        emailService.sendSimpleMessage("divyansh.s@tcs.com", "Test Email", "This is a test email.");
        return "Email sent successfully!";
    }

    @PostMapping({"/register"})
    public ResponseEntity<ApiResponse<User>> registerUser(@RequestBody User user) {

            user.setPassword(this.bCryptPasswordEncoder.encode(user.getPassword()));
            User registeredUser = this.userService.registerUser(user);
            ApiResponse<User> res = new ApiResponse("User registered successfully!", registeredUser, true);
            return ResponseEntity.ok(res);

    }

    @PostMapping({"/login"})
    public ResponseEntity<ApiResponse<LoginDTO>> verifyUser(@RequestBody User user, HttpServletResponse response) throws TokenGenerationException, BadCredentialsException{

            String token = this.userService.verifyCustomer(user);
            User loggedInUser = userService.findByEmailId(user.getEmail());
            LoginDTO dto = new LoginDTO();

            if(token.equals("FAIL")){
                throw new TokenGenerationException("Error generating the token!");
            }

            dto.setUser(loggedInUser);
            dto.setToken(token);
            response.setHeader("Authorization", "Bearer " + token);
            ApiResponse<LoginDTO> res = new ApiResponse("User logged in successfully!", dto, true);
            return ResponseEntity.ok(res);
    }

    @GetMapping({"/customer/{id}"})
    public ResponseEntity<ApiResponse<User>> getUserById(@PathVariable int id) throws UserNotFoundException {
            User user = this.userService.findUserById(id);
            if (user == null) {
                throw new UserNotFoundException("User data not found!");
            }
            ApiResponse<User> res = new ApiResponse("User fetched successfully!", user, true);
            return ResponseEntity.ok(res);

    }

    @PutMapping({"/{id}"})
    public ResponseEntity<ApiResponse<User>> updateUser(@RequestBody User user) throws DataIntegrityViolationException {

            User updatedUser = this.userService.updateUser(user);
            ApiResponse<User> res = new ApiResponse("User updated successfully!", updatedUser, true);
            return ResponseEntity.ok(res);

    }

    @PutMapping({"/changePassword/{id}"})
    public ResponseEntity<ApiResponse<User>> updatePassword(@RequestBody ChangePasswordDTO changePasswordDTO , @PathVariable("id") int id) throws UserNotAuthenticatedException {
        User user = userService.findUserById(id);

        User userNewPassword = this.userService.updatePassword(changePasswordDTO , user);

        ApiResponse<User> res = new ApiResponse("Password updated successfully!",userNewPassword, true);

        return ResponseEntity.ok(res);
    }
}
