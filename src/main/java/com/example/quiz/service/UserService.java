package com.example.quiz.service;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.quiz.dto.RegistrationDTO;
import com.example.quiz.models.Role;
import com.example.quiz.models.User;
import com.example.quiz.repos.RoleRepository;
import com.example.quiz.repos.UserRepository;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder bCryptPasswordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(),user.getPassword(),
        		mapRolesToAuthorities(user.getRoles()));
    }
    
    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles){
    	return roles.stream().map(r -> new SimpleGrantedAuthority(r.getName())).collect(Collectors.toList());
    }
    
    public User findByUsername(String username){
        return userRepository.findByUsername(username);
    }

    public User findUserById(Long userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(new User());
    }

    public boolean saveUser(RegistrationDTO userDto) {
        User userFromDB = userRepository.findByUsername(userDto.getUsername());
       
        if (userFromDB != null) {
            return false;
        }
        
        Role role = roleRepository.findByName("ROLE_USER");
        if(role==null) {
        	role = new Role();
        	role.setName("ROLE_USER");
        }
        
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setRoles(Collections.singletonList(role));
        user.setPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));
        userRepository.save(user);
        return true;
    }

    public boolean isAdmin(Principal principal){
        if(principal!=null){
            User user = findByUsername(principal.getName());
            return user.getRoles().stream()
               .anyMatch(role -> "ROLE_ADMIN".equals(role.getName()));
        }
        return false;
    }
}
