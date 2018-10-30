package organise.data;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The class creates a table for potential mean annual increment for each period period
 * this class will be the support for the class of volumes harvested if done in each period
 * The class requires two fields that are the planning horizon and the length of each period
 * <p>
 *     The class is crucial as well when it comes to creating scenarios. This class can be considered as
 *     the base scenario and all others may derive from it in order to create scenarios
 * </p>
 * @author Martin B. Bagaram
 * @since  November 2018
 */
public class PMAIPerPeriod {
    private final int PLANNING_HORIZON;
    private final int PERIOD_LENGTH;

    /**
     * Construct PMAI class by initializing the final fields
     *
     * @param planningHorizon the length of the planning horizon
     * @param periodLength the length of a period
     */
    public PMAIPerPeriod(int planningHorizon, int periodLength) {
        PLANNING_HORIZON = planningHorizon;
        PERIOD_LENGTH = periodLength;
    }

    /**
     *
     * @param conn
     * @throws SQLException
     */
    public void creatTablePMAI(Connection conn) throws SQLException {
        StringBuilder sql = new StringBuilder("CREATE TABLE pmai_periods("
                                                    + "standid text");

        for (int i = 0; i <= PLANNING_HORIZON; i += PERIOD_LENGTH) {
            int period = i / PERIOD_LENGTH;
            sql.append(", period" + period);
        }
        sql.append(");");
        conn.createStatement().execute(sql.toString());
    }
}
