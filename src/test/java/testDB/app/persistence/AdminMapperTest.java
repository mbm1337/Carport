package testDB.app.persistence;

import app.entities.Admin;
import app.entities.Order;
import app.entities.User;
import app.persistence.AdminMapper;
import app.persistence.ConnectionPool;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class AdminMapperTest {

    private final static String TESTUSER = "postgres";
    private final static String TESTPASSWORD = "postgres";
    private final static String TESTURL = "jdbc:postgresql://localhost:5432/carport_test?serverTimezone=CET&useSSL=false&allowPublicKeyRetrieval=true";

    private final static String TESTDB = "carport_test";
    private static ConnectionPool connectionPool;


    @BeforeAll
    public static void setUpClass(){
        try {
            //Creating  database object
            connectionPool = ConnectionPool.getInstance(TESTUSER, TESTPASSWORD, TESTURL, TESTDB);

        } catch (Exception e) {
            //Fails if no class is found
            e.printStackTrace();
        }
    }


    @BeforeEach
    void setUp(){
        try (Connection testConnection = connectionPool.getConnection()) {
            //Resetting database
            try (Statement stmt = testConnection.createStatement() ) {
                // Remove all rows from all tables
                stmt.execute("DELETE FROM public.carport_calculator");
                stmt.execute("DELETE FROM public.carport_length");
                stmt.execute("DELETE FROM public.carport_width");
                stmt.execute("DELETE FROM public.carports");
                stmt.execute("DELETE FROM public.orderdetails");
                stmt.execute("DELETE FROM public.orders");
                stmt.execute("DELETE FROM public.materials");
                stmt.execute("DELETE FROM public.roof");
                stmt.execute("DELETE FROM public.shed_length");
                stmt.execute("DELETE FROM public.shed_width");
                stmt.execute("DELETE FROM public.shipping");
                stmt.execute("DELETE FROM public.user");
                stmt.execute("DELETE FROM public.city");


                stmt.execute("INSERT INTO public.city VALUES " +
                        "(1000,'TestCity1','Testregion1','Testkommune1')," +
                        "(2000,'TestCity2','Testregion2','Testkommune2')," +
                        "(3000,'TestCity3','Testregion3','Testkommune3')");

                stmt.execute("INSERT INTO public.materials VALUES " +
                        "(1,'TestMaterial1','Testype1','TestSize1','TestUnit1',100,100,50)," +
                        "(2,'TestMaterial2','Testype2','TestSize2','TestUnit2',200,200,100)," +
                        "(3,'TestMaterial3','Testype3','TestSize3','TestUnit3',300,300,150)");

                stmt.execute("INSERT INTO public.user VALUES " +
                        "(1,'Ole','Hansen','email1',1000,'adress1',false,'1234',12345678)," +
                        "(2,'Kim','Jensen','email2',2000,'adress2',false,'5678',87654321)," +
                        "(3,'Tom','Olsen','email3',3000,'adress3',true,'9012',65735274)");

                stmt.execute("INSERT INTO public.orders VALUES " +
                        "(1,'01/01/2011','Ordered','comment1' ,11111,1,11111,11111)," +
                        "(2,'02/02/2022','Shipped','comment2',22222,2,22222,22222)," +
                        "(3,'03/03/2033','Delivered','comment3',33333,3,33333,33333)");

                stmt.execute("INSERT INTO public.orderdetails VALUES " +
                        "(1,10,3)" + ",(2,20,3)" + ",(3,30,3)");

            } catch (SQLException throwables){
                fail("Didnt execute");
            }

        } catch (SQLException throwables) {
            //Fails if connection to database fails
            fail("Database connection failed");
        }
    }

    @Test
    void testConnection() throws SQLException {
        assertNotNull(connectionPool.getConnection());
    }

    @Test
    void getOrderDetails() throws SQLException {
        Admin orderDetails = AdminMapper.getOrderDetails(1, connectionPool);
        assertEquals(1, orderDetails.getOrderId());
        assertEquals("2011-01-01", orderDetails.getOrderDate());
        assertEquals("Ordered", orderDetails.getStatus());
        assertEquals("comment1", orderDetails.getComments());
        assertEquals(11111, orderDetails.getOrderPrice());
        assertEquals(1, orderDetails.getUserId());
        }

    @Test
    void getUsersAndOrders() throws SQLException {

        Map<User, List<Order>> usersAndOrders = AdminMapper.getUsersAndOrders(connectionPool);
        assertEquals(3, usersAndOrders.size());
        Set<User> userkeys = usersAndOrders.keySet();
        // get user with id 1 in userkeys
        User u1 = userkeys.stream().filter(user -> user.getId() == 1).findFirst().get();
        assertEquals("Ole", u1.getFirstName());
        assertEquals("Hansen", u1.getLastName());
        assertEquals("email1", u1.getEmail());
        assertEquals(1000, u1.getZip());
        assertEquals("adress1", u1.getAddress());
        assertFalse(u1.isAdmin());
        assertEquals(12345678, u1.getPhoneNumber());
    }
}