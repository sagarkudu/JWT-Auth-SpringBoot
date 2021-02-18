package com.sagar.jwt.services;

import java.util.ArrayList;

import com.sagar.jwt.model.JwtRequest;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        
        //we have to use repo of db, this is fake id and password.
        if(username.equals("sagar")){

            //making object of User of spring user
            //this arraylist will have permissions or authorities or roles
            return new User("Sagar", "sagar123", new ArrayList<>());
        }
        else{
            throw new UsernameNotFoundException("User not found !!");
        }
    }

	public UserDetails loadUserByUsername(JwtRequest jwtRequest) {
		return null;
	}
    
}
