
package worckingWithData;

import tables_data.IPRegions;
import tables_data.Logs;
import tables_data.Users;

import java.util.ArrayList;
import java.util.Collection;

public class ParseData {
    public Collection<Users> parseUsers (String content) {
        Collection<Users> users = new ArrayList<>();
        String[] lines = content.split("\n");

        for (String line : lines) {
            String[] data = line.split("\t");
            Boolean sex = false;
            if (data[2].equals("male")) {
                sex = true;
            }
            Users user = new Users(data[0], data[1], sex, Integer.parseInt(data[3]));
            users.add(user);
        }

        return users;
    }


    public Collection<IPRegions> parseRegions (String content) {
        Collection<IPRegions> regions = new ArrayList<>();
        String[] lines = content.split("\n");

        for (String line : lines) {
            String[] last = line.split("\t");
            IPRegions region = new IPRegions(last[0], last[1]);
            regions.add(region);
        }

        return regions;
    }


    public Collection<Logs> parseLogs (String content) {
        Collection<Logs> logs = new ArrayList<>();
        String[] lines = content.split("\n");

        for (String line : lines) {
            String[] last = line.split(("\t\t\t"));
            String ip = last[0];

            last = last[1].split("\t");
            logs.add(new Logs(ip, last[0].substring(0, 8), last[1], Integer.parseInt(last[2]), Integer.parseInt(last[3]), last[4].split(" ")[0]));
        }
        return logs;
    }
}
