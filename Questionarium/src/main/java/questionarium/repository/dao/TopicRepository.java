package questionarium.repository.dao;

import questionarium.model.Topic;

import java.util.List;

public interface TopicRepository {

    Topic save(Topic topic);

    Topic get(int id);

    List<Topic> getAll();

    boolean remove(int id);

    int update(Topic topic);
}
