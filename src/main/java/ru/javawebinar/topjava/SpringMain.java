package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryUserRepository;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
          //  System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
           //AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
         //   System.out.println(adminUserController.getAll());
           // adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            //InMemoryUserRepository inMemoryUserRepository = (InMemoryUserRepository)new InMemoryUserRepository();
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
           // mealRestController.delete(3);
            //System.out.println(mealRestController.getAll());
            mealRestController.update(new Meal(1,LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 4444,1));
            System.out.println(mealRestController.get(1));
            mealRestController.delete(6);

            System.out.println(mealRestController.getAll());

            //System.out.println((List<User>) inMemoryUserRepository.getAll());
            //System.out.println(inMemoryUserRepository.get(1));
           // System.out.println(inMemoryUserRepository.get(2));
            //inMemoryUserRepository.delete(2);

            //inMemoryUserRepository.save(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            User newUser = new User(3, "userName3", "email@mail.ru3", "password", Role.ROLE_ADMIN);
            //inMemoryUserRepository.save(newUser);

            //InMemoryMealRepository instanceMemoMeal = new InMemoryMealRepository();
            //System.out.println( instanceMemoMeal.get(1,1));

       /*    for (Meal meals:instanceMemoMeal.getAll()) {
                System.out.println("cycle in spring Main");
                System.out.println(meals.getId());
                System.out.println(meals.getDescription());

            }*/

                
            }
        }

}
