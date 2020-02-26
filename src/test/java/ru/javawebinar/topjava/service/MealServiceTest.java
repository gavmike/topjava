package ru.javawebinar.topjava.service;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration("classpath:spring/spring-app.xml")
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))


public class MealServiceTest {
    @Autowired
    MealService mealService;

    @Test
    public void get() {
        Meal meal = mealService.get(MEAL1_ID, USER_ID);
        assertMatch(meal, MEAL1);
    }

    @Test(expected = NotFoundException.class)
    public void getForeigner() {
        Meal meal = mealService.get(MEAL1_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteIfHave() {
        mealService.delete(MEAL1_ID, USER_ID);
        mealService.get(MEAL1_ID, USER_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteIfForegein() {
        mealService.delete(MEAL1_ID, ADMIN_ID);
    }

    @Test(expected = NotFoundException.class)
    public void deleteIfNotHave() {
        mealService.delete(1, USER_ID);
    }

    @Test
    public void getBetweenHalfOpen() {
        List<Meal> actualList = mealService.getBetweenHalfOpen(LocalDate.of(2016, Month.JUNE, 21),
                LocalDate.of(2016, Month.JUNE, 23), USER_ID);
        assertMatch(actualList, MEAL1);
    }

    @Test
    public void getAll() {
        List<Meal> actualList = mealService.getAll(USER_ID);
        assertMatch(actualList, MEAL3, MEAL1);
    }

    @Test
    public void update() {
        mealService.update(MEAL_UPDATE, USER_ID);
        assertMatch(mealService.get(MEALUPDATE_ID, USER_ID), MEAL_UPDATE);
    }

    @Test(expected = NotFoundException.class)
    public void updateForeigner() {
        mealService.update(MEAL_UPDATE, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal createMeal = mealService.create(MEAL_CREATE, USER_ID);
        MEAL_CREATE.setId(MEALCREATE_ID);
        assertMatch(createMeal, MEAL_CREATE);
        assertMatch(mealService.get(MEALCREATE_ID, USER_ID), MEAL_CREATE);
    }
}