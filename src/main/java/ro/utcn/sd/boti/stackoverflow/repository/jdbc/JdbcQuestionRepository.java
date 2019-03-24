package ro.utcn.sd.boti.stackoverflow.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.boti.stackoverflow.entity.Question;
import ro.utcn.sd.boti.stackoverflow.repository.QuestionRepository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcQuestionRepository implements QuestionRepository {
    private final JdbcTemplate template;

    @Override
    public Question save(Question entity) {
        if (entity.getId() == null) {
            entity.setId(insert(entity));
        } else {
            update(entity);
        }
        return entity;
    }

    @Override
    public void remove(Question entity) { template.update("DELETE FROM question WHERE id = ?", entity.getId()); }

    @Override
    public Optional<Question> findById(int id) {
        List<Question> questions = template.query("SELECT * FROM question WHERE id = ?", new QuestionMapper(), id);
        return questions.isEmpty() ? Optional.empty() : Optional.of(questions.get(0));
    }

    @Override
    public List<Question> findAll() {
        return template.query("SELECT * FROM question", new QuestionMapper());
    }

    @Override
    public List<Question> findByText(String s) {
        String sql = "SELECT * FROM question WHERE title LIKE ?";
        return template.query(sql, new PreparedStatementSetter() {
            public void setValues(PreparedStatement preparedStatement) throws SQLException {
                preparedStatement.setString(1, "%" + s + "%");
            }
        },new QuestionMapper());
    }

    private int insert(Question question) {
        // we use the SimpleJdbcInsert to easily retrieve the generated ID
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("author", null == question.getAuthor() ? 0 : question.getAuthor().getId());
        map.put("title", question.getTitle());
        map.put("text", question.getText());
        map.put("time", question.getTime());
        map.put("vote", question.getVote());
        return insert.executeAndReturnKey(map).intValue();
    }

    private void update(Question question) {
        template.update("UPDATE question SET title = ?, text = ?, vote = ? WHERE id = ?",
                question.getTitle(), question.getText(), question.getVote(), question.getId());
    }
}
