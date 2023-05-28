package questionarium.repository;

import questionarium.model.Topic;
import questionarium.repository.dao.TopicRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TopicRepositoryImpl implements TopicRepository {

    private final Connection connection;

    private static final String SELECT_BY_ID = "SELECT * FROM public.topic WHERE id = ?";

    private static final String SAVE =
            """
                            INSERT INTO public.topic(
                            name)
                            VALUES (?)
                    """;

    private static final String REMOVE_BY_ID =
            """
                            DELETE FROM public.topic
                            WHERE id = ?
                                        
                    """;

    private static final String UPDATE =
            """
                            UPDATE public.topic
                            SET name=?
                            WHERE  id = ?;
                                                
                    """;


    public TopicRepositoryImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean save(Topic topic) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE);
            preparedStatement.setString(1, topic.getName());
            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Topic get(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();

            return Topic.builder()
                    .id(resultSet.getInt(1))
                    .name(resultSet.getString(2))
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
    public int update(Topic topic) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(UPDATE);
            preparedStatement.setString(1, topic.getName());
            preparedStatement.setInt(2, topic.getId());
            return preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
