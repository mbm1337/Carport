package app.persistence;

import app.persistence.ConnectionPool;
import app.persistence.MaterialMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MaterialMapperTest {


    @Mock
    private ConnectionPool connectionPool;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement ps;

    @Mock
    private ResultSet rs;

    @BeforeEach
    public void setup() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(connectionPool.getConnection()).thenReturn(connection);

    }



    @Test
    void getPrice_returnsPrice_whenIdExists() throws SQLException {

        // Define behavior
        when(connectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(anyString())).thenReturn(100);

        // Call method under test
        int price = MaterialMapper.getPrice(1, connectionPool);

        // Verify result
        assertEquals(100, price);
    }


    @Test
    void getPrice_throwsException_whenSQLExceptionOccurs() throws SQLException {

        // Define behavior
        when(connectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenThrow(SQLException.class);

        // Call method under test and verify exception
        assertThrows(RuntimeException.class, () -> MaterialMapper.getPrice(1, connectionPool));
    }
}