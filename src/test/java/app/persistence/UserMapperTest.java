package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserMapperTest {

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
    void loginSuccessful() throws SQLException, DatabaseException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyString())).thenReturn(1);
        when(resultSet.getString(anyString())).thenReturn("test");
        when(resultSet.getBoolean(anyString())).thenReturn(false);

        User user = UserMapper.login("test@test.com", "password", connectionPool);

        assertNotNull(user);
        assertEquals(1, user.getId());
    }

    @Test
    void loginFailed() throws SQLException {
        when(resultSet.next()).thenReturn(false);

        assertThrows(DatabaseException.class, () -> UserMapper.login("test@test.com", "password", connectionPool));
    }

    @Test
    void createUserSuccessful() throws SQLException, DatabaseException {
        when(preparedStatement.executeUpdate()).thenReturn(1);

        assertDoesNotThrow(() -> UserMapper.createuser("John", "Doe", "john@doe.com", 12345, "Address", false, "password", 1234567890, connectionPool));
    }

    @Test
    void createUserFailed() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        assertThrows(DatabaseException.class, () -> UserMapper.createuser("John", "Doe", "john@doe.com", 12345, "Address", false, "password", 1234567890, connectionPool));
    }

    @Test
    void searchUserSuccessful() throws SQLException, DatabaseException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt(anyString())).thenReturn(1);
        when(resultSet.getString(anyString())).thenReturn("test@test.com");

        User user = UserMapper.searchUser("test@test.com", connectionPool);

        assertNotNull(user);
        assertEquals(1, user.getId());
    }
    


}