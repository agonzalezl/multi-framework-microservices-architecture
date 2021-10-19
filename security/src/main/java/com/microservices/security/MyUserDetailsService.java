package com.microservices.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.microservices.security.repositories.UserRepository;
import com.microservices.security.models.User;
import com.microservices.security.models.MyUserDetails;

import java.util.Optional;
import java.util.ArrayList;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findByUserName(userName);

        user.orElseThrow(() -> new UsernameNotFoundException("Not found: " + userName));
       
        return user.map(MyUserDetails::new).get();
        /*
        return new org.springframework.security.core.userdetails.User("foo", "foo",
                new ArrayList<>()); */
    }

}