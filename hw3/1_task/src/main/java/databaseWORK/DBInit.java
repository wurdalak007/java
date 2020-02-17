package databaseWORK;

import lombok.AllArgsConstructor;
import program.main.Program;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;
import java.util.stream.Collectors;


@AllArgsConstructor
public class DBInit {
    final ConnectionSource source;

//    public DBInit(ConnectionSource connect) {
//        source = connect;
//    }


    private String getSQL(String name) throws IOException {
        try (BufferedReader br = new BufferedReader(
                new InputStreamReader(
                        Program.class.getClassLoader().getResourceAsStream(name),
                        StandardCharsets.UTF_8))) {
            return br.lines().collect(Collectors.joining("\n"));
        }
    }

    public void create() throws SQLException, IOException {

        String sql = getSQL("createDB.sql");
        source.statement(stmt -> {
            stmt.execute(sql);
        });
    }
}