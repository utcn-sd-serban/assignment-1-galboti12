package ro.utcn.sd.boti.stackoverflow.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import ro.utcn.sd.boti.stackoverflow.entity.Answer;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AnswerMapper implements RowMapper<Answer> {

    @Override
    public Answer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Answer answer = new Answer();
        answer.setId(rs.getInt("id"));
        if (answer.getQuestion() != null) answer.getQuestion().setId(rs.getInt("question_id"));
        if (answer.getAuthor() != null) answer.getAuthor().setId(rs.getInt("author"));
        answer.setText(rs.getString("text"));
        answer.setTime(rs.getString("time"));
        return answer;
    }
}
