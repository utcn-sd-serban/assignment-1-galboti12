package ro.utcn.sd.boti.stackoverflow.repository;

import ro.utcn.sd.boti.stackoverflow.entity.Answer;

import java.util.List;
import java.util.Optional;

public interface AnswerRepository {
    Answer save(Answer entity);
    void remove(Answer entity);
    Optional<Answer> findById(int id);
}
