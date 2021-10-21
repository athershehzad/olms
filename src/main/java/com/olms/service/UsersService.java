package com.olms.service;

import com.olms.entity.UserInfo;
import com.olms.exceptions.NotFoundException;
import com.olms.repository.UsersRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class UsersService {
    private final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public UserInfo getUser(Integer id) {
        return usersRepository.findByIdAndStatus(id, true).orElseThrow(NotFoundException::new);
    }

    public UserInfo createUser(UserInfo userInfo) {
        return usersRepository.save(userInfo);
    }

    public List<UserInfo> getUsers() {

        return usersRepository.findAllByStatusOrderByIdDesc(true);
    }

    public UserInfo updateUser(Integer id, UserInfo request) {
        UserInfo fromDb = getUser(id);
        fromDb.setFirstName(request.getFirstName());
        fromDb.setLastName(request.getLastName());
        fromDb.setUserType(request.getUserType());
        fromDb.setStatus(request.isStatus());
        fromDb.setUpdatedDate(LocalDateTime.now());
        return usersRepository.save(fromDb);
    }
}
