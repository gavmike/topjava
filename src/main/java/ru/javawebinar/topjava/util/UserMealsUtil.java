package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);
        List<UserMealWithExcess> mealsTo2 = filteredByCyclesTwoVersion(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo2.forEach(System.out::println);
        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime,
                                                            LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesMeals = new HashMap<>();
        for (UserMeal umeals : meals) {
            sumCaloriesMeals.merge(umeals.getDateTime().toLocalDate(), umeals.getCalories(), Integer::sum);
        }
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal umeals : meals) {
            if (TimeUtil.isBetweenInclusive(umeals.getDateTime().toLocalTime(),
                    startTime, endTime)) userMealWithExcesses
                    .add(creatUserMealWithExcess(umeals,sumCaloriesMeals.get(umeals.getDateTime().toLocalDate()) > caloriesPerDay));
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByCyclesTwoVersion(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesMeals = new HashMap<>();
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal usermeal : meals) {
            LocalDate curentDate = usermeal.getDateTime().toLocalDate();
            sumCaloriesMeals.put(curentDate, sumCaloriesMeals.getOrDefault(curentDate, 0) + usermeal.getCalories());
        }
        for (UserMeal userMeals : meals) {
            if (TimeUtil.isBetweenInclusive(userMeals.getDateTime().toLocalTime(), startTime, endTime)){
                userMealWithExcesses.add(creatUserMealWithExcess(userMeals, sumCaloriesMeals.get(userMeals.getDateTime().toLocalDate()) > caloriesPerDay));
            }
        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime,
                                                             LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesMeals = meals.stream()
                .collect(Collectors.groupingBy(meal -> meal.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        List<UserMealWithExcess> userMealWithExcesses;
        userMealWithExcesses = meals.stream()
                .filter(meal -> TimeUtil.isBetweenInclusive(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> creatUserMealWithExcess(meal, sumCaloriesMeals.get(meal.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
        return userMealWithExcesses;
    }

    public static UserMealWithExcess creatUserMealWithExcess(UserMeal userMeal, boolean exceed) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed);
    }
}
