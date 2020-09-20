package advisor.utilities;

import advisor.config.Config;

import java.util.ArrayList;
import java.util.List;

public class PagedList<T> {

    private List<Page<T>> list = new ArrayList<>();
    private int elementsPerPage = Config.NUMBER_OF_ENTRIES_ON_A_PAGE;

    public PagedList(List<T> list) {
       buildList(list);
    }

    public PagedList(List<T> list, int elementsPerPage) {
        buildList(list);
        this.elementsPerPage = elementsPerPage;
    }

    private void buildList(List<T> initList) {
        for (int i = 0; i < initList.size(); i += elementsPerPage) {
            if (i + elementsPerPage <= initList.size()) {
                list.add(new Page<>(initList.subList(i, i + elementsPerPage)));
            } else {
                list.add(new Page<>(initList.subList(i, initList.size())));
            }
        }
    }

    public Page<T> getPage(int number) {
        if (number <= list.size() && number > 0) {
            return list.get(number - 1);
        } else {
            throw new IndexOutOfBoundsException("no page with number " + number);
        }
    }

    public int size() {
        return list.size();
    }
}
