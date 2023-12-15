package testDB.app.persistence;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import testDB.app.Database;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class AdminMapperTest {

    private final static String TESTUSER = "postgres";
    private final static String TESTPASSWORD = "postgres";
    private final static String TESTURL = "jdbc:postgresql://localhost:5432/carport_test?serverTimezone=CET&useSSL=false&allowPublicKeyRetrieval=true";
    private static Database db;


    @BeforeAll
    public static void setUpClass(){
        try {
            //Creating  database object
            db = new Database(TESTUSER, TESTPASSWORD, TESTURL);

        } catch (ClassNotFoundException e) {
            //Fails if no class is found
            e.printStackTrace();
        }

    }


    @BeforeEach
    void setUp(){
        try (Connection testConnection = db.connect()) {
            //Resetting database
            try (Statement stmt = testConnection.createStatement() ) {
                // Remove all rows from all tables
                stmt.execute("DELETE FROM public.carport_calculator");
                stmt.execute("DELETE FROM public.carport_length");
                stmt.execute("DELETE FROM public.carport_width");
                stmt.execute("DELETE FROM public.carports");
                stmt.execute("DELETE FROM public.city");
                stmt.execute("DELETE FROM public.materials");
                stmt.execute("DELETE FROM public.orderdetails");
                stmt.execute("DELETE FROM public.orders");
                stmt.execute("DELETE FROM public.roof");
                stmt.execute("DELETE FROM public.shed_length");
                stmt.execute("DELETE FROM public.shed_width");
                stmt.execute("DELETE FROM public.shipping");
                stmt.execute("DELETE FROM public.user");

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
        assertNotNull(db.connect());


    }



}