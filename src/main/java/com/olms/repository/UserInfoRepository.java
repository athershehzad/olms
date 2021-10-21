package com.olms.repository;

import com.olms.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo, Integer> {

    List<UserInfo> findAllByStatusOrderByIdDesc(boolean active);

    Optional<UserInfo> findByIdAndStatus(Integer id, boolean active);

    Optional<UserInfo> findByEmailAndStatusIsTrue(String email);

}
