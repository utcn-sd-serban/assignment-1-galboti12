package ro.utcn.sd.boti.stackoverflow.repository.jdbc;

import org.springframework.jdbc.core.RowMapper;
import ro.utcn.sd.boti.stackoverflow.entity.Question;

import java.sql.ResultSet;
import java.sql.SQLException;


public class QuestionMapper implements RowMapper<Question> {

    @Override
    public Question mapRow(ResultSet rs, int rowNum) throws SQLException {
        Question question = new Question();
        question.setId(rs.getInt("id"));
        question.getAuthor().setId(rs.getInt("author"));
        question.setTitle(rs.getString("title"));
        question.setText(rs.getString("text"));
        question.setTime(rs.getString("time"));
        question.setVote(rs.getInt("vote"));
        return question;
    }

}
