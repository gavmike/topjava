package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.util.List;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;
@Service
public class MealService {

    private MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }
    public Meal create(Meal meal, int userId){
        return repository.save(meal,userId);
    }
    public void delete(int id){
        checkNotFoundWithId(repository.delete(id),id);
    }
    public Meal get(int id, int userId){
       return checkNotFoundWithId(repository.get(id,userId),id);
    }
    public List<Meal> getAll(){
        return repository.getAll().stream().collect(Collectors.toList());
    }
    public void update(Meal meal, int userId){
        checkNotFoundWithId(repository.save(meal,userId),meal.getId());
    }
}