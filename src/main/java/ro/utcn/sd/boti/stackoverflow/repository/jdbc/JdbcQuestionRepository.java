package ro.utcn.sd.boti.stackoverflow.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.boti.stackoverflow.entity.Question;
import ro.utcn.sd.boti.stackoverflow.repository.QuestionRepository;

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
    public void remove(Question entity) { template.update("DELETE FROM student WHERE id = ?", entity.getId()); }

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
        String sql = "SELECT * FROM question WHERE title LIKE :name";
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("name", "%" +s+"%");
        return template.query(sql, new QuestionMapper(), params);
    }

    private int insert(Question question) {
        // we use the SimpleJdbcInsert to easily retrieve the generated ID
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("question");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("author", question.getAuthor().getId());
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
