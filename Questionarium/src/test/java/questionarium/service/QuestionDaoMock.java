package questionarium.service;

import questionarium.model.Question;
import questionarium.model.Topic;
import questionarium.repository.dao.QuestionRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class QuestionDaoMock implements QuestionRepository {

    public List<Topic> topics = new ArrayList<>() {
        {
            add(Topic.builder().id(1).name("Arrays").build());
            add(Topic.builder().id(2).name("Loop").build());
            add(Topic.builder().id(3).name("Stream").build());
        }
    };
    public List<Question> questions = new ArrayList<>() {
        {
            add(Question.builder().id(1).text("Arrays?").topicId(1).build());
            add(Question.builder().id(2).text("Loop?").topicId(2).build());
            add(Question.builder().id(3).text("Stream?").topicId(3).build());
            add(Question.builder().id(4).text("Lambda?").topicId(3).build());
        }
    };

    @Override
    public List<Question> getAllByTopicName(String topicName) {
        int topicId = topics.stream()
                .filter(topic -> topic.getName().equals(topicName))
                .findFirst()
                .map(Topic::getId)
                .orElse(0);
        if (topicId != 0) {
            return questions.stream()
                    .filter(question -> question.getTopicId() == topicId)
                    .collect(Collectors.toList());
        }
        return null;
    }

    @Override
    public List<Question> getAll() {
        return new ArrayList<>(questions);
    }

    @Override
    public Question get(int id) {
        for (Question question : questions) {
            if (question.getId() == id) return question;
        }
        return null;
    }

    @Override
    public Question save(Question question) {
        questions.add(question);
        return question;
    }

    @Override
    public boolean remove(int id) {
        return questions.removeIf(question -> question.getId() == id);
    }

    @Override
    public int update(Question question) {
        for (Question element : questions) {
            if (element.getId() == question.getId()) {
                element.setText(question.getText());
                element.setTopicId(question.getTopicId());
                return question.getId();
            }
        }
        return 0;
    }
}
