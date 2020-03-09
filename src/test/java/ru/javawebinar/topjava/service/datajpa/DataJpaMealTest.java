package ru.javawebinar.topjava.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.service.MealServiceTest;

//@ActiveProfiles({Profiles.HSQL_DB,Profiles.DATAJPA})
@ActiveProfiles( Profiles.DATAJPA)
public class DataJpaMealTest extends MealServiceTest {
}
