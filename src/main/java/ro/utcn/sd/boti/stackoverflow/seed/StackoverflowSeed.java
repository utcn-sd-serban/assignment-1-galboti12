package ro.utcn.sd.boti.stackoverflow.seed;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.boti.stackoverflow.entity.Question;
import ro.utcn.sd.boti.stackoverflow.entity.Tag;
import ro.utcn.sd.boti.stackoverflow.entity.User;
import ro.utcn.sd.boti.stackoverflow.repository.QuestionRepository;
import ro.utcn.sd.boti.stackoverflow.repository.RepositoryFactory;
import ro.utcn.sd.boti.stackoverflow.repository.TagRepository;
import ro.utcn.sd.boti.stackoverflow.repository.UserRepository;

@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
public class StackoverflowSeed implements CommandLineRunner {
    private final RepositoryFactory factory;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        QuestionRepository questionRrepository = factory.getQuestionRepository();
        TagRepository tagRepository = factory.getTagRepository();
        UserRepository userRepository = factory.getUserRepository();


        User u1 = new User("boti", "boti", new Integer(1));
        User u2 = new User("root", "root", new Integer(0));

        if(userRepository.findAll().isEmpty()){
            userRepository.save(u1);
            userRepository.save(u2);
        }

        Question q1 = new Question(u1,"Asd lol", "Bdsa", "2019-03-24 15:13:54");
        Question q2 = new Question(u1,"qwe", "Dsadsada", "2019-03-24 15:13:54");
        Question q3 = new Question(u2,"zxc", "Fdsadsadsa", "2019-03-24 15:13:54");

        Tag t1 = new Tag("yolo");
        Tag t2 = new Tag("123");
        Tag t3 = new Tag("what");
        q1.getTags().add(t2);
        q3.getTags().add(t1);
        q3.getTags().add(t2);
        t2.getQuestions().add(q1);
        t2.getQuestions().add(q3);
        t1.getQuestions().add(q3);

        if (questionRrepository.findAll().isEmpty()) {
            questionRrepository.save(q1);
            questionRrepository.save(q2);
            questionRrepository.save(q3);
        }

        if (tagRepository.findAll().isEmpty()){
            tagRepository.save(t1);
            tagRepository.save(t2);
            tagRepository.save(t3);
        }
    }
}