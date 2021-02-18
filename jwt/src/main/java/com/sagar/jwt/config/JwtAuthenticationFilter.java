package com.sagar.jwt.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sagar.jwt.helper.JwtUtil;
import com.sagar.jwt.services.CustomUserDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        
            //get jwt or header
            // this will have 'Bearer ' then jwt code
            String requestTokenHeader = request.getHeader("Authorization "); 

            //extract username and jwt token from requestTokenHeader
            String username=null;
            String jwtToken=null;
            
            //validate if starting with 'Bearer '
            if(requestTokenHeader!=null && requestTokenHeader.startsWith("Bearer ")){
                //removing Bearer
                jwtToken=requestTokenHeader.substring(7);

                //once we get the request, is code starting with 'Bearer '? 
                //now we want username
                try {
                    username = this.jwtUtil.extractUsername(jwtToken);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            

                //get username
                UserDetails userDetails = this.customUserDetailsService.loadUserByUsername(username);

                //checks
                if(username!=null && SecurityContextHolder.getContext().getAuthentication()==null){
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

                    //setting details
                    usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    //set authentication in security
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                } else {
                    System.out.println("token is not validated...");
                }
            }
            filterChain.doFilter(request, response);
    }
    
    
}
