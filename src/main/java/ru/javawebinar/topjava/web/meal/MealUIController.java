package ru.javawebinar.topjava.web.meal;


import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;


@RestController
@RequestMapping("/ajax/meals")
public class MealUIController extends AbstractMealController {

    @Override
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<MealTo> getAll() {
        return super.getAll();
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @PostMapping
    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    public void createOrUpdate(@RequestParam Integer id,
                               @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTime,
                               @RequestParam String description,
                               @RequestParam Integer calories) {
        Meal meal = new Meal(id, dateTime, description, calories);
        if (meal.isNew()) {
            super.create(meal);
        }
    }
   @Override
   @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
   public List<MealTo> getBetween(
           @RequestParam(value = "startDate", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
           @RequestParam(value = "startTime", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime startTime,
           @RequestParam(value = "endDate", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
           @RequestParam(value = "endTime", required = false)@DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime endTime) {
               return super.getBetween(startDate, startTime, endDate, endTime);
          }



}
