package questionarium.repository;

import questionarium.exception.*;
import questionarium.model.Topic;
import questionarium.repository.dao.TopicRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TopicRepositoryImpl implements TopicRepository {

    private final Connection connection;

    private static final String SELECT_BY_ID = "SELECT * FROM public.topic WHERE id = ?";

    private static final String SELECT_ALL = "SELECT * FROM public.topic";

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
    public Topic save(Topic topic) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SAVE, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, topic.getName());
            preparedStatement.execute();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            generatedKeys.next();
            topic.setId(generatedKeys.getInt(1));
            return topic;

        } catch (SQLException e) {
            throw new SqlAddObjectException("cannot save Topic object to DB");
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
            throw new SqlGetObjectException("cannot get Topic object");
        }
    }

    @Override
    public List<Topic> getAll() {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<Topic> topics = new ArrayList<>();

            while (resultSet.next()) {
                Topic build = Topic.builder()
                        .id(resultSet.getInt(1))
                        .name(resultSet.getString(2))
                        .build();
                topics.add(build);
            }
            return topics;

        } catch (SQLException e) {
            throw new SqlGetFewObjectsException("cannot get few Topic objects");
        }
    }

    @Override
    public boolean remove(int id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(REMOVE_BY_ID);
            preparedStatement.setInt(1, id);
            return preparedStatement.execute();

        } catch (SQLException e) {
            throw new SqlRemovalException("Cant remove Topic");
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
            throw new SqlUpdateException("Cannot update Topic object");
        }
    }
}
