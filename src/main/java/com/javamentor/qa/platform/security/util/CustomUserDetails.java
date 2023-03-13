package com.javamentor.qa.platform.security.util;

import com.javamentor.qa.platform.models.entity.user.User;
import com.javamentor.qa.platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomUserDetails implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByLogin(username).stream().findFirst()
                .orElseThrow(() -> new UsernameNotFoundException("User not Found"));
        return UserMapper.userToPrincipal(user);
    }
}