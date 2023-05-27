package questionarium.repository;

import questionarium.model.Question;
import questionarium.repository.dao.QuestionRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class QuestionRepositoryImpl implements QuestionRepository {

    private final Connection connection;

    private static final String SELECT_BY_ID = "SELECT * FROM public.question WHERE id = ?";

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
    public boolean save(Question question) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE);
            preparedStatement.setString(1, question.getText());
            preparedStatement.setInt(2, question.getTopicId());
            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
    }
}
