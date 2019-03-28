package ro.utcn.sd.boti.stackoverflow.repository.memory;

import ro.utcn.sd.boti.stackoverflow.entity.Answer;
import ro.utcn.sd.boti.stackoverflow.repository.AnswerRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryAnswerRepository implements AnswerRepository {

    private final Map<Integer, Answer> data = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public Answer save(Answer answer) {
        if (answer.getId() == null){
            answer.setId(currentId.incrementAndGet());
        }
        data.put(answer.getId(), answer);
        return answer;
    }

    @Override
    public void remove(Answer answer) { data.remove(answer.getId()); }

    @Override
    public Optional<Answer> findById(int id) { return Optional.ofNullable(data.get(id)); }
}
