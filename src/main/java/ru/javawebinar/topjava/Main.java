package ru.javawebinar.topjava;

import ru.javawebinar.topjava.dao.MealsDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;


import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.List;

/**
 * @see <a href="http://topjava.herokuapp.com">Demo application</a>
 * @see <a href="https://github.com/JavaOPs/topjava">Initial project</a>
 */
public class Main {
    public static void main(String[] args) {
     /*   Meal meal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        Meal meal2 = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        System.out.println(meal.getId());
        System.out.println(meal2.getId());*/
        MealsDAOImpl mealsDAO = new MealsDAOImpl();
       // System.out.println(mealsDAO.getAll());
        Meal meal = new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
        //mealsDAO.update(meal,3);
        System.out.println(mealsDAO.getAll().size());
        System.out.println(mealsDAO.getAll().get(3).getId());
        //mealsDAO.delete(3);
       System.out.println(mealsDAO.getAll().get(3).getId());
       // System.out.println(mealsDAO.getAll());
       // System.out.println(mealsDAO.getAll().size());

       /* System.out.println( mealsDAO.get(1));*/
       List<MealTo> mealTo =  MealsUtil.filteredByCycles(mealsDAO.getAll(), LocalTime.of(00,00),LocalTime.of(23,59),2000);
        System.out.println(mealTo.size());
        System.out.println(mealTo.get(3).getId());


    }
}
