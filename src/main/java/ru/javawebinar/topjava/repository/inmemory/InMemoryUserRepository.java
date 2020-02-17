package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private Map<Integer,User> usersStorage = new ConcurrentHashMap<>();
    AtomicInteger counter = new AtomicInteger();
   {
        User user1 = new User(null, "pavel", "email1@mail.ru", "password1", Role.ROLE_ADMIN);
        User user2 = new User(null, "mike", "email2@mail.ru", "password2", Role.ROLE_USER);
       int i1 = counter.incrementAndGet();
        user1.setId(i1);
        usersStorage.put(user1.getId(),user1);
        int i2 = counter.incrementAndGet();
        user2.setId(i2);
        usersStorage.put(user2.getId(),user2);

            }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return usersStorage.remove(id)!=null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if(user.isNew()){
         int i = counter.incrementAndGet();
         user.setId(i);
         usersStorage.put(user.getId(),user);
         return user;
        }
        else return usersStorage.computeIfPresent(user.getId(),(id,oldUser)->user) ;
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return usersStorage.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return  usersStorage.values().stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Iterator<Map.Entry<Integer,User>> iterator = usersStorage.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) iterator.next();
            User user = (User)  mapElement.getValue();
            if(user.getEmail().equals(email)) return usersStorage.get(user.getId());
        }
        return null;
    }
    Comparator<User> comparator = new Comparator<User>() {
        @Override
        public int compare(User user, User user2) {
            return user.getName().compareTo(user2.getName());
        }
    };
}
