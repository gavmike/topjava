package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);


    {
        MealsUtil.MEALS.forEach(m->save(m,m.getUserId()));
       /* System.out.println("init repo from static InMemoryRepo");
        System.out.println(repository);
        System.out.println("------------------");*/
    }

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meal.setUserId(userId);
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
       if (repository.get(id).getUserId()==userId)  return repository.remove(id)!=null;
       else return false;

    }

    @Override
    public Meal get(int id,int userId) {

       if(repository.get(id).getUserId()==userId)
           return repository.get(id);
       else return  null;


    }

    @Override
    public Collection<Meal> getAll() {
       return repository.values().stream().sorted(comparator).collect(Collectors.toList());

    }
    Comparator<Meal> comparator = new Comparator<Meal>() {
        @Override
        public int compare(Meal meal, Meal meal1) {
            return meal.getDate().compareTo(meal1.getDate());
        }
    };
}

