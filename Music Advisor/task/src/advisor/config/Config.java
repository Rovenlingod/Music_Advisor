package advisor.config;

public class Config {

    public static int NUMBER_OF_ENTRIES_ON_A_PAGE = 5;
    public static String API_PATH = "https://api.spotify.com";
    public static final String API_FEATURED_PATH = "/v1/browse/featured-playlists";
    public static final String API_NEW_RELEASES_PATH = "/v1/browse/new-releases";
    public static final String API_CATEGORIES_PATH = "/v1/browse/categories";
    public static final String CLIENT_ID = "";
    public static final String CLIENT_SECRET = "";
    public static final String REDIRECT_URI = "http://localhost:8080";
    public static final int REDIRECT_URI_PORT = 8080;
    public static String ACCESS_SERVER_POINT = "https://accounts.spotify.com";
    public static final String AUTH_PATH = "";
    public static String AUTH_LINK = ACCESS_SERVER_POINT + AUTH_PATH;


    public static void updateLink(String accessPoint) {
        ACCESS_SERVER_POINT = accessPoint;
        AUTH_LINK = accessPoint + AUTH_PATH;
    }

    public static void updateApiLink(String newLink) {
        API_PATH = newLink;
    }

    public static void updateNumberOfEntries(int newNumber) {
        NUMBER_OF_ENTRIES_ON_A_PAGE = newNumber;
    }
}
