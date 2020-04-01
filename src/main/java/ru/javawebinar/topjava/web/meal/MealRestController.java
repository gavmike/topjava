package ru.javawebinar.topjava.web.meal;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(value = MealRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MealRestController extends AbstractMealController {
    static final String REST_URL = "/rest/meals";

    @GetMapping
    List<MealTo> getAllMeal() {
        return super.getAll();
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @GetMapping("/{id}")
    public Meal get(@PathVariable int id) {
        return super.get(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody Meal meal, @PathVariable int id) {
        super.update(meal, id);
    }

    @PostMapping
    public ResponseEntity<Meal> createMeal(@RequestBody Meal meal) {
        Meal mealCreate = super.create(meal);
        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath().path(REST_URL + "{/id}").buildAndExpand(mealCreate.getId()).toUri();
        return ResponseEntity.created(uri).body(mealCreate);
    }

    @GetMapping("/filter")
    public List<MealTo> getFilterDate(@RequestParam(value = "start", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
                                      @RequestParam(value = "end", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end) {
        return super.getBetween(start.toLocalDate(), start.toLocalTime(), end.toLocalDate(), end.toLocalTime());
    }
}