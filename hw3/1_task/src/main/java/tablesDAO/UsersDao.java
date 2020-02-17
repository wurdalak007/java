package tablesDAO;

import databaseWORK.ConnectionSource;
import lombok.AllArgsConstructor;
import tables_data.Users;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class UsersDao {
    private final ConnectionSource source;

    private Users createUser(ResultSet resultSet) throws SQLException {
        return new Users(resultSet.getString("ip"),
                resultSet.getString("browser"),
                resultSet.getBoolean("sex"),
                resultSet.getInt("age"));
    }

    public void saveUsers(Collection<Users> users) throws SQLException {
        source.preparedStatement("insert into Users (ip, browser, sex, age) values (?, ?, ?, ?)", insertConference -> {
            for (Users user : users) {
                insertConference.setString(1, user.getIp());
                insertConference.setString(2, user.getBrowser());
                insertConference.setBoolean(3, user.getSex());
                insertConference.setInt(4, user.getAge());
                insertConference.execute();
            }
        });
    }

    public Set<Users> getUsers() throws SQLException {
        return source.statement(stmt -> {
            Set<Users> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from Users");
            while (resultSet.next()) {
                result.add(createUser(resultSet));
            }
            return result;
        });
    }
}