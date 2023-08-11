package com.example.springbootjpaoracle.repository;

import com.example.springbootjpaoracle.entity.UserStoredProcedure;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


@Repository
@Transactional
public interface UserStoredProcedureRepository extends CrudRepository<UserStoredProcedure, Long> {

  @Procedure(name = "User.plus1")
  Integer plus1BackedByOtherNamedStoredProcedure(Integer arg);

  @Procedure
  Integer plus1inout(Integer arg);

}
