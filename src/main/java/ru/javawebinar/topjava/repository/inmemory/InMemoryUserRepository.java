package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;


import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private Map<Integer, User> usersStorage = new ConcurrentHashMap<>();
    AtomicInteger counter = new AtomicInteger();

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return usersStorage.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save {}", user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            usersStorage.put(user.getId(), user);
            return user;
        } else  {
            return usersStorage.computeIfPresent(user.getId(), (id, oldUser) -> user);
        }
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return usersStorage.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAll");
        return usersStorage.values().stream().sorted(comparator).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        log.info("getByEmail {}", email);
        Iterator<Map.Entry<Integer, User>> iterator = usersStorage.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry mapElement = (Map.Entry) iterator.next();
            User user = (User) mapElement.getValue();
            if (user.getEmail().equals(email)) return user;
        }
        return null;
    }

    Comparator<User> comparator = (user, user2) -> user.getName().compareTo(user2.getName());
}
