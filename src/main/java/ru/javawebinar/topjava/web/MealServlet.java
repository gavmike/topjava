package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealsDAO;
import ru.javawebinar.topjava.dao.MealsDAOImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(UserServlet.class);
    MealsDAO mealsDAO = new MealsDAOImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String type = req.getParameter("type");

        if(type==null) {

            List<MealTo> mealTo = MealsUtil.filteredByStreams(mealsDAO.getAll(), LocalTime.of(00, 00), LocalTime.of(23, 59), 2000);
            //log.info("meals{}", mealTo);

            req.setAttribute("meals", mealTo);
            req.getRequestDispatcher("meal.jsp").forward(req, resp);
        }
        if (type.equals("update")) {
            int id = Integer.valueOf(req.getParameter("id"));
            Meal meal = mealsDAO.get(id-1);
            log.info("meal{}", meal);
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("update.jsp").forward(req, resp);


        }
     }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String type = req.getParameter("type");

        if(type.equals("add")) {
            final String dateTime = req.getParameter("dateTime");
            final String description = req.getParameter("description");
            final String calories = req.getParameter("calories");
            Meal newMeal = new Meal(LocalDateTime.parse(dateTime), description, Integer.valueOf(calories));
            mealsDAO.save(newMeal);

        }
        if(type.equals("edit")) {
            final String dateTime = req.getParameter("dateTime");
            final String description = req.getParameter("description");
            final String calories = req.getParameter("calories");
            int id = Integer.valueOf(req.getParameter("id"));
            Meal editMeal = new Meal(id,LocalDateTime.parse(dateTime), description, Integer.valueOf(calories));
            log.info("id{}", id);
            mealsDAO.update(editMeal,id-1);

        }
        if(type.equals("delete")) {

            int id = Integer.valueOf(req.getParameter("id"));

            log.info("id{}", id);
            mealsDAO.delete(id-1);

        }

    }
}
