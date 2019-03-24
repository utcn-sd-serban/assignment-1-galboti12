package ro.utcn.sd.boti.stackoverflow.repository.memory;

import ro.utcn.sd.boti.stackoverflow.entity.Question;
import ro.utcn.sd.boti.stackoverflow.repository.QuestionRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryQuestionRepository implements QuestionRepository{

    private final Map<Integer, Question> data = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public Question save(Question question) {
        if (question.getId() == null){
            question.setId(currentId.incrementAndGet());
        }
        data.put(question.getId(), question);
        return question;
    }

    @Override
    public void remove(Question question) { data.remove(question.getId()); }

    @Override
    public Optional<Question> findById(int id) { return Optional.ofNullable(data.get(id)); }

    @Override
    public List<Question> findAll() { return new ArrayList<>(data.values()); }

    @Override
    public List<Question> findByText(String s) {
        ArrayList<Question> result = new ArrayList<>();
        for (Question q : data.values()){
            if (q.getTitle().toLowerCase().contains(s.toLowerCase())){ result.add(q); }
        }
        return result;
    }
}
