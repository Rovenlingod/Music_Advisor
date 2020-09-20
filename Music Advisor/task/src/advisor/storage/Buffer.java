package advisor.storage;

import advisor.service.PagingService;
import advisor.utilities.Page;
import advisor.utilities.PagedList;

public class Buffer<T> {

    private static Buffer instance = null;
    private PagedList<Page<T>> data;

    private Buffer() {
    }

    public static Buffer getInstance() {
        if (instance == null) instance = new Buffer();
        return instance;
    }

    public void clear() {
        data = null;
        PagingService.getInstance().reset();
    }

    public void addData(PagedList<Page<T>> data) {
        this.data = data;
    }

    public PagedList<Page<T>> getData() {
        return data;
    }

    public boolean isEmpty() {
        return data == null;
    }
}
