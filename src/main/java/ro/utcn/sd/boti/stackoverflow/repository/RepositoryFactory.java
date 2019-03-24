package ro.utcn.sd.boti.stackoverflow.repository;

public interface RepositoryFactory {

    QuestionRepository getQuestionRepository();

    TagRepository getTagRepository();

    UserRepository getUserRepository();

}
