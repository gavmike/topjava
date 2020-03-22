package ru.javawebinar.topjava.repository.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ParameterizedPreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Repository
public class JdbcUserRepository implements UserRepository {

    private static final BeanPropertyRowMapper<User> ROW_MAPPER = BeanPropertyRowMapper.newInstance(User.class);

    private final JdbcTemplate jdbcTemplate;

    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private final SimpleJdbcInsert insertUser;

    @Autowired
    public JdbcUserRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.insertUser = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("users")
                .usingGeneratedKeyColumns("id");

        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public User save(User user) {
        BeanPropertySqlParameterSource parameterSource = new BeanPropertySqlParameterSource(user);

        if (user.isNew()) {
            Number newKey = insertUser.executeAndReturnKey(parameterSource);
            user.setId(newKey.intValue());
            insertRoles(user);
        } else if (namedParameterJdbcTemplate.update(
                "UPDATE users SET name=:name, email=:email, password=:password, " +
                        "registered=:registered, enabled=:enabled, calories_per_day=:caloriesPerDay WHERE id=:id", parameterSource) == 0) {
            return null;
        }
        deleteRoles(user);
        insertRoles(user);
        return user;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update("DELETE FROM users WHERE id=?", id) != 0;
    }

    @Override
    public User get(int id) {
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE id=?", ROW_MAPPER, id);
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public User getByEmail(String email) {
//        return jdbcTemplate.queryForObject("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        List<User> users = jdbcTemplate.query("SELECT * FROM users WHERE email=?", ROW_MAPPER, email);
        return setRoles(DataAccessUtils.singleResult(users));
    }

    @Override
    public List<User> getAll() {
        Map<Integer, Set<Role>> map = new HashMap<>();
        jdbcTemplate.query("select * from user_roles",  rs -> {
            Integer userId = rs.getInt("user_id");
            Role role = Role.valueOf(rs.getString("role"));

            map.merge(userId, EnumSet.of(role), (oldVal, newVal) -> {
                oldVal.add(role);
                return oldVal;
            });
        });

        List<User> usersList = jdbcTemplate.query("SELECT * FROM users ORDER BY name, email", ROW_MAPPER);
        usersList.forEach(user -> user.setRoles(map.get(user.getId())));

        return usersList;

    }

    public void insertRoles(User user) {
        Set<Role> userRoles = user.getRoles();
        if (!userRoles.isEmpty()) {
            jdbcTemplate.batchUpdate("INSERT INTO user_roles (user_id, role) values (?,?)", userRoles, userRoles.size(),
                    new ParameterizedPreparedStatementSetter<Role>() {
                        @Override
                        public void setValues(PreparedStatement ps, Role argument) throws SQLException {
                            ps.setInt(1, user.getId());
                            ps.setString(2, argument.name());
                        }
                    });

        }
    }

    public void deleteRoles(User user) {
        jdbcTemplate.update("delete  from user_roles where user_id=?", user.getId());
    }

    public User setRoles(User user) {
        if (user != null) {

            List<Role> role = jdbcTemplate.queryForList("select role from user_roles where user_id=?", Role.class, user.getId());
            user.setRoles(role);
        }
        return user;
    }
}
