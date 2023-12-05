package app.exceptions;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DatabaseException extends Exception {
    public DatabaseException(String message, SQLException e) {
        super(message);
    }

    public DatabaseException(String userMessage, String systemMessage) {
        super(userMessage);
        Logger.getLogger("web").log(Level.SEVERE, systemMessage);
    }

    public DatabaseException(String s) {
    }
}
