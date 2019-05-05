package club.mrdaisite.torder.common.api;

import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * CommonPage
 *
 * @author dai
 * @date 2019/05/05
 */
public class CommonPage {
    /**
     * 单前页数
     */
    private Integer page;
    /**
     * 每页的记录数
     */
    private Integer perPage;
    /**
     * 总页数
     */
    private Integer pageSize;
    private List<Object> list;

    public CommonPage(PageInfo pageInfo) {
        setPage(pageInfo.getPageNum());
        setPerPage(pageInfo.getPageSize());
        setPageSize(pageInfo.getPages());
        setList(pageInfo.getList());
    }

    public CommonPage(PageInfo pageInfo, List<Object> list) {
        setPage(pageInfo.getPageNum());
        setPerPage(pageInfo.getPageSize());
        setPageSize(pageInfo.getPages());
        setList(list);
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPerPage() {
        return perPage;
    }

    public void setPerPage(Integer perPage) {
        this.perPage = perPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    @Override
    public String toString() {
        return "CommonPage{" +
                "page=" + page +
                ", perPage=" + perPage +
                ", pageSize=" + pageSize +
                ", list=" + list +
                '}';
    }
}
