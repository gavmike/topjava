package ru.javawebinar.topjava;

import org.springframework.context.support.GenericXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.TestUtil.mockAuthorize;
import static ru.javawebinar.topjava.UserTestData.USER;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management
        try (GenericXmlApplicationContext appCtx = new GenericXmlApplicationContext()) {
            appCtx.getEnvironment().setActiveProfiles(Profiles.getActiveDbProfile(), Profiles.REPOSITORY_IMPLEMENTATION);
            appCtx.load("spring/inmemory.xml");
            appCtx.refresh();


            Connection con = null;

            Statement stmt = null;
            ResultSet result = null;
            int res =0;

            try {
                //Registering the HSQLDB JDBC driver
                Class.forName("org.hsqldb.jdbc.JDBCDriver");
                //Creating the connection with HSQLDB
                con = DriverManager.getConnection("jdbc:hsqldb:file:C:/temp/topjava;ifxeists=true", "sa", "");
                if (con!= null){
                    System.out.println("Connection created successfully");
                    stmt = con.createStatement();
                    result = stmt.executeQuery("select * from meals");
                  /*  while(result.next()){
                        System.out.println(result.getInt("id")+" | "+
                                result.getString("name"));
                    }*/
                    // res = stmt.executeUpdate("CREATE TABLE tutorials_tbl (id INT NOT NULL, title VARCHAR(50) NOT NULL, author VARCHAR(20) NOT NULL, submission_date DATE, PRIMARY KEY (id)); ");
                    // System.out.println(res);


                }else{
                    System.out.println("Problem with creating connection");
                }

            }  catch (Exception e) {
                e.printStackTrace(System.out);
            }






            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", 2000, Role.ADMIN));
            System.out.println();

            mockAuthorize(USER);

            MealRestController mealController = appCtx.getBean(MealRestController.class);
            List<MealTo> filteredMealsWithExcess =
                    mealController.getBetween(
                            LocalDate.of(2020, Month.JANUARY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2020, Month.JANUARY, 31), LocalTime.of(11, 0));
            filteredMealsWithExcess.forEach(System.out::println);
            System.out.println();
            System.out.println(mealController.getBetween(null, null, null, null));
        }
    }
}
