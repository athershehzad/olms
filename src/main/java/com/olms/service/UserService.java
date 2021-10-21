package com.olms.service;

import com.olms.entity.UserInfo;
import com.olms.exceptions.NotFoundException;
import com.olms.repository.UserInfoRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserInfoRepository userInfoRepository;

    public UserService(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }

    public UserInfo getUser(Integer id) {
        return userInfoRepository.findByIdAndStatus(id, true).orElseThrow(NotFoundException::new);
    }

    public String createUser(UserInfo userInfo) {

        Optional<UserInfo> info = userInfoRepository.findByEmailAndStatusIsTrue(userInfo.getEmail());
        if (info.isPresent()) {
            return "<p style=\"color:red\">User already exist <b>" + userInfo.getEmail() + "</b> ✨.</p>";
        } else {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            String encodedPassword = passwordEncoder.encode(userInfo.getPassword());
            userInfo.setPassword(encodedPassword);
            userInfo.setStatus(true);
            userInfo.setCreationDate(LocalDateTime.now());
            userInfoRepository.save(userInfo);
            return "Created user <b>" + userInfo.getFirstName() + " " + userInfo.getLastName() + "</b> ✨.";
        }


    }

    public List<UserInfo> getUsers() {
        return userInfoRepository.findAllByStatusOrderByIdDesc(true);
    }

    public UserInfo updateUser(Integer id, UserInfo request) {
        UserInfo fromDb = getUser(id);
        fromDb.setFirstName(request.getFirstName());
        fromDb.setLastName(request.getLastName());
        fromDb.setUserType(request.getUserType());
        fromDb.setStatus(request.isStatus());
        fromDb.setUpdatedDate(LocalDateTime.now());
        return userInfoRepository.save(fromDb);
    }
}
