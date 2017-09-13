package ua.kiev.prog.bank.service;

import ua.kiev.prog.bank.dao.UserDaoImp;
import ua.kiev.prog.bank.entity.User;

import java.util.List;

/**
 * Created by Вадим on 13.09.2017.
 */
public class UserService {
    private UserDaoImp userDao;

    public UserService(UserDaoImp userDao) {
        this.userDao = userDao;
    }

    public void saveUser(User user){
        userDao.save(user);
    }

    public User findById(long id){
        return userDao.findById(id);
    }

    public List<User> getAll(){
        return userDao.getAll();
    }
}
