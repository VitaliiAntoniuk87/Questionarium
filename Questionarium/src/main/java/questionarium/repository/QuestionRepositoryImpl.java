package questionarium.repository;

import questionarium.exception.*;
import questionarium.model.Question;
import questionarium.repository.dao.QuestionRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QuestionRepositoryImpl implements QuestionRepository {

    private final Connection connection;

    private static final String SELECT_BY_ID = "SELECT * FROM public.question WHERE id = ?";

    private static final String SELECT_ALL = "SELECT * FROM public.question";

    private static final String SELECT_ALL_BY_TOPIC_NAME =
            """
                            SELECT question.id,text,topic_id,
                            name FROM public.question
                            FULL JOIN public.topic ON
                            question.topic_id = topic.id WHERE name = ?
                    """;

    private static final String SAVE =
            """
                            INSERT INTO public.question(
                            text, topic_id)
                            VALUES (?, ?)
                    """;


    private static final String REMOVE_BY_ID =
            """
                            DELETE FROM public.question
                            WHERE id = ?
                                        
                    """;


    private static final String UPDATE =
            """
                            UPDATE public.question
                            SET text=?, topic_id=?
                            WHERE  id = ?;
                                                
                    """;


    public QuestionRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Question> getAllByTopicName(String topicName) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_BY_TOPIC_NAME);
            preparedStatement.setString(1, topicName);
            return getQuestions(preparedStatement);

        } catch (SQLException e) {
            throw new SqlGetFewObjectsException("cannot get few Question objects by Topic");
        }
    }

    @Override
    public List<Question> getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            return getQuestions(preparedStatement);

        } catch (SQLException e) {
            throw new SqlGetFewObjectsException("cannot get few Question objects");
        }

    }

    private List<Question> getQuestions(PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();
        ArrayList<Question> questions = new ArrayList<>();

        while (resultSet.next()) {
            questions.add(Question.builder()
                    .id(resultSet.getInt(1))
                    .text(resultSet.getString(2))
                    .topicId(resultSet.getInt(3))
                    .build()
            );
        }
        return questions;
    }

    @Override
    public Question save(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, question.getText());
            preparedStatement.setInt(2, question.getTopicId());
            preparedStatement.execute();

            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            question.setId(generatedKeys.getInt(1));
            return question;

        } catch (SQLException e) {
            throw new SqlAddObjectException("cannot save Question object to DB");
        }
    }


    @Override
    public Question get(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return Question.builder()
                    .id(resultSet.getInt(1))
                    .text(resultSet.getString(2))
                    .topicId(resultSet.getInt(3))
                    .build();

        } catch (SQLException e) {
            throw new SqlGetObjectException("cannot get Question object");
        }
    }

    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new SqlRemovalException("Cant remove Question");
        }
    }


    @Override
    public int update(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, question.getText());
            preparedStatement.setInt(2, question.getTopicId());
            preparedStatement.setInt(3, question.getId());
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new SqlUpdateException("Cannot update Question object");
        }
    }
}
