package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
@Controller
public class MealRestController extends AbstractMealController {
    @Autowired
    private MealService service;

    @Override
    public List<MealTo> getAll() {
        return super.getAll();
    }

    public List<MealTo> getAllWithTimeFilter(LocalTime start, LocalTime end) {
        return  MealsUtil.getFilteredTos(service.getAll(),MealsUtil.DEFAULT_CALORIES_PER_DAY,start,end);
    }

    @Override
    public void delete(int id) {
        super.delete(id);
    }

    @Override
    public Meal create(Meal meal) {
        return super.create(meal);
    }

    @Override
    public void update(Meal meal) {
        super.update(meal);
    }

    @Override
    public Meal get(int id) {
        return super.get(id);
    }
}