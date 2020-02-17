package tablesDAO;

import databaseWORK.ConnectionSource;
import lombok.AllArgsConstructor;
import tables_data.IPRegions;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
public class IPRegionsDao {
    private final ConnectionSource source;

    private IPRegions createRegion(ResultSet resultSet) throws SQLException {
        return new IPRegions(resultSet.getString("ip"),
                resultSet.getString("region"));
    }

    public void saveRegions(Collection<IPRegions> regions) throws SQLException {
        source.preparedStatement("insert into IPRegions (ip, region) values (?, ?)", insertConference -> {
            for (IPRegions r : regions) {
                insertConference.setString(1, r.getIp());
                insertConference.setString(2, r.getRegion());
                insertConference.execute();
            }
        });
    }

    public Set<IPRegions> getRegions() throws SQLException {
        return source.statement(stmt -> {
            Set<IPRegions> result = new HashSet<>();
            ResultSet resultSet = stmt.executeQuery("select * from IPRegions");
            while (resultSet.next()) {
                result.add(createRegion(resultSet));
            }
            return result;
        });
    }
}