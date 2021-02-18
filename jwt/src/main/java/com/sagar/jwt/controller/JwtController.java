package com.sagar.jwt.controller;

import com.sagar.jwt.helper.JwtUtil;
import com.sagar.jwt.model.JwtRequest;
import com.sagar.jwt.model.JwtResponse;
import com.sagar.jwt.services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JwtController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping("/token")
    //accept username and password
    public ResponseEntity<?> generateToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        System.out.println(jwtRequest);
        
        //we will authenticate username and password 
        try {
            this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Bad Credentials");
        } catch (BadCredentialsException e){
            e.printStackTrace();
            throw new Exception("Bad Credentials");
        }

        // if username and password is correct then generate the token.
        UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(jwtRequest.getUsername());

        //to generate call the jwttutil class
        String token = this.jwtUtil.generateToken(userDetails);
        System.out.println("JWT :" +token);

        //but we have send token back to server, we have to convert it to json.
        //we have to send it object in key value pair, {"token" : value }, so let's create new JwtResponse.java class.
        return ResponseEntity.ok(new JwtResponse(token));
    }

    

}
