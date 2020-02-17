package ru.javawebinar.topjava.web;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class SecurityUtil {
 private static   int Id;

public static void setAuthUserId(int userId){
    Id =userId;

}
    public static int authUserId() {
        return Id;
    }

    public static int authUserCaloriesPerDay() {
        return DEFAULT_CALORIES_PER_DAY;
    }

}