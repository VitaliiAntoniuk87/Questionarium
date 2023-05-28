package questionarium.repository.dao;

import questionarium.model.Topic;

public interface TopicRepository {

    boolean save(Topic topic);

    Topic get(int id);

    boolean remove(int id);

    int update(Topic topic);
}
