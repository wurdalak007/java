package program.main;

import databaseWORK.ConnectionSource;
import databaseWORK.DBInit;
import org.apache.commons.io.IOUtils;
import org.h2.jdbcx.JdbcConnectionPool;
import solver.Requester;
import tablesDAO.IPRegionsDao;
import tablesDAO.LogsDao;
import tablesDAO.UsersDao;
import tables_data.IPRegions;
import tables_data.Logs;
import tables_data.Users;
import worckingWithData.ParseData;
import yandex.files.YandexDiskGetFile;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Properties;

public class Program {


    public static void main(String[] args) throws SQLException, IOException {
        final Properties properties = new Properties();
        properties.load(new FileInputStream("config.properties"));
        String login = properties.getProperty("login");
        String password = properties.getProperty("password");
        YandexDiskGetFile yandexDisk = new YandexDiskGetFile(login, password);

        InputStream stream = yandexDisk.doGet(properties.getProperty("ipDataM"));
        InputStream stream1 = yandexDisk.doGet(properties.getProperty("userData"));
        InputStream stream2 = yandexDisk.doGet(properties.getProperty("LogsLM"));


        String ipData = IOUtils.toString(stream, "UTF-8");
        String userData = IOUtils.toString(stream1, "UTF-8");
        String logsData = IOUtils.toString(stream2, "UTF-8");

        ConnectionSource connect = new ConnectionSource(JdbcConnectionPool.create("jdbc:h2:mem:database;DB_CLOSE_DELAY=1", "", ""));
        DBInit db = new DBInit(connect);
        db.create();


        ParseData parser = new ParseData();

        Collection <IPRegions> regions = parser.parseRegions(ipData);
        Collection<Users> users = parser.parseUsers(userData);
        Collection <Logs> logs = parser.parseLogs(logsData);

        IPRegionsDao tableReg = new IPRegionsDao(connect);
        UsersDao tableUsers = new UsersDao(connect);
        LogsDao tableLogs = new LogsDao(connect);

        tableReg.saveRegions(regions);
        tableUsers.saveUsers(users);
        tableLogs.saveLogs(logs);

        Requester requester = new Requester(connect, tableReg, tableUsers, tableLogs);


        System.out.print(tableUsers.getUsers().size());
    }

}