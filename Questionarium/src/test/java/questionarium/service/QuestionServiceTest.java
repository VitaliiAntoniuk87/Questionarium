package questionarium.service;

import org.junit.Assert;
import org.junit.Test;
import questionarium.model.Question;

public class QuestionServiceTest {

    private final QuestionDaoMock questionDaoMock = new QuestionDaoMock();

    @Test
    public void getRandomQuestionByTopicTest() {
        QuestionService questionService = new QuestionService(questionDaoMock);
        String topicName = "Stream";
        Question question = questionService.getRandomQuestionByTopic(topicName);
        int expected = question.getId();

        Assert.assertTrue(expected >= 3);
    }

    @Test
    public void getRandomQuestionTest() {
        QuestionService questionService = new QuestionService(questionDaoMock);
        Question question = questionService.getRandomQuestion();
        int expected = question.getId();

        Assert.assertTrue(expected >= 1 && expected <= 4);
    }

    @Test
    public void addQuestionTest() {
        QuestionService questionService = new QuestionService(questionDaoMock);
        int listSizeActual = questionDaoMock.questions.size();
        Question question = Question.builder().id(12).text("Interface?").topicId(3).build();
        questionService.addQuestion(question.getText(), question.getTopicId());
        int listSizeExpected = questionDaoMock.questions.size();

        Assert.assertEquals(listSizeExpected - 1, listSizeActual);
    }

    @Test
    public void deleteQuestionTest() {
        QuestionService questionService = new QuestionService(questionDaoMock);
        int listSizeActual = questionDaoMock.questions.size();
        Question question = Question.builder().id(4).text("Collection?").topicId(3).build();
        questionService.deleteQuestion(question);
        int listSizeExpected = questionDaoMock.questions.size();

        Assert.assertEquals(listSizeExpected + 1, listSizeActual);
    }
}