package ua.kiev.prog.bank.dao;

import ua.kiev.prog.bank.entity.ExchangeCurrency;
import ua.kiev.prog.bank.enums.Currency;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Created by Вадим on 11.09.2017.
 */
public class ExchangeCurrencyDaoImpl extends AbstractDaoImp {

    public ExchangeCurrencyDaoImpl(EntityManager entityManager) {
        super(entityManager);
    }

    public double getRate(Currency currencyFrom, Currency currencyTo){
        TypedQuery<Double> query = entityManager
                .createQuery("SELECT e.rate from ExchangeCurrency e where e.currencyFrom=:currencyFrom and e.currencyTo=:currencyTo",Double.class)
                .setParameter("currencyFrom", currencyFrom)
                .setParameter("currencyTo", currencyTo);
        return query.getSingleResult();
    }
}
