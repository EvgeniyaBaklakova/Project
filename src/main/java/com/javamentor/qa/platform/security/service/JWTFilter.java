package com.javamentor.qa.platform.security.service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component // Marks this as a component. Now, Spring Boot will handle the creation and management of the JWTFilter Bean
// and you will be able to inject it in other places of your code
public class JWTFilter extends OncePerRequestFilter {

    // Injecting Dependencies
    private final UserDetailsServiceImpl userDetailsService;
    private final JWTUtil jwtUtil;

    public JWTFilter(UserDetailsServiceImpl userDetailsService, JWTUtil jwtUtil) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        // Extracting the "Authorization" header
        String authHeader = request.getHeader("Authorization");

        // Checking if the header contains a Bearer token
        if (authHeader != null && !authHeader.isBlank() && authHeader.startsWith("Bearer ")) {
            // Extract JWT
            String jwt = authHeader.substring(7);
            if (jwt == null || jwt.isBlank()) {
                // Invalid JWT
                response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token in Bearer Header");
            } else {
                try {
                    // Verify token and extract email
                    String email = jwtUtil.validateTokenAndRetrieveSubject(jwt);

                    // Fetch User Details
                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);

                    // Create Authentication Token
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(email, userDetails.getPassword(), userDetails.getAuthorities());

                    // Setting the authentication on the Security Context using the created token
                    if (SecurityContextHolder.getContext().getAuthentication() == null) {
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                } catch (JWTVerificationException exc) {
                    // Failed to verify JWT
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid JWT Token");
                }
            }
        }

        // Continuing the execution of the filter chain
        filterChain.doFilter(request, response);
    }
}


//package com.javamentor.qa.platform.security.service;
//
//import javax.servlet.FilterChain;
//import javax.servlet.ServletException;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//import io.jsonwebtoken.io.IOException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.web.authentication.WebAuthenticationDetails;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//@Component
//public class JwtFilter extends OncePerRequestFilter {
//
//    private UserDetailsService userDetailsService;
//    private JwtProvider jwtProvider;
//
//    public JwtFilter(UserDetailsService userDetailsService, JwtProvider jwtProvider) {
//        this.userDetailsService = userDetailsService;
//        this.jwtProvider = jwtProvider;
//
//    }
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
//            throws ServletException, IOException, java.io.IOException {
//        String authToken = jwtProvider.getToken(request);
//        if (null != authToken) { // fails this check
//            String userName = jwtProvider.getUsernameFromToken(authToken);
//            if (null != userName) {
//                UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
//                if (jwtProvider.validateToken(authToken, userDetails)) {
//                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//                    authentication.setDetails(new WebAuthenticationDetails(request));
//
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                }
//            }
//        }
//        filterChain.doFilter(request, response); // returns error here
//    }
//}