package advisor.service;

import advisor.domain.Category;
import advisor.domain.FeaturedPlaylist;
import advisor.domain.NewRelease;
import advisor.domain.Playlist;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;

public class JSONParser {

    private static JSONParser instance = null;

    private JSONParser() {
    }

    public static JSONParser getInstance() {
        if (instance == null) instance = new JSONParser();
        return instance;
    }

    public String parseAccessToken(String jsonAccessToken) {
        JsonObject jo = JsonParser.parseString(jsonAccessToken).getAsJsonObject();
        String accessToken = jo.get("access_token").getAsString();
        return accessToken;
    }

    public List<FeaturedPlaylist> parseFeatured(String jsonFeatured) {
        JsonObject jo = JsonParser.parseString(jsonFeatured).getAsJsonObject();
        JsonObject playlistsObject = jo.getAsJsonObject("playlists");
        List<FeaturedPlaylist> playlists = new ArrayList<>();
        for (JsonElement item :
             playlistsObject.getAsJsonArray("items")) {
            FeaturedPlaylist featuredPlaylist = new FeaturedPlaylist();
            JsonObject obj = item.getAsJsonObject();
            featuredPlaylist.setName(obj.get("name").getAsString());
            JsonObject externalUrls = obj.getAsJsonObject("external_urls");
            featuredPlaylist.setLink(externalUrls.get("spotify").getAsString());
            playlists.add(featuredPlaylist);
        }
        return playlists;
    }

    public List<NewRelease> parseNewReleases(String newReleasesJson) {
        JsonObject jo = JsonParser.parseString(newReleasesJson).getAsJsonObject();
        JsonObject albumsObj = jo.getAsJsonObject("albums");
        List<NewRelease> releases = new ArrayList<>();
        for (JsonElement item :
                albumsObj.getAsJsonArray("items")) {
            NewRelease newRelease = new NewRelease();
            JsonObject itemObj = item.getAsJsonObject();
            List<String> artists = new ArrayList<>();
            for (JsonElement artist :
                 itemObj.getAsJsonArray("artists")) {
                artists.add(artist.getAsJsonObject().get("name").getAsString());
            }
            newRelease.setArtists(artists);
            newRelease.setName(itemObj.get("name").getAsString());
            newRelease.setLink(itemObj.getAsJsonObject("external_urls").get("spotify").getAsString());
            releases.add(newRelease);
        }
        return releases;
    }

    public List<Category> parseCategories(String categoriesJson) {
        JsonObject jo = JsonParser.parseString(categoriesJson).getAsJsonObject();
        JsonObject categoriesObj = jo.getAsJsonObject("categories");
        List<Category> categories = new ArrayList<>();
        for (JsonElement item :
                categoriesObj.getAsJsonArray("items")) {
            Category category = new Category();
            JsonObject itemObj = item.getAsJsonObject();
            category.setId(itemObj.get("id").getAsString());
            category.setName(itemObj.get("name").getAsString());
            categories.add(category);
        }
        return categories;
    }

    public List<Playlist> parseSingleCategory(String categoryJson) {
        JsonObject jo = JsonParser.parseString(categoryJson).getAsJsonObject();
        JsonObject playlistsObj = jo.getAsJsonObject("playlists");
        List<Playlist> playlists = new ArrayList<>();
        for (JsonElement item :
                playlistsObj.getAsJsonArray("items")) {
            Playlist playlist = new Playlist();
            JsonObject itemObj = item.getAsJsonObject();
            playlist.setLink(itemObj.getAsJsonObject("external_urls").get("spotify").getAsString());
            playlist.setName(itemObj.get("name").getAsString());
            playlists.add(playlist);
        }
        return playlists;
    }

    public String parseApiError(String apiErrorJson) {
        JsonObject jo = JsonParser.parseString(apiErrorJson).getAsJsonObject();
        JsonObject errorObj = jo.getAsJsonObject("error");
        return errorObj.get("message").getAsString();
    }
}
