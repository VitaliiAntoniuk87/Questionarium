import questionarium.Command;
import questionarium.CommandName;
import questionarium.ConnectionSingleton;
import questionarium.repository.QuestionRepositoryImpl;
import questionarium.repository.TopicRepositoryImpl;
import questionarium.service.QuestionService;
import questionarium.service.TopicService;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {


        Connection connection = ConnectionSingleton.getConnection();

        QuestionService questionService = new QuestionService(new QuestionRepositoryImpl(connection));
        TopicService topicService = new TopicService(new TopicRepositoryImpl(connection));

        Command command = new Command(topicService, questionService);

        command.getStart();

    }
}