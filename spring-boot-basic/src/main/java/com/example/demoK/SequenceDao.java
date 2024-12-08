package com.example.demoK;

public interface SequenceDao {

  Sequence getSequence(String sequenceId);

  int getNextValue(String sequenceId);
} 
