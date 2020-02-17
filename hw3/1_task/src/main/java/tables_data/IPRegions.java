package tables_data;


public class IPRegions {
    private final String ip;
    private final String region;

    public IPRegions(String ip, String region) {
        this.ip = ip;
        this.region = region;
    }

    public String getIp() {
        return ip;
    }

    public String getRegion() {
        return region;
    }
}

