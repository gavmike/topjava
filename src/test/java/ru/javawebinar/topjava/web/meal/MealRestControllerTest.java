package ru.javawebinar.topjava.web.meal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.TestUtil.readFromJson;

class MealRestControllerTest extends AbstractControllerTest {
    @Autowired
    MealService mealService;


    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(MealRestController.REST_URL + "/100005"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MEAL_MATCHER.contentJson(MEAL4));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(MealRestController.REST_URL + "/100005"))
                .andExpect(status().isNoContent())
                .andDo(print());
        MEAL_MATCHER.assertMatch(mealService.getAll(SecurityUtil.authUserId()), List.of(MEAL7, MEAL6, MEAL5, MEAL3, MEAL2, MEAL1));
    }

    @Test
    void getAllMeal() throws Exception {
        perform(MockMvcRequestBuilders.get(MealRestController.REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MEALTo_MATCHER.contentJson(MealsUtil.getTos(MEALS,SecurityUtil.authUserCaloriesPerDay())));
    }

    @Test
    void createMeal() throws Exception {
        Meal meal = MealTestData.getNew();
        ResultActions resultActions = perform(MockMvcRequestBuilders.post(MealRestController.REST_URL).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andExpect(status().isCreated());
        Meal mealFromDb = readFromJson(resultActions, Meal.class);
        MEAL_MATCHER.assertMatch(mealService.getAll(SecurityUtil.authUserId()), List.of(mealFromDb, MEAL7, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }

    @Test
    void update() throws Exception {
        Meal meal = getUpdated();
        perform(MockMvcRequestBuilders.put(MealRestController.REST_URL + "/" + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(meal)))
                .andExpect(status().isNoContent());

        MEAL_MATCHER.assertMatch(mealService.getAll(SecurityUtil.authUserId()), List.of(MEAL7, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, meal));
    }


    @Test
    void getFilterDate() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders.get(MealRestController.REST_URL + "/filter?start=2019-05-30T00:00&end=2021-05-31T10:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MEALTo_MATCHER.contentJson((MealsUtil.getTos(List.of(MEAL4), 55))));

    }
    @Test
    void getFilterFormatter() throws Exception {
        ResultActions resultActions = perform(MockMvcRequestBuilders.get(MealRestController.REST_URL + "/between?startDate=2019-05-30&startTime=00:00&endDate=2021-05-31&endTime=10:00"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(MockMvcResultMatchers.content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(MEALTo_MATCHER.contentJson((MealsUtil.getTos(List.of(MEAL4), 55))));
    }
}