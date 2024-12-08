package com.example.tx.demoG;

import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

public class AccountServiceImpl implements AccountService {

  // 注入Dao
  private AccountDao accountDao;
  // 注入事务管理的模板
  private TransactionTemplate transactionTemplate;

  public void setAccountDao(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  public void setTransactionTemplate(TransactionTemplate transactionTemplate) {
    this.transactionTemplate = transactionTemplate;
  }

  @Override
  public void transfer(final String from, final String to, final Double money) {
    transactionTemplate.execute(new TransactionCallbackWithoutResult() {
      @Override
      protected void doInTransactionWithoutResult(TransactionStatus arg0) {
        accountDao.outMoney(from, money);
        //int d = 1 / 0;
        accountDao.inMoney(to, money);
      }
    });

  }

}
