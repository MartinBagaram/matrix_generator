/**
 * Class for manipulating the databse. Establishes the connection and creates
 * table on demand and maybe other things
 * @author Martin B. Bagaram
 * @since  November 2018
 */
package database.connection;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connector {
    private static final String USER = "dbuser";
    private static final String PASSWORD = "password";
    private static final String CONN = "jdbc:mysql://localhost/dbname";
    private static final String SQLCON = "jdbc:sqlite:";

    /**
     * Establish the connection with the database.
     * @parm the database string. May specify <b> the full path of the database</b>
     * @return Connection <i> indicating the connection to the sqlite database</i>
     *  will return <i>null</i> if the connection fails and the exception is not handled
     * @throws SQLException
     */
    public Connection getConnection(String dbString) throws SQLException {
        try {
            Class.forName("org.sqlite.JDBC");
            //DriverManager.registerDriver(new JDBC());
            return DriverManager.getConnection(SQLCON + dbString);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return  null;
    }



}
