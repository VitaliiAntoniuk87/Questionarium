package questionarium;

import questionarium.model.Question;
import questionarium.model.Topic;
import questionarium.service.QuestionService;
import questionarium.service.TopicService;

import java.util.*;

public class Command {

    private TopicService topicService;
    private QuestionService questionService;

    private final Runnable getRandomQuestion = () -> System.out.println(questionService.getRandomQuestion().getText());

    private final Runnable getRandomQuestionByTopic = () -> {
        System.out.println("Choose the Topic name from the following list:");
        List<Topic> allTopics = printTopics();
        System.out.println("Write the topic name:");
        String topicName = checkTopicName(allTopics, new Scanner(System.in).next());
        System.out.println(questionService.getRandomQuestionByTopic(topicName).getText());
    };

    private final Runnable getTopics = () -> {
        System.out.println("Requested topic List:");
        printTopics();
    };

    private final Runnable addNewQuestion = () -> {
        System.out.println("Choose the topic you want add question in:");
        List<Topic> allTopics = printTopics();
        System.out.println("Write the topic name:");
        String topicName = checkTopicName(allTopics, new Scanner(System.in).next());
        int topicId = 0;
        for (Topic topic : allTopics) {
            if (topic.getName().equals(topicName)) {
                topicId = topic.getId();
            }
        }
        System.out.println("Oke. Now write down your question:");
        String newQuestion = new Scanner(System.in).next();
        questionService.addQuestion(newQuestion, topicId);
    };

    private final Runnable deleteQuestion = () -> {
        System.out.println("Question list:");
        List<Question> allQuestions = questionService.getAllQuestions();
        allQuestions.forEach(e -> System.out.println(e.getId() + ". " + e.getText()));
        System.out.println("Write question number to delete it:");
        Question question = getQuestionById(allQuestions, new Scanner(System.in).nextInt());
        questionService.deleteQuestion(Objects.requireNonNull(question));
    };

    private final Runnable addNewTopic = () -> {
        System.out.println("Please write name for new topic:");
        topicService.addTopic(new Scanner(System.in).next());
    };

    public Command(TopicService topicService, QuestionService questionService) {
        this.topicService = topicService;
        this.questionService = questionService;
    }

    public void getStart() {
        System.out.println("Hello, please select a command:");
        Arrays.asList(CommandName.values()).forEach(System.out::println);
        System.out.println("Make your choice:");
        String command = checkCommandName(new Scanner(System.in).next());
        terminal(CommandName.valueOf(command));

    }

    private void terminal(CommandName command) {
        Map<CommandName, Runnable> commandLine = new HashMap<>();
        commandLine.put(CommandName.GET_RANDOM_QUESTION_BY_TOPIC, getRandomQuestionByTopic);
        commandLine.put(CommandName.GET_RANDOM_QUESTION, getRandomQuestion);
        commandLine.put(CommandName.ADD_QUESTION, addNewQuestion);
        commandLine.put(CommandName.DELETE_QUESTION, deleteQuestion);
        commandLine.put(CommandName.GET_TOPICS, getTopics);
        commandLine.put(CommandName.ADD_TOPIC, addNewTopic);

        commandLine.get(command).run();
    }

    private String checkTopicName(List<Topic> topics, String topicName) {
        boolean isNameCorrect = false;
        while (!isNameCorrect) {
            for (Topic topic : topics) {
                if (topic.getName().equals(topicName)) {
                    isNameCorrect = true;
                    break;
                }
            }
            if (!isNameCorrect) {
                System.out.println("Provided name - wrong. Enter correct name: ");
                topicName = new Scanner(System.in).next();
            }
        }
        return topicName;
    }

    private String checkCommandName(String command) {
        boolean isNameCorrect = false;
        while (!isNameCorrect) {
            for (CommandName name : CommandName.values()) {
                if (name.name().equals(command)) {
                    isNameCorrect = true;
                    break;
                }
            }
            if (!isNameCorrect) {
                System.out.println("Provided name - wrong. Enter correct name: ");
                command = new Scanner(System.in).next();
            }
        }
        return command;
    }

    private Question getQuestionById(List<Question> questions, int userNumber) {
        Question correctQuestion = null;
        while (correctQuestion == null) {
            for (Question question : questions) {
                if (question.getId() == userNumber) {
                    correctQuestion = question;
                    break;
                }
            }
            if (correctQuestion == null) {
                System.out.println("Provided number - wrong. Enter correct number: ");
                userNumber = new Scanner(System.in).nextInt();
            }
        }
        return null;
    }

    private List<Topic> printTopics() {
        List<Topic> allTopics = topicService.getAllTopics();
        for (Topic topic : allTopics) {
            System.out.println(topic.getName());
        }
        return allTopics;
    }
}
