package ro.utcn.sd.boti.stackoverflow.repository;

import ro.utcn.sd.boti.stackoverflow.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository{
    Question save(Question entity);
    void remove(Question entity);
    Optional<Question> findById(int id);
    List<Question> findAll();
    List<Question> findByText(String s);
}
