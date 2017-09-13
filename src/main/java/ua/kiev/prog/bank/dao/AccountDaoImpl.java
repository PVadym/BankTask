package ua.kiev.prog.bank.dao;

import ua.kiev.prog.bank.entity.Account;
import ua.kiev.prog.bank.entity.User;
import ua.kiev.prog.bank.enums.Currency;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Вадим on 11.09.2017.
 */
public class AccountDaoImpl extends AbstractDaoImp {
    public AccountDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public Account findAccountByUserAndCurrency(long id, Currency currency){
       TypedQuery<Account> query =  entityManager.createQuery("select a from Account a where a.user.id=:id and a.currency=:currency", Account.class);
        query.setParameter("id",id)
                .setParameter("currency", currency);
        return  query.getSingleResult();
    }

    public List<Account> findAccountsByUserId(long id){
        TypedQuery<Account> query =  entityManager.createQuery("select a from Account a where a.user.id=:id", Account.class);
        query.setParameter("id",id);
        return  query.getResultList();
    }
}
