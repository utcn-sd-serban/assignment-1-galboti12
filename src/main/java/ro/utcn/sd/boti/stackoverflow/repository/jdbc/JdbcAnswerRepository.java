package ro.utcn.sd.boti.stackoverflow.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import ro.utcn.sd.boti.stackoverflow.entity.Answer;
import ro.utcn.sd.boti.stackoverflow.entity.Answer;
import ro.utcn.sd.boti.stackoverflow.repository.AnswerRepository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RequiredArgsConstructor
public class JdbcAnswerRepository implements AnswerRepository {
    private final JdbcTemplate template;

    @Override
    public Answer save(Answer entity) {

        if (entity.getId() == null) {
            entity.setId(insert(entity));
        } else {
            update(entity);
        }
        return entity;
    }

    @Override
    public void remove(Answer entity) {
        template.update("DELETE FROM answer WHERE id = ?", entity.getId());
    }

    @Override
    public Optional<Answer> findById(int id) {
        List<Answer> answers = template.query("SELECT * FROM answer WHERE id = ?", new AnswerMapper(), id);
        return answers.isEmpty() ? Optional.empty() : Optional.of(answers.get(0));
    }

    private int insert(Answer answer) {
        SimpleJdbcInsert insert = new SimpleJdbcInsert(template);
        insert.setTableName("answer");
        insert.usingGeneratedKeyColumns("id");
        Map<String, Object> map = new HashMap<>();
        map.put("question_id", null == answer.getQuestion() ? 0 : answer.getQuestion().getId());
        map.put("author", null == answer.getAuthor() ? 0 : answer.getAuthor().getId());
        map.put("text", answer.getText());
        map.put("time", answer.getTime());
        return insert.executeAndReturnKey(map).intValue();
    }

    private void update(Answer answer) {
        template.update("UPDATE answer SET text = ? WHERE id = ?",
                answer.getText(), answer.getId());
    }
}
