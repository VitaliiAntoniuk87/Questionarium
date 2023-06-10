package questionarium.service;

import questionarium.model.Question;
import questionarium.repository.dao.QuestionRepository;

import java.util.List;
import java.util.Random;

public class QuestionService {

    private final QuestionRepository questionRepository;
    private static final Random RANDOM = new Random();

    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    public Question getRandomQuestionByTopic(String topicName) {
        List<Question> questions = questionRepository.getAllByTopicName(topicName);
        return questions.get(RANDOM.nextInt(0, questions.size()));
    }

    public Question getRandomQuestion() {
        List<Question> questions = questionRepository.getAll();
        return questions.get(RANDOM.nextInt(0, questions.size()));
    }

    public List<Question> getAllQuestions() {
        return questionRepository.getAll();
    }


    public Question addQuestion(String questionText, int topicId) {
        return questionRepository.save(Question.builder().text(questionText).topicId(topicId).build());
    }

    public void deleteQuestion(Question question) {
        questionRepository.remove(question.getId());
    }


}
