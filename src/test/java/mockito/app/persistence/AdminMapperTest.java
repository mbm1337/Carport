package mockito.app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.AdminMapper;
import app.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AdminMapperTest {

    @Mock
    private ConnectionPool connectionPool;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;
    @Mock
    private ResultSet resultSet;
    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(connectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);
        when(preparedStatement.executeQuery()).thenReturn(resultSet);
    }

    @Test
    public void getUsersAndOrdersReturnsExpectedResult() throws SQLException {
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt(anyString())).thenReturn(1);
        when(resultSet.getString(anyString())).thenReturn("test");

        Map<User, List<Order>> result = AdminMapper.getUsersAndOrders(connectionPool);

        assertFalse(result.isEmpty());
        verify(connectionPool, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(2)).next();
    }

    @Test
    public void getMaterialsReturnsExpectedResult() throws SQLException {
        when(resultSet.next()).thenReturn(true, false);
        when(resultSet.getInt(anyString())).thenReturn(1);
        when(resultSet.getString(anyString())).thenReturn("test");
        when(resultSet.getShort(anyString())).thenReturn((short) 1);
        when(resultSet.getDouble(anyString())).thenReturn(1.0);

        List<Material> result = AdminMapper.getMaterials(connectionPool);

        assertFalse(result.isEmpty());
        verify(connectionPool, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeQuery();
        verify(resultSet, times(2)).next();
    }

    @Test
    public void updateMaterialThrowsDatabaseExceptionWhenNoRowsAffected() throws SQLException {
        when(preparedStatement.executeUpdate()).thenReturn(0);

        Material material = new Material(1, "test", "test", "test", "test", (short) 1, 1.0, 1.0);

        assertThrows(DatabaseException.class, () -> AdminMapper.updateMaterial(material, connectionPool));
        verify(connectionPool, times(1)).getConnection();
        verify(connection, times(1)).prepareStatement(anyString());
        verify(preparedStatement, times(1)).executeUpdate();
    }
}