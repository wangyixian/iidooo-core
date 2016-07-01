package com.iidooo.core.model;

import com.iidooo.core.enums.SortField;
import com.iidooo.core.enums.SortType;

public class Page {

    public Page() {

        this.start = 0;
        pageSize = 10;
        currentPage = 1;

        sortField = SortField.CreateTime.toString();

        sortType = SortType.desc.toString();
    }

    private int start;

    private int end;

    /**
     * Display line number of per page
     */
    private int pageSize;

    /**
     * The number of current page
     */
    private int currentPage;

    /**
     * The record sum of searched
     */
    private int recordSum;

    /**
     * The page sum of searched/page size
     */
    private int pageSum;

    private String sortField;

    private String sortType;

    /**
     * Get the start line No.
     * 
     * @return start
     */
    public final int getStart() {
        return start;
    }

    /**
     * Set the start line No.
     * 
     * @param start
     */
    public void setStart(int start) {
        this.start = start;
    }

    /**
     * Get the end line No.
     * 
     * @return end
     */
    public int getEnd() {
        return end;
    }

    /**
     * Set the end line No.
     * 
     * @param end
     */
    public void setEnd(int end) {
        this.end = end;
    }

    /**
     * Get the record sum of per page
     * 
     * @return pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * Set the record sum of per page
     * 
     * @param pageSize
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * Get the current page No.
     * 
     * @return currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * Set the current page No.
     * 
     * @param currentPage
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * Get the record sum of searched
     * 
     * @return recordSum
     */
    public int getRecordSum() {
        return recordSum;
    }

    /**
     * Set the record sum of searched
     * 
     * @param recordSum
     */
    public void setRecordSum(int recordSum) {
        this.recordSum = recordSum;
    }

    /**
     * Get the page sum
     * 
     * @return
     */
    public int getPageSum() {
        return pageSum;
    }

    /**
     * Set the page sum
     * 
     * @param pageSum
     */
    public void setPageSum(int pageSum) {
        this.pageSum = pageSum;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortType() {
        return sortType;
    }

    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

}
