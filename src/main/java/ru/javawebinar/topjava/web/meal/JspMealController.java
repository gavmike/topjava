package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalDate;
import static ru.javawebinar.topjava.util.DateTimeUtil.parseLocalTime;
@Controller
@RequestMapping(value = "/meals")
public class JspMealController extends AbstractMealController {


    public JspMealController(MealService service) {
        super(service);
    }

    @GetMapping(value = "/{id}", params = "delete")
    public String delete(@PathVariable("id") Integer id) {
     /*   int userId = SecurityUtil.authUserId();
        service.delete(id, userId);*/
        super.delete(id);
        return "redirect:/meals";
    }

    @GetMapping(value = "/{id}", params = "update")
    public String update(@PathVariable("id") Integer id, HttpServletRequest httpServletRequest) {
        int userId = SecurityUtil.authUserId();
        Meal meal = service.get(id, userId);
        httpServletRequest.setAttribute("meal", meal);
        return "mealForm";
    }

    @GetMapping(value = "create")
    public String create(HttpServletRequest request) {
        final Meal meal =
                new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000);
        request.setAttribute("meal", meal);
        return "mealForm";
    }

    @PostMapping(value = "/save")
    public String save(HttpServletRequest request) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
      //  int userId = SecurityUtil.authUserId();
        Meal meal = new Meal(
                LocalDateTime.parse(request.getParameter("dateTime")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));

        if (StringUtils.isEmpty(request.getParameter("id"))) {
           // service.create(meal, userId);
            super.create(meal);
        } else {
            meal.setId(Integer.parseInt(request.getParameter("id")));
            //service.update(meal, userId);
            super.update(meal,meal.getId());
        }
        return "redirect:/meals";
    }

    @GetMapping
    public String getAll(Model model) {
        int userId = SecurityUtil.authUserId();
       // model.addAttribute("meals", MealsUtil.getTos(service.getAll(userId), SecurityUtil.authUserCaloriesPerDay()));
       model.addAttribute("meals", super.getAll());
        return "meals";
    }

    @GetMapping(value = "/filter")
    public String getAllFilter(Model model, HttpServletRequest request) {
        int userId = SecurityUtil.authUserId();
        LocalDate startDate = parseLocalDate(request.getParameter("startDate"));
        LocalDate endDate = parseLocalDate(request.getParameter("endDate"));
        LocalTime startTime = parseLocalTime(request.getParameter("startTime"));
        LocalTime endTime = parseLocalTime(request.getParameter("endTime"));
        List<MealTo> mealToList = MealsUtil.getFilteredTos(service.getBetweenInclusive(startDate, endDate, userId),
                SecurityUtil.authUserCaloriesPerDay(), startTime, endTime);
        request.setAttribute("meals", mealToList);
        model.addAttribute("meals", mealToList);
        return "meals";
    }
}
