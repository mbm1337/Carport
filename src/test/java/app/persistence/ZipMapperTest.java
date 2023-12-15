package app.persistence;

import app.entities.City;
import app.exceptions.DatabaseException;
import app.persistence.ConnectionPool;
import app.persistence.ZipMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class ZipMapperTest {

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
        when(resultSet.getInt("zip")).thenReturn(1234, 5678);
        when(resultSet.getString("city_name")).thenReturn("Test City 1", "Test City 2");
    }

    @Test
    public void getCityByZipReturnsCityName() throws SQLException, DatabaseException {
        when(resultSet.next()).thenReturn(true);

        String result = ZipMapper.getCityByZip(1234, connectionPool);

        assertEquals("Test City 1", result);
    }

    @Test
    public void getCityByZipThrowsDatabaseExceptionForSqlException() throws SQLException {
        when(connectionPool.getConnection()).thenThrow(SQLException.class);

        assertThrows(DatabaseException.class, () -> ZipMapper.getCityByZip(1234, connectionPool));
    }

    @Test
    public void citiesByZipReturnsListOfCities() throws SQLException, DatabaseException {
        when(resultSet.next()).thenReturn(true, true, false);

        List<City> result = ZipMapper.citiesbyzip(connectionPool);

        assertEquals(2, result.size());
        assertEquals(1234, result.get(0).getZip());
        assertEquals("Test City 1", result.get(0).getCity());
        assertEquals(5678, result.get(1).getZip());
        assertEquals("Test City 2", result.get(1).getCity());
    }

    @Test
    public void citiesByZipThrowsDatabaseExceptionForSqlException() throws SQLException {
        when(connectionPool.getConnection()).thenThrow(SQLException.class);

        assertThrows(DatabaseException.class, () -> ZipMapper.citiesbyzip(connectionPool));
    }
}