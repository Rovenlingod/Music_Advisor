package advisor.service;

import advisor.config.CurrentUser;
import advisor.storage.Buffer;

import java.util.List;
import java.util.Objects;

public class PagingService {

    private static PagingService instance = null;
    private int currentPage = 0;

    private PagingService() {
    }

    public static PagingService getInstance() {
        if (Objects.isNull(instance)) {
            instance = new PagingService();
        }
        return instance;
    }

    public void reset() {
        currentPage = 0;
    }

    public String next() {
        if (Buffer.getInstance().getData() == null) {
            throw new NullPointerException("no data in storage");
        }
        try {
            currentPage++;
            return buildOutput(Buffer.getInstance().getData().getPage(currentPage).getContent());
        } catch (IndexOutOfBoundsException e) {
            currentPage--;
            return "No more pages.";
        }
    }

    public String prev() {
        if (Buffer.getInstance().getData() == null) {
            throw new NullPointerException("no data in storage");
        }
        if (currentPage == 1) {
            return "No more pages.";
        }
        currentPage--;
        return buildOutput(Buffer.getInstance().getData().getPage(currentPage).getContent());
    }

    private <T> String buildOutput(List<T> playlists) {
        StringBuffer result = new StringBuffer();
        for (T e : playlists) {
            result.append(e.toString());
            result.append("\n");
        }
        result.append("---PAGE "
                + currentPage
                + " OF "
                + Buffer.getInstance().getData().size()
                + "---");
        return result.toString();
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
