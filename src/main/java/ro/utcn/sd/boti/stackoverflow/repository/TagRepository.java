package ro.utcn.sd.boti.stackoverflow.repository;

import ro.utcn.sd.boti.stackoverflow.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagRepository{
    Tag save(Tag entity);
    void remove(Tag entity);
    Optional<Tag> findById(int id);
    List<Tag> findAll();
}
