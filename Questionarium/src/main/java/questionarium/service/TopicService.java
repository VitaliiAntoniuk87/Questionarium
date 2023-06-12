package questionarium.service;

import questionarium.model.Topic;
import questionarium.repository.dao.TopicRepository;

import java.util.List;

public class TopicService {

    private final TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public List<Topic> getAllTopics() {
        return topicRepository.getAll();
    }

    public Topic addTopic(String topicName) {
        return topicRepository.save(Topic.builder().name(topicName).build());
    }
}
