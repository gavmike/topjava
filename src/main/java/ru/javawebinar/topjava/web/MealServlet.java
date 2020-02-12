package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealsDao;
import ru.javawebinar.topjava.dao.MealsDaoImpl;
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
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    MealsDao mealsDAO = new MealsDaoImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String type = req.getParameter("type");
        if(type==null) {
            List<MealTo> mealTo = MealsUtil.filteredByStreams(mealsDAO.getAll(), LocalTime.of(00, 00),
                    LocalTime.of(23, 59), 2000);
            req.setAttribute("meals", mealTo);
            req.getRequestDispatcher("meal.jsp").forward(req, resp);
        }
        if (type.equals("update")) {
            int id = Integer.valueOf(req.getParameter("id"));
            Meal meal = mealsDAO.get(id);
            req.setAttribute("meal", meal);
            req.getRequestDispatcher("update.jsp").forward(req, resp);
        }
        if (type.equals("create")) {

            req.getRequestDispatcher("create.jsp").forward(req, resp);
        }
     }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String type = req.getParameter("type");
        final String dateTime = req.getParameter("dateTime");
        final String description = req.getParameter("description");
        final String calories = req.getParameter("calories");
        if(type.equals("add")) {

            Meal newMeal = new Meal(LocalDateTime.parse(dateTime), description, Integer.valueOf(calories));
            mealsDAO.save(newMeal);
            resp.sendRedirect("meals");
        }
        if(type.equals("edit")) {

            int id = Integer.valueOf(req.getParameter("id"));
            Meal editMeal = new Meal(id,LocalDateTime.parse(dateTime), description, Integer.valueOf(calories));
            mealsDAO.update(editMeal,id);
            resp.sendRedirect("meals");
        }
        if(type.equals("delete")) {
            int id = Integer.valueOf(req.getParameter("id"));
            mealsDAO.delete(id);
            log.info("delete eat id ={}", id);
            resp.sendRedirect("meals");
        }

    }
}
