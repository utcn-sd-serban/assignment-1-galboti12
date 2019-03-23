package ro.utcn.sd.boti.stackoverflow.repository;

import ro.utcn.sd.boti.stackoverflow.entity.Question;

public interface RepositoryFactory {

    QuestionRepository createQuestionRepository();

}
