package ro.utcn.sd.boti.stackoverflow.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.utcn.sd.boti.stackoverflow.entity.Question;
import ro.utcn.sd.boti.stackoverflow.exception.QuestionNotFoundException;
import ro.utcn.sd.boti.stackoverflow.repository.QuestionRepository;
import ro.utcn.sd.boti.stackoverflow.repository.RepositoryFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Queue;

@Service
@RequiredArgsConstructor
public class QuestionManagementService {
    private final RepositoryFactory repositoryFactory;

    @Transactional
    public List<Question> listQuestions() {
        return repositoryFactory.createQuestionRepository().findAll();
    }

    @Transactional
    public Question addQuestion(String title, String text) {
        return repositoryFactory.createQuestionRepository().save(
                new Question(title, text, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date())));
    }

    @Transactional
    public void updateTitle(int id, String title) {
        QuestionRepository repository = repositoryFactory.createQuestionRepository();
        Question question = repository.findById(id).orElseThrow(QuestionNotFoundException::new);
        question.setTitle(title);
        repository.save(question);
    }

    @Transactional
    public void updateText(int id, String text) {
        QuestionRepository repository = repositoryFactory.createQuestionRepository();
        Question question = repository.findById(id).orElseThrow(QuestionNotFoundException::new);
        question.setText(text);
        repository.save(question);
    }

    @Transactional
    public void removeQuestion(int id) {
        QuestionRepository repository = repositoryFactory.createQuestionRepository();
        Question question = repository.findById(id).orElseThrow(QuestionNotFoundException::new);
        repository.remove(question);
    }
}