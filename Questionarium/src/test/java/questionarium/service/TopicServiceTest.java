package questionarium.service;

import org.junit.Assert;
import org.junit.Test;
import questionarium.model.Topic;

import java.util.List;

public class TopicServiceTest {

    private final TopicDaoMock topicDaoMock = new TopicDaoMock();

    @Test
    public void getAllTopicsTest() {
        TopicService topicService = new TopicService(topicDaoMock);
        List<Topic> actual = topicDaoMock.topics;
        List<Topic> expected = topicService.getAllTopics();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addTopicTest() {
        TopicService topicService = new TopicService(topicDaoMock);
        int listSizeActual = topicDaoMock.topics.size();
        Topic topic = Topic.builder().id(22).name("Lambda").build();
        topicService.addTopic(topic.getName());
        int listSizeExpected = topicDaoMock.topics.size();

        Assert.assertEquals(listSizeExpected - 1, listSizeActual);
    }
}