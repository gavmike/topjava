package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserTest extends UserServiceTest {

    @Test
    public void getUserWithMeal() {
        User actual = service.getUserWithMeal(ADMIN_ID);
        USER_MATCHER.assertMatch(actual, ADMIN);
        MEAL_MATCHER.assertMatch(actual.getMeals(), List.of(ADMIN_MEAL1, ADMIN_MEAL2));

    }
}
