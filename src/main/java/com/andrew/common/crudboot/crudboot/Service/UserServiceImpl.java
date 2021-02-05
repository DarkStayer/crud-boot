package com.andrew.common.crudboot.crudboot.Service;

import com.andrew.common.crudboot.crudboot.DAO.UserRepository;
import com.andrew.common.crudboot.crudboot.Model.Role;
import com.andrew.common.crudboot.crudboot.Model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public void addUser(User user) {
        if (user.getId()==0){
            user.setRoles(new HashSet<>(Collections.singleton(new Role(1L, "ROLE_USER"))));
            userRepository.save(user);
        }
        userRepository.save(user);
    }

    @Override
    public void removeUserById(long id) {
        userRepository.deleteById(id);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(long id) {
        Optional <User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(new User());
    }

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findUserByName(name);
        if (user==null){
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }
}
