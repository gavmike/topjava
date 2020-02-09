package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.CopyOnWriteArrayList;

public class MealsDAOImpl implements MealsDAO {
  private List<Meal> storage = new CopyOnWriteArrayList();
  private AtomicInteger id = new AtomicInteger(0);
    {
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save( new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500));
        save( new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000));
        save(new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 600));
        save( new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100));
        save(  new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000));
        save( new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500));
        save( new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 310));
    }

    @Override
    public Meal get(int id) {
        int i = 0;
        for (Meal meals:storage) {
            if(meals.getId()==id){
                i = storage.indexOf(meals);
            }
        }
        return storage.get(i);
    }

    @Override
    public void delete(int id) {
        int i=0;
        for (Meal meal:storage) {
            if(meal.getId()==id)
            { i = storage.indexOf(meal);
               break;
            }
        }
        storage.remove(i);
    }

    @Override
    public void update(Meal meal, int id) {
        int i =0;
        for (Meal mealThis:storage) {
            if(mealThis.getId()==id){
                i = storage.indexOf(mealThis);
            }
        }
        storage.set(i,meal);
    }

    @Override
    public Meal save(Meal meal) {
        meal.setId(id.getAndIncrement());
        storage.add(meal);
        return meal;
    }

    @Override
    public List<Meal> getAll() {
        return storage;
    }
}
