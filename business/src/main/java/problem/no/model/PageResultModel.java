package problem.no.model;

import com.github.pagehelper.Page;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chengfj
 * @param <T>
 */
@Getter
@Setter
public class PageResultModel<T> implements Serializable {
    private Long total;
    private List<T> list;
    private Integer pageNum;
    private Integer pageSize;
    private Integer pages;

    public PageResultModel(List<T> list) {
        if (list instanceof Page) {
            Page<T> page = (Page<T>) list;
            this.pageNum = page.getPageNum();
            this.pageSize = page.getPageSize();
            this.total = page.getTotal();
            this.pages = page.getPages();
            this.list = list;
        } else if (list instanceof ArrayList) {
            this.list = list;
        }
    }

    public PageResultModel(List<T> list, BaseQueryFilter queryFilter, Long total) {
        if (list instanceof ArrayList) {
            this.list = list;
        }
        this.pageSize = queryFilter.getLimit();
        if (queryFilter.getOffset().equals(0)) {
            this.pageNum = 1;
        } else {
            this.pageNum = queryFilter.getOffset() / this.pageSize + 1;
        }
        this.total = total;
        this.pages = total.intValue() / this.getPageSize() + 1;
    }
}
