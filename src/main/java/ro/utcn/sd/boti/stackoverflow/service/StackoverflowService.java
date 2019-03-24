package ro.utcn.sd.boti.stackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.boti.stackoverflow.entity.Question;
import ro.utcn.sd.boti.stackoverflow.entity.Tag;
import ro.utcn.sd.boti.stackoverflow.entity.User;
import ro.utcn.sd.boti.stackoverflow.exception.QuestionNotFoundException;
import ro.utcn.sd.boti.stackoverflow.exception.TagNotFoundException;
import ro.utcn.sd.boti.stackoverflow.exception.UserNotFoundException;
import ro.utcn.sd.boti.stackoverflow.repository.QuestionRepository;
import ro.utcn.sd.boti.stackoverflow.repository.RepositoryFactory;
import ro.utcn.sd.boti.stackoverflow.repository.TagRepository;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
public class StackoverflowService {
    private final RepositoryFactory repositoryFactory;

    @Transactional
    public List<Question> listQuestions() {
        List<Question> questions= repositoryFactory.getQuestionRepository().findAll();
        questions.sort(Comparator.comparing(Question::getTime).reversed());
        return questions;
    }

    @Transactional
    public Question addQuestion(User user, String title, String text, String[] names) {
        Question question = new Question(user, title, text, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        TagRepository tagRepository = repositoryFactory.getTagRepository();
        List<Tag> tagList = new ArrayList<>();
        for (String tagName : names){
            if (tagName.length()>2) {
                Tag tag = new Tag(tagName);
                question.getTags().add(tag);
                tag.getQuestions().add(question);
                tagList.add(tagRepository.save(tag));
            }
        }
        return repositoryFactory.getQuestionRepository().save(question);
    }

    @Transactional
    public void removeQuestion(int id) {
        QuestionRepository repository = repositoryFactory.getQuestionRepository();
        Question question = repository.findById(id).orElseThrow(QuestionNotFoundException::new);
        repository.remove(question);
    }

    @Transactional
    public List<Question> searchInTitle(String s) {
        QuestionRepository repository = repositoryFactory.getQuestionRepository();
        return repository.findByText(s);
    }

    public List<Tag> listTags() { return repositoryFactory.getTagRepository().findAll(); }

    public List<Question> searchByTag(int id) {
        Tag tag = repositoryFactory.getTagRepository().findById(id).get();
        return tag.getQuestions();
    }

    public Optional<User> findByUserName(String username) {
        return repositoryFactory.getUserRepository().findByUserName(username);
    }
}