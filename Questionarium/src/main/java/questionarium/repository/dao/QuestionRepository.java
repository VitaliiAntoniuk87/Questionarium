package questionarium.repository.dao;

import questionarium.model.Question;

import java.util.List;

public interface QuestionRepository {

    List<Question> getAllByTopicName(String topicName);

    List<Question> getAll();

    Question get(int id);

    Question save(Question question);

    boolean remove(int id);

    int update(Question question);

}
