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

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        Map<LocalDate, Integer> sumCulMeal = new HashMap<>();

        for (UserMeal umeals : meals) {
            sumCulMeal.merge(umeals.getDateTime().toLocalDate(), umeals.getCalories(), (culCurrent, culNext) -> culCurrent + culNext);
        }
        for (UserMeal umeals : meals) {
            if (sumCulMeal.get(umeals.getDateTime().toLocalDate()) > caloriesPerDay & TimeUtil
                    .isBetweenInclusive(umeals.getDateTime().toLocalTime(), startTime, endTime))
                userMealWithExcesses.add(
                        new UserMealWithExcess(umeals.getDateTime(), umeals.getDescription(), umeals.getCalories(), true));

        }

        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByCyclesTwoVersion(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map<LocalDate, Integer> sumCulor = new HashMap<>();
        List<UserMealWithExcess> userMealWithExcesses = new ArrayList<>();
        for (UserMeal usermeal : meals) {
            LocalDate curentdate = usermeal.getDateTime().toLocalDate();
            sumCulor.put(curentdate, sumCulor.getOrDefault(curentdate, 0) + usermeal.getCalories());

        }
        for (UserMeal userMeals : meals) {
            if (sumCulor.get(userMeals.getDateTime().toLocalDate()) > caloriesPerDay && TimeUtil
                    .isBetweenInclusive(userMeals.getDateTime().toLocalTime(), startTime, endTime)) userMealWithExcesses
                    .add(creatUserMealWithExcess(userMeals, true));

        }
        return userMealWithExcesses;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {

        List<UserMealWithExcess> userMealWithExcesses;
        Map<LocalDate, Integer> sumCulMeal;


        sumCulMeal = meals.stream().collect(Collectors.groupingBy(x -> x.getDateTime().toLocalDate(), Collectors.summingInt(x -> x.getCalories())));

        userMealWithExcesses = meals.stream().filter(m -> TimeUtil.isBetweenInclusive(m.getDateTime().toLocalTime(), startTime, endTime))
                .map(m -> creatUserMealWithExcess(m, sumCulMeal.get(m.getDateTime().toLocalDate()) > caloriesPerDay)).collect(Collectors.toList());


        return userMealWithExcesses.stream().filter(excess -> excess.isExcess()).collect(Collectors.toList());
    }

    public static UserMealWithExcess creatUserMealWithExcess(UserMeal userMeal, boolean exceed) {
        return new UserMealWithExcess(userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories(), exceed);
    }
}
