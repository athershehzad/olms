package com.olms.repository;

import com.olms.entity.BookFineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookFineRepository extends JpaRepository<BookFineEntity, Long> {

    List<BookFineEntity> findAllByStatusOrderByIdDesc(boolean active);

    Optional<BookFineEntity> findByIdAndStatus(Integer id, boolean active);
}
