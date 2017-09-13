package ua.kiev.prog.bank.dao;

/**
 * Created by Вадим on 11.09.2017.
 */
public interface GenericDao <T> {

    void save(T t);

}
