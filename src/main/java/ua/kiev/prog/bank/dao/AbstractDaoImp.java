package ua.kiev.prog.bank.dao;

import ua.kiev.prog.bank.entity.User;

import javax.persistence.EntityManager;

/**
 * Created by Вадим on 11.09.2017.
 */
public abstract class AbstractDaoImp implements GenericDao  {

    protected EntityManager entityManager;

    public AbstractDaoImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(Object o) {
        try {
            entityManager.getTransaction().begin();
            entityManager.persist(o);
            entityManager.getTransaction().commit();
        } catch (Exception e){
            e.printStackTrace();
            entityManager.getTransaction().rollback();
        }
    }
}
