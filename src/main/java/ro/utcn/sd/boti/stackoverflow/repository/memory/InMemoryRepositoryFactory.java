package ro.utcn.sd.boti.stackoverflow.repository.memory;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.utcn.sd.boti.stackoverflow.repository.QuestionRepository;
import ro.utcn.sd.boti.stackoverflow.repository.RepositoryFactory;

@Component
@ConditionalOnProperty(name = "stackoverflow.repository-type", havingValue = "MEMORY")
public class InMemoryRepositoryFactory implements RepositoryFactory {
    private final InMemoryQuestionRepository questionRepository = new InMemoryQuestionRepository();

    @Override
    public QuestionRepository createQuestionRepository() {
        return questionRepository;
    }
}
