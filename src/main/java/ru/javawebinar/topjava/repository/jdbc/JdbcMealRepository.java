package ru.javawebinar.topjava.repository.jdbc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

@Repository
public class JdbcMealRepository implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(JdbcMealRepository.class);

    private JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert simpleJdbcInsert;
    BeanPropertyRowMapper rowMapper= new BeanPropertyRowMapper<>(Meal.class);

    @Autowired
    public JdbcMealRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate).withTableName("meals").usingGeneratedKeyColumns("id");
    }

    @Override
    public Meal save(Meal meal, int userId) {
      MapSqlParameterSource map = new MapSqlParameterSource()
                .addValue("id", meal.getId())
                .addValue("datetime", meal.getDateTime())
                .addValue("description", meal.getDescription())
                .addValue("user_id", userId)
                .addValue("calories", meal.getCalories());
        if (meal.isNew()) {
            Number newKey = simpleJdbcInsert.executeAndReturnKey(map);
            meal.setId(newKey.intValue());
            log.info("mealID={} ", meal.getId());
         /*   jdbcTemplate.update("insert into meals(datetime, description, calories, user_id) values (?,?,?,?)",
                    meal.getDate(), meal.getDescription(), meal.getCalories(), userId);*/

        } else {
            if (jdbcTemplate.update("update  meals set  datetime =?, description = ?, calories =?  where id =? AND user_id =?",
                    meal.getDate(), meal.getDescription(), meal.getCalories(), meal.getId(), userId) == 0) {
                return null;
            }
        }
        return meal;
    }

    @Override
    public boolean delete(int id, int userId) {
        return jdbcTemplate.update("delete from meals where id = ? and user_id =?", id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
                Meal meal =(Meal) jdbcTemplate.query("select * from meals where id =? and user_id=?", rowMapper, id, userId)
                .stream()
                .findAny()
                .orElse(null);
      return DataAccessUtils.singleResult(Arrays.asList(meal));
    }

    @Override
    public List<Meal> getAll(int userId) {
        return jdbcTemplate.query("select * from meals where user_id =? order by id", rowMapper, userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return jdbcTemplate.query("select * from meals where user_id =? and datetime < ? and datetime >= ? order by id",
                rowMapper, userId, endDate, startDate);
    }
}
