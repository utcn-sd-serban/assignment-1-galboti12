package ro.utcn.sd.boti.stackoverflow.repository;

import ro.utcn.sd.boti.stackoverflow.entity.Question;

import java.util.List;
import java.util.Optional;

public interface QuestionRepository {
    List<Question> findAll();
    Question save(Question question);
    void remove(Question question);
    Optional<Question> findById(int id);
}
