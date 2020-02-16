package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

public class AbstractMealController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService mealService;
    public List<MealTo> getAll(){
        log.info("getAll");
       return MealsUtil.getTos( mealService.getAll(),MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
    public void  delete(int id){
        log.info("delete {}", id);
       mealService.delete(id);
    }
    public Meal create (Meal meal){
        log.info("create {}", meal);
        int userId = SecurityUtil.authUserId();
        checkNew(meal);
        return mealService.create(meal,userId);
    }
    public void update (Meal meal){
        log.info("update {}", meal);
        int userId = SecurityUtil.authUserId();
        mealService.update(meal,userId);
    }
    public Meal get(int id){
        log.info("get {}", id);
        int userId = SecurityUtil.authUserId();
       return mealService.get(id,userId);
    }

}
