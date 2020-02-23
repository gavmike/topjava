package ru.javawebinar.topjava.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcMealRepository.class);
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            jdbcTemplate.update("insert into meals(dateTime, description, calories, user_id) values (?,?,?,?)", meal.getDate(), meal.getDescription(), meal.getCalories(), userId);
            return meal;
        } else {
            jdbcTemplate.update("update  meals set  dateTime =?, description = ?, calories =?  where id =? AND user_id =?", meal.getDate(), meal.getDescription(), meal.getCalories(), meal.getId(), userId);
            return meal;
        }
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("delete from meals where id = ? and user_id =?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return jdbcTemplate.query("select * from meals where id =?", new Object[]{id}, new BeanPropertyRowMapper<>(Meal.class))
                .stream()
                .findAny()
                .orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("select * from meals where user_id =? order by id", new BeanPropertyRowMapper<>(Meal.class),userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("select * from meals where user_id =? and dateTime < ? and dateTime > ? order by id",
                new BeanPropertyRowMapper<>(Meal.class), userId, endDate, startDate);
    }
}
