package questionarium.service;

import questionarium.model.Topic;
import questionarium.repository.dao.TopicRepository;

import java.util.ArrayList;
import java.util.List;

public class TopicDaoMock implements TopicRepository {

    public List<Topic> topics = new ArrayList<>() {
        {
            add(Topic.builder().id(1).name("Arrays").build());
            add(Topic.builder().id(2).name("Loop").build());
            add(Topic.builder().id(3).name("Stream").build());
        }
    };

    @Override
    public Topic save(Topic topic) {
        topics.add(topic);
        return topic;
    }

    @Override
    public Topic get(int id) {
        for (Topic topic : topics) {
            if (topic.getId() == id) return topic;
        }
        return null;
    }

    @Override
    public List<Topic> getAll() {
        return new ArrayList<>(topics);
    }

    @Override
    public boolean remove(int id) {
        return topics.removeIf(question -> question.getId() == id);
    }

    @Override
    public int update(Topic topic) {
        for (Topic element : topics) {
            if (element.getId() == topic.getId()) {
                element.setName(topic.getName());
                return topic.getId();
            }
        }
        return 0;
    }
}
