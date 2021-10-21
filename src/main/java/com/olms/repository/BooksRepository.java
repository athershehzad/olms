package com.olms.repository;

import com.olms.entity.BooksEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BooksRepository extends JpaRepository<BooksEntity, Long> {

    List<BooksEntity> findByTitleOfTheBookOrAuthor(String title, String value);

    Optional<BooksEntity> findByIdAndStatus(long id, boolean active);

    List<BooksEntity> findAllByStatusOrderByIdDesc(boolean active);
}
