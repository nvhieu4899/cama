package go.nvhieucs.cama.controllers;

import go.nvhieucs.cama.models.applicationUser.ApplicationUser;
import go.nvhieucs.cama.models.applicationUser.UserRepository;
import go.nvhieucs.cama.models.applicationUser.UserServices;
import go.nvhieucs.cama.responsesModel.MessageResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServices userServices;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/sign-up")
    public ResponseEntity<ApplicationUser> createUser(@RequestBody ApplicationUser applicationUser) {
        if (userServices.existsByUsername(applicationUser.getUsername())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        } else {
            applicationUser.setPassword(bCryptPasswordEncoder.encode(applicationUser.getPassword()));
            return ResponseEntity.ok(userServices.save(applicationUser));
        }
    }

    @GetMapping("/info")
    public ApplicationUser userInfo(Authentication authentication) throws HttpClientErrorException {
        if (authentication.isAuthenticated()) {
            return userServices.findOneByUsername(authentication.getName());
        } else {
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }

    @PutMapping("/info")
    public ResponseEntity<Object> changeUserInfo(@RequestBody ApplicationUser user, Authentication authentication) {
        if (authentication.isAuthenticated()) {
            ApplicationUser applicationUser = userServices.findOneByUsername(authentication.getName());
            applicationUser.setEmail(user.getEmail());
            return ResponseEntity.ok(userServices.save(applicationUser));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("Authorization header", "Request is not authorized"));
        }
    }

    @PutMapping("/password")
    public ResponseEntity<MessageResponse> changeUserPassword(@RequestBody Map<String, String> request,
                                                              Authentication authentication) {
        if (authentication != null) {
            ApplicationUser applicationUser = userServices.findOneByUsername(authentication.getName());
            if (bCryptPasswordEncoder.matches(request.get("oldPassword"), applicationUser.getPassword())) {
                applicationUser.setPassword(bCryptPasswordEncoder.encode(request.get("newPassword")));
                return ResponseEntity.ok(new MessageResponse("", "Success"));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("oldPassword", "Wrong old password"));
            }
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new MessageResponse("", "Unauthorized"));
        }
    }

}
