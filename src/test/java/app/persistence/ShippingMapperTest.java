package app.persistence;

import app.persistence.ConnectionPool;
import app.persistence.ShippingMapper;
import app.entities.Shipping;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ShippingMapperTest {

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
    public void getShippingInfoByZipReturnsCorrectShippingInfo() throws SQLException, DatabaseException {
        when(resultSet.next()).thenReturn(true);
        when(resultSet.getInt("shipping_price")).thenReturn(100);
        when(resultSet.getString("shipping_time")).thenReturn("1 week");

        Shipping shipping = ShippingMapper.getShippingInfoByZip(1001, connectionPool);

        assertNotNull(shipping);
        assertEquals(1001, shipping.getZipCode());
        assertEquals(100, shipping.getShippingPrice());
        assertEquals("1 week", shipping.getShippingTime());
    }


    @Test
    public void getShippingInfoByZipThrowsDatabaseExceptionWhenSQLExceptionOccurs() throws SQLException {
        when(connectionPool.getConnection()).thenThrow(SQLException.class);

        assertThrows(DatabaseException.class, () -> ShippingMapper.getShippingInfoByZip(1001, connectionPool));
    }
}