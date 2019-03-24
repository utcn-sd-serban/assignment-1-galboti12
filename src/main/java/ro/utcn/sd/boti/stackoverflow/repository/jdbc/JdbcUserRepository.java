package ro.utcn.sd.boti.stackoverflow.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.boti.stackoverflow.entity.User;
import ro.utcn.sd.boti.stackoverflow.entity.User;
import ro.utcn.sd.boti.stackoverflow.repository.UserRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcUserRepository implements UserRepository {

    private final JdbcTemplate template;

    @Override
    public User save(User entity) {
        if (entity.getId() == null) {
            entity.setId(insert(entity));
        } else {
            update(entity);
        }
        return entity;
    }

    @Override
    public void remove(User entity) {template.update("DELETE FROM user WHERE id = ?", entity.getId()); }

    @Override
    public Optional<User> findById(int id) {
        List<User> result = template.query("SELECT * FROM user WHERE id = ?", new UserMapper(), id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    @Override
    public List<User> findAll() {
        return template.query("SELECT * FROM user", new UserMapper());
    }

    @Override
    public Optional<User> findByUserName(String s) {
         List<User> result = template.query("SELECT * FROM user WHERE username = ?", new UserMapper(), s);
         return Optional.ofNullable(result.get(0));
    }

    private int insert(User user) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("user");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("username", user.getUsername());
        map.put("password", user.getPassword());
        map.put("isadmin", user.getIs_admin());
        return insert.executeAndReturnKey(map).intValue();
    }

    private void update(User user) {
        template.update("UPDATE user SET username = ?, password = ?, is_admin = ? WHERE id = ?",
                user.getUsername(), user.getPassword(), user.getIs_admin(), user.getId());
    }
}
