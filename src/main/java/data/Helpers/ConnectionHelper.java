package data.Helpers;

import com.sybase.jdbc4.jdbc.SybDataSource;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Lebel on 01/04/2014.
 */
public class ConnectionHelper {
    private static final int CONFIG_LIVE = 0x37;
    private static final int CONFIG_TEST = 0x21;
    private static final int CONFIG_LOCAL = 0x41;

    public Connection getConnection() {
        Connection con = null;
        SybDataSource ds = new SybDataSource();

        try {
            ds = getDefaultConfig(CONFIG_LIVE);  //set configuration to connect to
            con = ds.getConnection();
            if (con.isClosed()) {
                con.abort(null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    private SybDataSource getDefaultConfig(int config) {
        return setConfig(config);
    }

    private SybDataSource setConfig(int configuration) {
        SybDataSource newConfig = new SybDataSource();
        newConfig.setUser("dba");
        newConfig.setPassword("sql");
        switch (configuration) {
            case CONFIG_TEST:
                newConfig.setServerName("192.168.10.248");
                newConfig.setPortNumber(2639);
                newConfig.setDatabaseName("proper3");
                break;
            case CONFIG_LIVE:
                newConfig.setServerName("192.168.10.246");
                newConfig.setPortNumber(2638);
                newConfig.setDatabaseName("proper");
                break;
            case CONFIG_LOCAL:
                newConfig.setServerName("192.168.10.96");
                newConfig.setPortNumber(2638);
                newConfig.setDatabaseName("proper3");
        }
        newConfig.setBE_AS_JDBC_COMPLIANT_AS_POSSIBLE("true");
        newConfig.setFAKE_METADATA("true");
        return newConfig;
    }
}


