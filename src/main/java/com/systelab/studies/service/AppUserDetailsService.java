package com.systelab.studies.service;

import com.systelab.studies.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service(value = "userService")
public class AppUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public AppUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
       return userRepository.findByLogin(s).map(user -> {
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
                    return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), authorities);
                }
        ).orElseThrow(() -> new UsernameNotFoundException(String.format("The username %s doesn't exist", s)));
    }
}