package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryMealRepository.class);

    {
        MealsUtil.MEALS.forEach(m -> save(m, m.getUserId()));
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
        else if(repository.get(meal.getId()).getUserId() == userId) {
            log.info("updateInMemory {}", meal);
            int oldUserId = repository.get(meal.getId()).getUserId();
            meal.setUserId(oldUserId);
            return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
        }
        else  return null;

    }

    @Override
    public boolean delete(int id, int userId) {
        if (repository.get(id).getUserId() == userId) return repository.remove(id) != null;
        if(repository.get(id)==null) return false;
        else return false;
    }

    @Override
    public Meal get(int id, int userId) {
        log.info("getFromDao {}", repository.get(id).getUserId());
        if(repository.get(id)==null) return null;
        if (repository.get(id).getUserId() == userId)
            return repository.get(id);
        else return null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return repository.values().stream().filter(x -> x.getUserId() == userId).sorted(comparator).collect(Collectors.toList());
    }

    Comparator<Meal> comparator = (meal, meal1) -> meal1.getDate().compareTo(meal.getDate());
}

