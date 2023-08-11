package com.example.springbootjpaoracle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.NamedStoredProcedureQuery;
import jakarta.persistence.ParameterMode;
import jakarta.persistence.StoredProcedureParameter;


@Entity
@NamedStoredProcedureQuery(name = "User.plus1", procedureName = "plus1inout",
    parameters = {
        @StoredProcedureParameter(mode = ParameterMode.IN, name = "arg", type = Integer.class),
        @StoredProcedureParameter(mode = ParameterMode.OUT, name = "res", type = Integer.class)
    })
public class UserStoredProcedure {

  @Id
  @GeneratedValue
  private Long id;

}
