package ro.utcn.sd.boti.stackoverflow.repository.memory;

import ro.utcn.sd.boti.stackoverflow.entity.Question;
import ro.utcn.sd.boti.stackoverflow.entity.Tag;
import ro.utcn.sd.boti.stackoverflow.repository.TagRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryTagRepository implements TagRepository {

    private final Map<Integer, Tag> data = new ConcurrentHashMap<>();
    private final AtomicInteger currentId = new AtomicInteger(0);

    @Override
    public Tag save(Tag tag) {
        if (tag.getId() == null){
            tag.setId(currentId.incrementAndGet());
        }
        data.put(tag.getId(), tag);
        return tag;
    }

    @Override
    public void remove(Tag tag) { data.remove(tag.getId()); }

    @Override
    public Optional<Tag> findById(int id) { return Optional.ofNullable(data.get(id)); }

    @Override
    public List<Tag> findAll() { return new ArrayList<>(data.values()); }
}
