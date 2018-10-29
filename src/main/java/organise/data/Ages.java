package organise.data;

import database.connection.Connector;

import java.sql.*;

public class Ages {

    private static String db = "db_example.db";
    private final static int PLANNING_HORIZON = 40;
    private final static int PERIOD_LENGTH = 10;
    private static String sql = "CREATE TABLE IF NOT EXISTS ages (\n"
                        + "standid TEXT, \n" //PRIMARY KEY
                        + "si_class INTEGER ,\n"
                        + "forest_type TEXT, \n"
                        + "ageef REAL\n"
                        + ");";

    /**
     *
     * @param sql The sql query for creation of a new table
     * @throws SQLException if there is something wrong
     */
    public static void createNewTable(String sql) throws SQLException {
        Connector c = new Connector();
        Connection conn = c.getConnection(db);
        Statement statement =  conn.createStatement();
        statement.execute(sql);

        for (int i = 0; i <= PLANNING_HORIZON; i += PERIOD_LENGTH) {
            addColumnToTable(conn, i/PERIOD_LENGTH);
        }
        populateTableWithInitialValues(conn);
    }

    /**
     * Populates the table of ages by initial values that were in the FVS table
     * It adds as well columns for periods and the ages corresponging<br>
     * @param conn Connection to the database
     * @throws SQLException if there is a problem
     */
    private static void populateTableWithInitialValues(Connection conn) throws SQLException {
        String select =  "SELECT standid, si_class, forest_type, ageef FROM FVS_COMPUTE";
        Statement stmt = conn.createStatement();
        ResultSet resultSet = stmt.executeQuery(select);

        // Put the results in the table
        //String insertion = "INSERT INTO ages VALUES (?, ?, ?, ?)";
        //PreparedStatement pstmt = conn.prepareStatement(insertion);

        while (resultSet.next()) {
            String insertion = "INSERT INTO ages VALUES (";
            /*pstmt.setString(1, resultSet.getString("standid"));
            pstmt.setInt(2, resultSet.getInt("si_class"));
            pstmt.setString(3, resultSet.getString("forest_type"));
            double age_initial = resultSet.getDouble("ageef");
            pstmt.setDouble(4, age_initial);
            String insert_suit = ")";
            */
            insertion += "\"" + resultSet.getString("standid") + "\"";
            insertion += "," + resultSet.getInt("si_class");
            insertion += ", \"" + resultSet.getString("forest_type") + "\"";
            double age_initial = resultSet.getDouble("ageef");
            insertion += "," + age_initial;
            double age = age_initial + PLANNING_HORIZON ;
            insertion += "," +  age;
            for (int i = PERIOD_LENGTH; i <= PLANNING_HORIZON; i += PERIOD_LENGTH) {
                age = PLANNING_HORIZON - i;
                insertion += "," +  age;
            }
            insertion += ");";
            //pstmt.execute();
            conn.createStatement().execute(insertion);
        }
    }


    /**
     * Add columns that will contain the age.
     * The method is necessary because we don't know in advance how
     *  many columns we will need.
     *  need to be fixed so it behaves well when the column already exists
     *  Right now will just fail
     * @param conn The Connection to the database
     * @param harvestPeriod period in which the harvest happens 0 for no harvest
     * @throws SQLException if a problem occurs
     */
    private static void addColumnToTable(Connection conn, int harvestPeriod) throws SQLException {
        String alteration = "ALTER TABLE ages ADD period" + harvestPeriod +  " REAL";
        conn.createStatement().execute(alteration);
    }

    public static void main(String[] args) throws SQLException {
        createNewTable(sql);
    }
}
