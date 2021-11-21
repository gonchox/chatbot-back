package com.chatbot.appchatbot.service;

import com.chatbot.appchatbot.domain.model.User;
import com.chatbot.appchatbot.domain.repository.UserRepository;
import com.chatbot.appchatbot.domain.service.UserService;
import com.chatbot.appchatbot.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;




//    @Override
//    public UserDetails loadUser(String username, String password) throws UsernameNotFoundException {
//        String defaultPassword = passwordEncoder.encode(password);
//        Optional valUser= userRepository.findByUsername(username);
//        if(!valUser.isEmpty()) {
//            return new org.springframework.security.core.userdetails.User(username, defaultPassword, DEFAULT_AUTHORITIES);
//        }
//        throw new UsernameNotFoundException(String.format("User not found with username %s", username));
//    }


    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        userRepository.delete(user);
        return ResponseEntity.ok().build();
    }

    @Override
    public User updateUser(Long userId, User userRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setPassword(userRequest.getPassword());
        user.setTelephone(userRequest.getTelephone());
        return userRepository.save(user);
    }

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId));
        return user;
    }

    @Override
    public Page<User> getAllUsers(Pageable pageable) {
        return userRepository.findAll(pageable);
    }

    @Override
    public User getUserByUsername(String username){
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User", "Username", username));

    }

//    @Override
//    public User loginUser(String username, String password) {
//        return userRepository.findUser(username, password)
//                .orElseThrow(() -> new ResourceNotFoundException("User", "Username",username));
//    }
}
