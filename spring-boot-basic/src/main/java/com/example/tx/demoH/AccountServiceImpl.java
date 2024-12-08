package com.example.tx.demoH;

public class AccountServiceImpl implements AccountService {

  // 注入Dao
  private AccountDao accountDao;

  public void setAccountDao(AccountDao accountDao) {
    this.accountDao = accountDao;
  }

  @Override
  public void transfer(final String from, final String to, final Double money) {
    accountDao.outMoney(from, money);
    //int d = 1 / 0;
    accountDao.inMoney(to, money);
  }

}
