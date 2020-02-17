package tables_data;

public class Logs {
    private final String ip;
    private final String time;
    private final String request;
    private final int size;
    private final int status;
    private final String browser;

    public Logs(String ip, String time, String request, int size, int status, String browser) {

        this.ip = ip;
        this.time = time;
        this.request = request;
        this.size = size;
        this.status = status;
        this.browser = browser;
    }

    public String getIp() {
        return ip;
    }

    public String getTime() {
        return time;
    }

    public String getRequest() {
        return request;
    }

    public int getSize() {
        return size;
    }

    public int getStatus() {
        return status;
    }

    public String getBrowser() {
        return browser;
    }
}

