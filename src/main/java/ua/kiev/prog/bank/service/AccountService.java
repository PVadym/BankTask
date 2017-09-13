package ua.kiev.prog.bank.service;

import ua.kiev.prog.bank.dao.AccountDaoImpl;
import ua.kiev.prog.bank.dao.ExchangeCurrencyDaoImpl;
import ua.kiev.prog.bank.dao.UserDaoImp;
import ua.kiev.prog.bank.entity.Account;
import ua.kiev.prog.bank.entity.Transaction;
import ua.kiev.prog.bank.entity.User;
import ua.kiev.prog.bank.enums.Currency;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


/**
 * Created by Вадим on 11.09.2017.
 */
public class AccountService {
    private AccountDaoImpl accountDao;
    private ExchangeCurrencyDaoImpl exchangeDao;

    public AccountService(AccountDaoImpl accountDao, ExchangeCurrencyDaoImpl exchangeDao) {
        this.accountDao = accountDao;
        this.exchangeDao = exchangeDao;
    }

    public void save(Account account){
        accountDao.save(account);
    }

    public void replenishment(long id, Currency currency, double amount){
        Account account = accountDao.findAccountByUserAndCurrency(id, currency);
        double currentBalance = account.getBalance();
        double afterBalance = currentBalance+amount;
        account.setBalance(afterBalance);
        createTransaction(amount, account, currentBalance);
        accountDao.save(account);
    }


    public void transfer(long id, Currency currencyFrom, Currency currencyTo, double amount){
        double rate = 1;
        if (currencyFrom != currencyTo){
            rate = exchangeDao.getRate(currencyFrom, currencyTo);
        }
        Account accountFrom = accountDao.findAccountByUserAndCurrency(id, currencyFrom);
        Account accountTo = accountDao.findAccountByUserAndCurrency(id, currencyTo);

        double currentBalance = accountFrom.getBalance();
        double afterBalance = currentBalance - amount;
        accountFrom.setBalance(afterBalance);
        createTransaction(afterBalance, accountFrom, currentBalance);

        currentBalance = accountTo.getBalance();
        afterBalance = currentBalance + amount/rate;
        accountTo.setBalance(afterBalance);
        createTransaction(afterBalance, accountTo, currentBalance);

        accountDao.save(accountFrom);
        accountDao.save(accountTo);
    }

    public double totalBalanceInUAH(long id){
        List<Account> accounts = new ArrayList<>();
        double totalBalance = 0.0;
        accounts.addAll(accountDao.findAccountsByUserId(id));
        for (Account account : accounts) {
            if(account.getCurrency()==Currency.UAH){
                totalBalance +=account.getBalance();
            } else {
                totalBalance +=account.getBalance()/exchangeDao.getRate(account.getCurrency(),Currency.UAH);
            }
        }
        return totalBalance;
    }

    public List<Account> getAccountsByUser(long id){
        return accountDao.findAccountsByUserId(id);
    }



    private void createTransaction(double afterBalance, Account account, double currentBalance) {
        Transaction transaction = new Transaction();
        transaction.setDate(new Date());
        transaction.setBalanceBefore(currentBalance);
        transaction.setBalanceAfter(afterBalance);
        account.addTransaction(transaction);
    }
}
