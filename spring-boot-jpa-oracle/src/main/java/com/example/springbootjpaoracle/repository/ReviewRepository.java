package com.example.springbootjpaoracle.repository;

import com.example.springbootjpaoracle.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

  //JpaRepository
  //CrudRepository
  //PagingAndSortingRepository
}
