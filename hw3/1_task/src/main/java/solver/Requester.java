package solver;

import databaseWORK.ConnectionSource;
import lombok.AllArgsConstructor;
import tablesDAO.IPRegionsDao;
import tablesDAO.LogsDao;
import tablesDAO.UsersDao;
import tables_data.Logs;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;

@AllArgsConstructor
public class Requester {
    private final ConnectionSource source;

    private final IPRegionsDao tableReg;
    private final UsersDao tableUsers ;
    private final LogsDao tableLog;

    public void first(int day) throws SQLException {
        source.preparedStatement("select ip, count(ip) as num from Logs\n" +
                "where Logs.time > ?\n" +
                "group by ip\n" +
                "order by num desc;", insert -> {

            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.DAY_OF_MONTH, (-1)*day);
            String newDay = String.valueOf(calendar.get(Calendar.YEAR));
            newDay += String.valueOf(calendar.get(Calendar.MONTH));
            newDay += String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

            insert.setString(1, newDay);
            ResultSet resultSet = insert.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("ip") + "\t" + resultSet.getInt("num"));
            }
            resultSet.close();
            insert.close();
        });
    }

    public void second() throws SQLException {
        source.statement(stmt -> {
            ResultSet resultSet = stmt.executeQuery("SELECT region, num FROM\n" +
                    "(SELECT region, COUNT(IPRegions.ip) as num from IPRegions\n" +
                    "JOIN Logs on Logs.ip = IPRegions.ip\n" +
                    "GROUP BY region)\n" +
                    "WHERE num >\n" +
                    "    (\n" +
                    "select avg(num) as mid from (\n" +
                    "  SELECT region, COUNT(IPRegions.ip) as num from IPRegions\n" +
                    "    JOIN Logs on Logs.ip = IPRegions.ip\n" +
                    "  GROUP BY region\n" +
                    ") as tmp\n" +
                    "    );"
            );

            while (resultSet.next()) {
                System.out.println(resultSet.getString("region") + "\t" +
                        resultSet.getInt("num")
                );
            }
            resultSet.close();
        });
    }

//    Format YYYYMMDD
    public void third(String day) throws SQLException {
        source.preparedStatement("select top 1 time, \n" +
                "(select count(sex) from Users \n" +
                "Join Logs on Logs.ip = Users.ip \n" +
                "where (sex = true AND time = ?)) as m,\n" +
                "(select count(sex) from Users \n" +
                "Join Logs on Logs.ip = Users.ip \n" +
                "where (sex = false AND time = ?)) as f from Logs;\n", insert -> {
            insert.setString(1, day);
            insert.setString(2, day);
            ResultSet resultSet = insert.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("time") + "\t" +
                        resultSet.getString("m") + "\t" + resultSet.getInt("f"));
            }
            resultSet.close();
            insert.close();
        });
    }


    // Returns (resource, num of visiting, data)
    public void fourth(int A, int B) throws SQLException {
        source.preparedStatement("Select TOP 100 request, count(request) as num, time from Logs\n" +
                "Join Users on Users.ip = Logs.ip\n" +
                "Where (age < ? AND time > ?)\n" +
                "group by request, age\n" +
                "order by num desc;" , insert -> {

            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.DAY_OF_MONTH, (-1)*B);
            String newDay = String.valueOf(calendar.get(Calendar.YEAR));
            newDay += String.valueOf(calendar.get(Calendar.MONTH));
            newDay += String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

            insert.setInt(1, A);
            insert.setString(2, newDay);
            ResultSet resultSet = insert.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("request") + "\t" +
                        "\t" + resultSet.getInt("num") +
                "\t" + resultSet.getString("time"));
            }
            resultSet.close();
            insert.close();
        });
    }


    public void fifth(int N) throws SQLException {
        source.preparedStatement("select TOP 10 region, count(region) as num from Logs\n" +
                "join IPRegions on IPRegions.ip = Logs.ip\n" +
                "where time > ? AND request like '%lenta.ru%'\n" +
                "group by region\n" +
                "order by num desc;" , insert -> {

            Calendar calendar = Calendar.getInstance();

            calendar.add(Calendar.DAY_OF_MONTH, (-1)*N);
            String newDay = String.valueOf(calendar.get(Calendar.YEAR));
            newDay += String.valueOf(calendar.get(Calendar.MONTH));
            newDay += String.valueOf(calendar.get(Calendar.DAY_OF_MONTH));

            insert.setString(1, newDay);
            ResultSet resultSet = insert.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("region") + "\t" +
                        "\t" + resultSet.getInt("num"));
            }
            resultSet.close();
            insert.close();
        });
    }

    public void sixth (String ip, String time, String request, int size, int status, String browser) throws SQLException {
        Collection<Logs> log = new ArrayList<>();
        log.add(new Logs(ip, time, request, size, status, browser));
        tableLog.saveLogs(log);
    }

    public void seventh(String day) throws SQLException {
        source.preparedStatement("delete from Logs\n" +
                "where time < ?;" , insert -> {

            insert.setString(1, day);
            insert.execute();
            insert.close();
        });
    }

    public void eighth(String day) throws SQLException {
        source.preparedStatement("update Logs\n" +
                "set request = REPLACE(request, '.ru', '.com')\n" +
                "where time < ? AND request like '%.ru%';" , insert -> {

            insert.setString(1, day);
            insert.execute();
            insert.close();
        });
    }


}
