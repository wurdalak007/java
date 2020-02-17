package tables_data;

public class Users {
    private final String ip;
    private final String browser;
    private final Boolean sex; // 1 - male; 0 - female;
    private final int age;

    public Users(String ip, String browser, Boolean sex, int age) {
        this.ip = ip;
        this.browser = browser;
        this.sex = sex;
        this.age = age;
    }


    public int getAge() {
        return age;
    }

    public Boolean getSex() {
        return sex;
    }

    public String getBrowser() {
        return browser;
    }

    public String getIp() {
        return ip;
    }

}
