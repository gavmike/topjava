package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL1_ID =START_SEQ+2;
    public static final int MEAL2_ID =START_SEQ+3;
    public static final int MEAL3_ID =START_SEQ+4;
    public static final int MEAL4_ID =START_SEQ+5;
    public static final int MEALUPDATE_ID =MEAL1_ID;
    public static final int MEALCREATE_ID =START_SEQ+6;



    public static final Meal MEAL1 = new Meal(MEAL1_ID, LocalDateTime.of(2016, Month.JUNE, 22, 19, 10), "diner", 1222);
    public static final Meal MEAL2 = new Meal(MEAL2_ID, LocalDateTime.of(2017,  Month.JUNE, 22, 12, 10), "lunch", 1000);
    public static final Meal MEAL3 = new Meal(MEAL3_ID, LocalDateTime.of(2018,  Month.JUNE, 22, 13, 10), "diner", 1000);
    public static final Meal MEAL4 = new Meal(MEAL4_ID, LocalDateTime.of(2019,  Month.JUNE, 22, 11, 10), "lunch", 1000);

    public static final Meal MEAL_UPDATE = new Meal(MEAL1_ID, LocalDateTime.of(2019,  Month.JUNE, 22, 11, 10), "lunch", 999);
    public static final Meal MEAL_CREATE = new Meal( LocalDateTime.of(2019,  Month.JUNE, 22, 11, 10), "lunch", 999);





}
