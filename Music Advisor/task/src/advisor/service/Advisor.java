package advisor.service;

import advisor.config.Config;
import advisor.config.CurrentUser;
import advisor.domain.Category;
import advisor.server.Server;
import advisor.storage.Buffer;
import advisor.utilities.Page;
import advisor.utilities.PagedList;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class Advisor {

    private static Advisor instance = null;

    private Advisor() {
    }

    public static Advisor getInstance() {
        if (Objects.isNull(instance)) {
            instance = new Advisor();
        }
        return instance;
    }

    public void auth() throws InterruptedException {
        Server.getInstance().start();
        System.out.println("use this link to request the access code:");
        System.out.println(Config.AUTH_LINK);
        System.out.println("waiting for code");
        synchronized (System.out) {
            while (CurrentUser.getInstance().getCode() == null) {
                System.out.wait();
            }
        }
        System.out.println("code received");
        Server.getInstance().shutdown();
        CurrentUser.getInstance().setAccessToken(JSONParser.getInstance().parseAccessToken(getAccessTokenForCurrentUser()));
    }

    private String getAccessTokenForCurrentUser() {
        if (CurrentUser.getInstance().getCode() == null) {
            throw new IllegalArgumentException("There is no code available");
        }
        HttpClient client = HttpClient.newBuilder().build();
        HttpRequest request = HttpRequest.newBuilder()
                .header("Content-Type", "application/x-www-form-urlencoded")
                .uri(URI.create(Config.ACCESS_SERVER_POINT + "/api/token"))
                .POST(HttpRequest.BodyPublishers.ofString("client_id=" + Config.CLIENT_ID +
                        "&client_secret=" + Config.CLIENT_SECRET +
                        "&grant_type=authorization_code" +
                        "&" + CurrentUser.getInstance().getCode() +
                        "&redirect_uri=" + Config.REDIRECT_URI))
                .build();
        return getResponse(request);
    }

    private String getResponse(HttpRequest request) {
        HttpClient client = HttpClient.newBuilder().build();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getFeatured() {
        if (!CurrentUser.getInstance().authorized()) {
            return "Please, provide access for application.";
        }
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + CurrentUser.getInstance().getAccessToken())
                .uri(URI.create(Config.API_PATH + Config.API_FEATURED_PATH))
                .GET()
                .build();
        Buffer.getInstance().clear();
        Buffer.getInstance().addData(new PagedList<>(JSONParser.getInstance().parseFeatured(getResponse(httpRequest))));
        return PagingService.getInstance().next();
    }


    public String getNewReleases() {
        if (!CurrentUser.getInstance().authorized()) {
            return "Please, provide access for application.";
        }
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + CurrentUser.getInstance().getAccessToken())
                .uri(URI.create(Config.API_PATH + Config.API_NEW_RELEASES_PATH))
                .GET()
                .build();
        Buffer.getInstance().clear();
        Buffer.getInstance().addData(new PagedList<>(JSONParser.getInstance().parseNewReleases(getResponse(httpRequest))));
        return PagingService.getInstance().next();
    }

    public String getCategories() {
        if (!CurrentUser.getInstance().authorized()) {
            return "Please, provide access for application.";
        }
        Buffer.getInstance().clear();
        Buffer.getInstance().addData(new PagedList<>(getListOfCategories()));
        return PagingService.getInstance().next();
    }

    private List<Category> getListOfCategories() {
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + CurrentUser.getInstance().getAccessToken())
                .uri(URI.create(Config.API_PATH + Config.API_CATEGORIES_PATH))
                .GET()
                .build();
        return JSONParser.getInstance().parseCategories(getResponse(httpRequest));
    }

    public String getPlaylist(String categoryName) {
        if (!CurrentUser.getInstance().authorized()) {
            return "Please, provide access for application.";
        }
        List<Category> categories = getListOfCategories();
        if (categories.isEmpty()) {
            System.out.println("empty list");
            return "Unknown category name.";
        }
        Optional<Category> category = categories.stream().filter(e -> e.getName().equals(categoryName)).findAny();
        String categoryId = category.isPresent() ? category.get().getId() : "";
        if ("".equals(categoryId)) {
            return "Unknown category name.";
        }
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .header("Authorization", "Bearer " + CurrentUser.getInstance().getAccessToken())
                .uri(URI.create(Config.API_PATH + Config.API_CATEGORIES_PATH + "/" + categoryId + "/playlists"))
                .GET()
                .build();
        String response = getResponse(httpRequest);
        try {
            Buffer.getInstance().clear();
            Buffer.getInstance().addData(new PagedList<>(JSONParser.getInstance().parseSingleCategory(response)));
            return PagingService.getInstance().next();
        } catch (NullPointerException e) {
            return JSONParser.getInstance().parseApiError(response);
        }

    }
}
