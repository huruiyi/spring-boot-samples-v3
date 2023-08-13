package com.example.springbootjpaoracle;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.springbootjpaoracle.repository.UserStoredProcedureRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.ParameterMode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@SpringBootTest(classes = JpaOracleApplication.class)
class StoredProcedureTest {

  @Autowired
  UserStoredProcedureRepository userStoredProcedureRepository;
  @Autowired
  EntityManager em;

  @Test
  void test_Procedure() {
    assertThat(userStoredProcedureRepository.plus1BackedByOtherNamedStoredProcedure(1)).isEqualTo(2);
    assertThat(userStoredProcedureRepository.plus1inout(1)).isEqualTo(2);
  }

  @Test
  void plainJpa21() {
    var proc = em.createStoredProcedureQuery("plus1inout");
    proc.registerStoredProcedureParameter(1, Integer.class, ParameterMode.IN);
    proc.registerStoredProcedureParameter(2, Integer.class, ParameterMode.OUT);

    proc.setParameter(1, 1);
    proc.execute();

    assertThat(proc.getOutputParameterValue(2)).isEqualTo(2);
  }

  @Test
  void plainJpa21_entityAnnotatedCustomNamedProcedurePlus1IO() {
    var proc = em.createNamedStoredProcedureQuery("User.plus1");

    proc.setParameter("arg", 1);
    proc.execute();

    Object res = proc.getOutputParameterValue("res");
    assertThat(res).isEqualTo(2);
  }
}
