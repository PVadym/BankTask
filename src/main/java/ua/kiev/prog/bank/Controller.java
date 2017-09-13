package ua.kiev.prog.bank;

import ua.kiev.prog.bank.dao.AccountDaoImpl;
import ua.kiev.prog.bank.dao.ExchangeCurrencyDaoImpl;
import ua.kiev.prog.bank.dao.UserDaoImp;
import ua.kiev.prog.bank.entity.Account;
import ua.kiev.prog.bank.entity.User;
import ua.kiev.prog.bank.enums.Currency;
import ua.kiev.prog.bank.service.AccountService;
import ua.kiev.prog.bank.service.ExchangeCurrencyService;
import ua.kiev.prog.bank.service.UserService;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Scanner;

/**
 * Created by Вадим on 13.09.2017.
 */
public class Controller {

    private EntityManagerFactory entityManagerFactory;
    private EntityManager entityManager;
    private UserService userService;
    private AccountService service;
    private ExchangeCurrencyService exchangeCurrencyService;
    private Scanner sc;

    public void start() {
        try {
            init();
            try {
                while (true) {
                    System.out.println("1: add new user");
                    System.out.println("2: view all users");
                    System.out.println("3: add new account for user");
                    System.out.println("4: view all accounts for user");
                    System.out.println("5: replenish account");
                    System.out.println("6: transfer money by accounts");
                    System.out.println("7: get total user`s balance in UAH");
                    System.out.println("Press Enter to exit");
                    System.out.print("-> ");

                    String s = sc.nextLine();
                    switch (s) {
                        case "1":
                            addUser();
                            break;
                        case "2":
                            viewAllUsers();
                            break;
                        case "3":
                            createAccount();
                            break;
                        case "4":
                            getAllAccountsByUser();
                            break;
                        case "5":
                            replenishmentAccount();
                            break;
                        case "6":
                            transferMoney();
                            break;
                        case "7":
                            getTotalBalanceInUAH();
                            break;
                        default:
                            return;
                    }
                }
            } finally {
                sc.close();
                entityManager.close();
                entityManagerFactory.close();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return;
        }
    }

    private void getTotalBalanceInUAH() {
        System.out.print("Enter User id: ");
        String idStr = sc.nextLine();
        long id = Long.parseLong(idStr);

        System.out.println(service.totalBalanceInUAH(id));
    }

    private void transferMoney() {
        System.out.print("Enter User id: ");
        String idStr = sc.nextLine();
        long id = Long.parseLong(idStr);

        System.out.print("Enter currency Account From (UAH,EUR,USD): ");
        String currencyStrFrom = sc.nextLine();
        Currency currencyFrom = Currency.valueOf(currencyStrFrom);

        System.out.print("Enter currency Account To (UAH,EUR,USD): ");
        String currencyStrTo = sc.nextLine();
        Currency currencyTo = Currency.valueOf(currencyStrTo);

        System.out.print("Enter amount: ");
        String amountStr = sc.nextLine();
        long amount = Long.parseLong(amountStr);

        service.transfer(id,currencyFrom,currencyTo,amount);
    }

    private void replenishmentAccount() {
        System.out.print("Enter User id: ");
        String idStr = sc.nextLine();
        long id = Long.parseLong(idStr);

        System.out.print("Enter Account currency (UAH,EUR,USD): ");
        String currencyStr = sc.nextLine();
        Currency currency = Currency.valueOf(currencyStr);

        System.out.print("Enter amount: ");
        String amountStr = sc.nextLine();
        long amount = Long.parseLong(amountStr);

        service.replenishment(id,currency,amount);

    }

    private void getAllAccountsByUser() {
        System.out.print("Enter User id: ");
        String idStr = sc.nextLine();
        long id = Long.parseLong(idStr);
        service.getAccountsByUser(id).forEach(System.out::println);
    }

    private void createAccount() {

        System.out.print("Enter User id: ");
        String idStr = sc.nextLine();
        long id = Long.parseLong(idStr);
        User user = userService.findById(id);

        System.out.print("Enter Account currency (UAH,EUR,USD): ");
        String currencyStr = sc.nextLine();
        Currency currency = Currency.valueOf(currencyStr);

        Account account = new Account();
        account.setBalance(0.0);
        account.setCurrency(currency);
        account.setUser(user);
        service.save(account);
    }

    private void viewAllUsers() {
        userService.getAll().forEach(System.out::println);
    }

    private void addUser() {
        User user = new User();
        System.out.print("Enter User name: ");
        String name = sc.nextLine();
        user.setName(name);
        System.out.print("Enter User address: ");
        String address = sc.nextLine();
        user.setAddress(address);
        userService.saveUser(user);
    }

    private void init() {
        sc = new Scanner(System.in);
        entityManagerFactory = Persistence.createEntityManagerFactory("jpa");
        entityManager = entityManagerFactory.createEntityManager();
        UserDaoImp userDaoImp = new UserDaoImp(entityManager);
        AccountDaoImpl accountDao = new AccountDaoImpl(entityManager);
        ExchangeCurrencyDaoImpl exchangeCurrencyDao = new ExchangeCurrencyDaoImpl(entityManager);
        userService = new UserService(userDaoImp);
        service = new AccountService(accountDao, exchangeCurrencyDao);
        exchangeCurrencyService = new ExchangeCurrencyService(exchangeCurrencyDao);
        exchangeCurrencyService.initRates();
    }

}
