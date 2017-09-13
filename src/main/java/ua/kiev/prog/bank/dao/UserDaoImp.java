package ua.kiev.prog.bank.dao;

import ua.kiev.prog.bank.entity.User;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Вадим on 11.09.2017.
 */
public class UserDaoImp extends AbstractDaoImp {

    public UserDaoImp(EntityManager entityManager) {
        super(entityManager);
    }

    public User findByName(String username){
        User user;
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);
        query.select(root)
                .where(builder.equal(root.get("name"), username));
        user = entityManager.createQuery(query).getSingleResult();
        return user;
    }

    public User findById(long id){
        return entityManager.find(User.class,id);
    }

    public List<User> getAll() {
        List<User> users = new ArrayList<>();
        users.addAll(entityManager.createQuery("select u from User u").getResultList());
        return users;
    }

}
