package advisor.domain;

import java.util.List;

public class NewRelease {

    private String name;
    private String link;
    private List<String> artists;

    public NewRelease() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public List<String> getArtists() {
        return artists;
    }

    public void setArtists(List<String> artists) {
        this.artists = artists;
    }

    @Override
    public String toString() {
        return this.name + "\n"
                + this.artists.toString() + "\n"
                + this.link + "\n";
    }
}
