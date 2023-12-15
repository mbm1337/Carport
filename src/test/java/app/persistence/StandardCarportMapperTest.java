package app.persistence;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import app.exceptions.DatabaseException;
import app.entities.StandardCarport;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class StandardCarportMapperTest {

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
    void getCarportByIdReturnsCarportWhenFound() throws SQLException, DatabaseException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getString("merchandiser")).thenReturn("Test Merchandiser");
        when(resultSet.getString("productname")).thenReturn("Test Product");
        when(resultSet.getInt("price")).thenReturn(1000);
        when(resultSet.getString("description")).thenReturn("Test Description");

        StandardCarport carport = StandardCarportMapper.getCarportById(1, connectionPool);

        assertNotNull(carport);
        assertEquals("Test Merchandiser", carport.getMerchandiser());
        assertEquals("Test Product", carport.getProductName());
        assertEquals(1000, carport.getPrice());
        assertEquals("Test Description", carport.getDescription());
    }


    @Test
    void getAllCarportsReturnsListOfCarports() throws SQLException, DatabaseException {
        when(resultSet.next()).thenReturn(true, true, false);
        when(resultSet.getInt("id")).thenReturn(1, 2);
        when(resultSet.getString("merchandiser")).thenReturn("Test Merchandiser 1", "Test Merchandiser 2");
        when(resultSet.getString("productname")).thenReturn("Test Product 1", "Test Product 2");
        when(resultSet.getInt("price")).thenReturn(1000, 2000);
        when(resultSet.getString("description")).thenReturn("Test Description 1", "Test Description 2");

        List<StandardCarport> carports = StandardCarportMapper.getAllCarports(connectionPool);

        assertNotNull(carports);
        assertEquals(2, carports.size());
        assertEquals("Test Merchandiser 1", carports.get(0).getMerchandiser());
        assertEquals("Test Product 1", carports.get(0).getProductName());
        assertEquals(1000, carports.get(0).getPrice());
        assertEquals("Test Description 1", carports.get(0).getDescription());
        assertEquals("Test Merchandiser 2", carports.get(1).getMerchandiser());
        assertEquals("Test Product 2", carports.get(1).getProductName());
        assertEquals(2000, carports.get(1).getPrice());
        assertEquals("Test Description 2", carports.get(1).getDescription());
    }

}