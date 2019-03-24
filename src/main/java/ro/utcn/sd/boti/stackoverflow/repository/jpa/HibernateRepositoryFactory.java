package ro.utcn.sd.boti.stackoverflow.repository.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import ro.utcn.sd.boti.stackoverflow.repository.QuestionRepository;
import ro.utcn.sd.boti.stackoverflow.repository.RepositoryFactory;
import ro.utcn.sd.boti.stackoverflow.repository.TagRepository;
import ro.utcn.sd.boti.stackoverflow.repository.UserRepository;

import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "stackoverflow.repository-type", havingValue = "JPA")
public class HibernateRepositoryFactory implements RepositoryFactory {
    private final EntityManager entityManager;

    @Override
    public QuestionRepository getQuestionRepository() { return new HibernateQuestionRepository(entityManager);
    }

    @Override
    public TagRepository getTagRepository() { return new HibernateTagRepository(entityManager);
    }

    @Override
    public UserRepository getUserRepository() { return new HibernateUserRepository(entityManager);
    }
}
