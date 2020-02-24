package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.jdbc.JdbcMealRepository;

import java.time.LocalDateTime;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            MealRepository mealRepository = (MealRepository) appCtx.getBean(JdbcMealRepository.class);
            //System.out.println(mealRepository.get(100002,100000));
         Meal newMeal =  new Meal(100006,LocalDateTime.now(),"NEWdiner",1111);
            //System.out.println(newMeal.isNew());
            mealRepository.save(newMeal,100_001);
            //System.out.println(mealRepository.getAll(100_000));
           // System.out.println(mealRepository.getBetweenHalfOpen(LocalDateTime.of(2010, Month.JANUARY, 30, 10, 0),
                 //   LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0),100_001));
            //mealRepository.delete(100_003,100_001);

           // mealRepository.save(newMeal,100001);
           // System.out.println(mealRepository.getAll(100000));
            /*System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            System.out.println();

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealTo> filteredMealsWithExcess =
                    mealController.getBetweenHalfOpen(
                            LocalDate.of(2020, Month.JANUARY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2020, Month.JANUARY, 31), LocalTime.of(11, 0));
            filteredMealsWithExcess.forEach(System.out::println);
            System.out.println();
            System.out.println(mealController.getBetweenHalfOpen(null, null, null, null));*/
        }
    }
}
