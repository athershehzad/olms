package com.olms.repository;

import com.olms.entity.RollsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RollsRepository extends JpaRepository<RollsEntity, Long> {

}
