package com.yunniu.lease.model;

import lombok.Data;

@Data
public class Page {

    protected int rowsCount;
    protected int curPage; // 当前页
    protected int pageCount; // 总页数
    protected int startCount;
    protected int pageSize;
    public static int size = 20;

    public static ManageParams getParams(ManageParams mps, int rowsCount) {
        mps.setRowsCount(rowsCount);
        mps.setStartCount(mps.getPageSize() * (mps.getCurPage() - 1));
        if (rowsCount % mps.getPageSize() == 0) {
            mps.setPageCount(rowsCount / mps.getPageSize());
        } else if (rowsCount < mps.getPageSize()) {
            mps.setPageCount(1);
        } else {
            mps.setPageCount(rowsCount / mps.getPageSize() + 1);
        }
        return mps;

    }
}
