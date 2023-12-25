package mockito.app.persistence;

import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.OrderMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class OrderMapperTest {

    @Mock
    private ConnectionPool connectionPool;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement preparedStatement;

    @Mock
    private ResultSet resultSet;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(connectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);

    }

    @Test
    void getOrderStatusByUserIdReturnsStatusWhenOrderExists() throws SQLException, DatabaseException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("status")).thenReturn("delivered");

        String status = OrderMapper.getOrderStatusByUserId(1, connectionPool);

        assertEquals("delivered", status);
    }

    @Test
    void getOrderStatusByUserIdThrowsDatabaseExceptionWhenSQLExceptionOccurs() throws SQLException {
        when(preparedStatement.executeQuery()).thenThrow(SQLException.class);

        assertThrows(DatabaseException.class, () -> OrderMapper.getOrderStatusByUserId(1, connectionPool));
    }
}