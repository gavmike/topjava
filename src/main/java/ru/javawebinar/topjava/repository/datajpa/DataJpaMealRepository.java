package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Repository

public class DataJpaMealRepository implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;
    @Autowired
    private CrudUserRepository userRepository;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {

        if(!meal.isNew()  && get(meal.getId(),userId)==null){
            return null;
        }


           meal.setUser(userRepository.getOne(userId));
           return crudRepository.save(meal);

    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) != 0;

    }

    @Override
    public Meal get(int id, int userId) {

        return crudRepository.getByIdAndUserId(id, userId);

    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserId(userId).stream().sorted(((meal, t1) -> t1.getId().compareTo(meal.getId())))
                .collect(Collectors.toList());
        /*return   crudRepository.findAll();*//*.stream().sorted(((meal, t1) -> meal.getId().compareTo(t1.getId())))
              .collect(Collectors.toList());
*/

    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        // return crudRepository.getAllByDateIsEndingWithAndUserId(startDateTime, endDateTime, userId);
        return crudRepository.getBetween(startDateTime, endDateTime, userId);
    }
}
