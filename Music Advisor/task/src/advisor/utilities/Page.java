package advisor.utilities;

import java.util.List;

public class Page<T> {

    private List<T> content;

    public Page(List<T> content) {
        this.content = content;
    }

    public List<T> getContent() {
        return content;
    }

    public void setContent(List<T> content) {
        this.content = content;
    }
}
