package ro.utcn.sd.boti.stackoverflow.repository.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ro.utcn.sd.boti.stackoverflow.repository.QuestionRepository;
import ro.utcn.sd.boti.stackoverflow.repository.RepositoryFactory;
import ro.utcn.sd.boti.stackoverflow.repository.TagRepository;
import ro.utcn.sd.boti.stackoverflow.repository.UserRepository;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "stackoverflow.repository-type", havingValue = "JDBC")
public class JdbcRepositoryFactory implements RepositoryFactory {
    private final JdbcTemplate template;

    @Override
    public QuestionRepository getQuestionRepository() {
        return new JdbcQuestionRepository(template);
    }

    @Override
    public TagRepository getTagRepository() { return new JdbcTagRepository(template);}

    @Override
    public UserRepository getUserRepository() { return new JdbcUserRepository(template); }


}
