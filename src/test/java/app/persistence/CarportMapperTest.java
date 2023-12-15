package app.persistence;

import app.entities.*;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.CarportMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CarportMapperTest {

    @Mock
    private ConnectionPool connectionPool;

    @Mock
    private Connection connection;

    @Mock
    private PreparedStatement ps;

    @Mock
    private ResultSet rs;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        when(connectionPool.getConnection()).thenReturn(connection);
        when(connection.prepareStatement(anyString())).thenReturn(ps);
        when(ps.executeQuery()).thenReturn(rs);
    }


    @Test
    public void getCarportWidthReturnsCorrectWidths() throws SQLException, DatabaseException {
        when(rs.next()).thenReturn(true, true, false);
        when(rs.getInt("id")).thenReturn(1,2);
        when(rs.getInt("width")).thenReturn(500, 600);

        List<CarportWidth> widths = CarportMapper.getCarportWidth(connectionPool);

        assertEquals(2, widths.size());
        assertEquals(500, widths.get(0).getWidth());
        assertEquals(600, widths.get(1).getWidth());
    }

    @Test
    public void getCarportWidthThrowsDatabaseExceptionWhenSQLExceptionOccurs() throws SQLException {
        when(connectionPool.getConnection()).thenThrow(SQLException.class);

        assertThrows(DatabaseException.class, () -> CarportMapper.getCarportWidth(connectionPool));
    }
}