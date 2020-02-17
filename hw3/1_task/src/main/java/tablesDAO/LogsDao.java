package tablesDAO;

import databaseWORK.ConnectionSource;
import lombok.AllArgsConstructor;
import tables_data.Logs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class LogsDao {
    private final ConnectionSource source;

    public Logs createLog(ResultSet resultSet) throws SQLException {
        return new Logs(resultSet.getString("ip"),
                resultSet.getString("time"),
                resultSet.getString("request"),
                resultSet.getInt("size"),
                resultSet.getInt("status"),
                resultSet.getString("browser"));
    }

    public void saveLogs(Collection<Logs> logs) throws SQLException {
        source.preparedStatement("insert into Logs (ip, time, request, size, status, browser) values (?, ?, ?, ?, ?, ?)", insertConference -> {
            for (Logs log : logs) {
                insertConference.setString(1, log.getIp());
                insertConference.setString(2, log.getTime());
                insertConference.setString(3, log.getRequest());
                insertConference.setInt(4, log.getSize());
                insertConference.setInt(5, log.getStatus());
                insertConference.setString(6, log.getBrowser());
                insertConference.execute();
            }
        });
    }

    public Set<Logs> getLogs() throws SQLException {
        return source.statement(stmt -> {
            Set<Logs> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from Logs");
            while (resultSet.next()) {
                result.add(createLog(resultSet));
            }
            return result;
        });
    }


}