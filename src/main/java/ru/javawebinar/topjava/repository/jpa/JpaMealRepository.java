package ru.javawebinar.topjava.repository.jpa;

import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User user = em.find(User.class, userId);
        if (user == null) return null;
        if (meal.getUser() == null) meal.setUser(user);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            if (get(meal.getId(), userId) == null) {
                return null;
            }
            if (!meal.getUser().equals(user)) {
                return null;
            }
            return em.merge(meal);
        }
    }

    @Transactional
    @Override
    public boolean delete(int id, int userId) {
        return em.createQuery("delete from Meal m where m.id=:id and m.user.id=:userId")
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        List<Meal> resultList = em.createQuery("SELECT m FROM Meal m WHERE m.id=:id AND m.user.id=:userId").setParameter("id", id)
                .setParameter("userId", userId).getResultList();
        return DataAccessUtils.singleResult(resultList);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createQuery("  from Meal m where m.user.id=:userId order by id desc").setParameter("userId", userId).getResultList();
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return  em.createQuery("  from Meal m where m.user.id=:userId and m.dateTime >= :startDate and m.dateTime< :endDate  order by id desc")
                .setParameter("userId", userId)
                .setParameter("startDate", startDate)
                .setParameter("endDate", endDate)
                .getResultList();

    }
}