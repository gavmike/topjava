package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService mealService;
    public List<MealTo> getAll(){
        log.info("getAll");
        int userId = SecurityUtil.authUserId();
       return MealsUtil.getTos( mealService.getAll(userId),MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
    public void  delete(int id){
        log.info("delete {}", id);
        int userId = SecurityUtil.authUserId();
       mealService.delete(id,userId);
    }
    public Meal create (Meal meal){
        log.info("create {}", meal);
        int userId = SecurityUtil.authUserId();
        log.info("userId {}", userId);
        checkNew(meal);
        return mealService.create(meal,userId);
    }
    public void update (Meal meal, int Id){
        log.info("update {}", meal);
        int userId = SecurityUtil.authUserId();
        log.info("userId {}", userId);
        assureIdConsistent(meal,Id);
        mealService.update(meal,userId);
    }
    public Meal get(int id){
        log.info("get {}", id);
        int userId = SecurityUtil.authUserId();
        log.info("userId {}", userId);
        return mealService.get(id,userId);
    }
    public List<MealTo> getAllWithTimeFilter(LocalTime start, LocalTime end, int userId, LocalDate startDate, LocalDate endTime) {
        return  MealsUtil.getFilteredTos(mealService.getAll(userId),MealsUtil.DEFAULT_CALORIES_PER_DAY,start,end)
                .stream().filter(x->x.getDateTime().toLocalDate().isBefore(endTime)).filter(x->x.getDateTime().toLocalDate().isAfter(startDate)).collect(Collectors.toList());
    }
}
