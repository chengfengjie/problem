package problem.no.model;

import lombok.Getter;
import lombok.Setter;

/**
 * @author chengfj
 */
@Getter
@Setter
public class BaseQueryFilter {
    private Integer pageNum = 1;
    private Integer pageSize = 10;

    private Integer offset = 0;
    private Integer limit = 10;

    public void makeCustomPage() {
        if (this.pageNum != null
                && this.pageSize != null
                && this.pageSize > 0
                && this.pageNum > 0) {
            this.offset = (this.pageNum - 1) * this.pageSize;
        }
        this.limit = this.pageSize;
        this.pageNum = null;
        this.pageSize = null;
    }
}
