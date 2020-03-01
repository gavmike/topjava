package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.jpa.JpaMealRepository;

import java.util.Arrays;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml", "spring/inmemory.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            //AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            //adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            System.out.println();

           // MealRestController mealController = appCtx.getBean(MealRestController.class);
            MealRepository mealRepository =(MealRepository) appCtx.getBean(JpaMealRepository.class);
            //mealRepository.getAll(100_000).forEach(System.out::println);
            //mealController.getAll().forEach(System.out::println);
           /* List<MealTo> filteredMealsWithExcess =
                    mealController.getBetween(
                            LocalDate.of(2020, Month.JANUARY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2020, Month.JANUARY, 31), LocalTime.of(11, 0));*/
           // filteredMealsWithExcess.forEach(System.out::println);
           // System.out.println();
            //System.out.println(mealController.getBetween(null, null, null, null));
        }
    }
}
