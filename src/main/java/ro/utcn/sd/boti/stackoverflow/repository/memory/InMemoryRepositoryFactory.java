package ro.utcn.sd.boti.stackoverflow.repository.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.utcn.sd.boti.stackoverflow.repository.QuestionRepository;
import ro.utcn.sd.boti.stackoverflow.repository.RepositoryFactory;
import ro.utcn.sd.boti.stackoverflow.repository.TagRepository;
import ro.utcn.sd.boti.stackoverflow.repository.UserRepository;

@Component
@ConditionalOnProperty(name = "stackoverflow.repository-type", havingValue = "MEMORY")
public class InMemoryRepositoryFactory implements RepositoryFactory {
    private final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();
    private final InMemoryTagRepository tagRepository = new InMemoryTagRepository();
    private final InMemoryUserRepository userRepository = new InMemoryUserRepository();

    @Override
    public QuestionRepository getQuestionRepository() {
        return questionRepository;
    }

    @Override
    public TagRepository getTagRepository() {
        return tagRepository;
    }

    @Override
    public UserRepository getUserRepository() { return userRepository; }
}
