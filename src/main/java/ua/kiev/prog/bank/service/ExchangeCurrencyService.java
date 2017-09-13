package ua.kiev.prog.bank.service;

import ua.kiev.prog.bank.dao.ExchangeCurrencyDaoImpl;
import ua.kiev.prog.bank.entity.ExchangeCurrency;
import ua.kiev.prog.bank.entity.User;
import ua.kiev.prog.bank.enums.Currency;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Вадим on 13.09.2017.
 */
public class ExchangeCurrencyService {

    private ExchangeCurrencyDaoImpl dao;

    public ExchangeCurrencyService(ExchangeCurrencyDaoImpl dao) {
        this.dao = dao;
    }

    public void saveRate(ExchangeCurrency exchangeCurrency){
        dao.save(exchangeCurrency);
    }


    public void initRates(){
        List<ExchangeCurrency> list = new ArrayList<>();
        ExchangeCurrency UAH_EUR = new ExchangeCurrency(null, Currency.UAH,Currency.EUR,30.0);
        ExchangeCurrency UAH_USD = new ExchangeCurrency(null,Currency.UAH,Currency.USD,25.0);
        ExchangeCurrency EUR_UAH = new ExchangeCurrency(null,Currency.EUR,Currency.UAH,0.03);
        ExchangeCurrency USD_UAH = new ExchangeCurrency(null,Currency.USD,Currency.UAH,0.04);
        ExchangeCurrency EUR_USD = new ExchangeCurrency(null,Currency.EUR,Currency.USD,1.2);
        ExchangeCurrency USD_EUR = new ExchangeCurrency(null,Currency.USD,Currency.EUR,0.83);

        list.add(UAH_EUR);
        list.add(UAH_USD);
        list.add(EUR_UAH);
        list.add(USD_UAH);
        list.add(EUR_USD);
        list.add(USD_EUR);

        list.forEach(this::saveRate);
    }
}
