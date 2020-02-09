package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsDAO {
    public Meal get (int id);
    public void delete(int id);
    public void update(Meal meal, int id);
    public Meal save (Meal meal);
    public List<Meal> getAll();
}
