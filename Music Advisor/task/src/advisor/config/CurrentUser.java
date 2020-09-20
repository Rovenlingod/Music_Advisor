package advisor.config;

import java.util.Objects;

public class CurrentUser {

    private volatile String code;
    private volatile String accessToken;
    private static volatile CurrentUser instance = null;

    private CurrentUser() {
    }

    public static CurrentUser getInstance() {
        if (Objects.isNull(instance)) {
            instance = new CurrentUser();
        }
        return instance;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean authorized() {
        return !Objects.isNull(code);
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

}
