package com.nikhilbawane.snazzymaps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Data model for pages
 * A page is returned by the SnazzyMaps API
 * Each page contains a number of styles
 * <p>
 * Created by Nikhil on 08-08-2017.
 */

public class Page {

    @SerializedName("pagination")
    @Expose
    private Pagination pagination;
    @SerializedName("styles")
    @Expose
    private List<Style> styles;

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    public List<Style> getStyles() {
        return styles;
    }

    public void setStyles(List<Style> styles) {
        this.styles = styles;
    }

    @Override
    public String toString() {
        String stylesH = "";
        for (int i = 0; i < styles.size(); i++) {
            stylesH += styles.get(0).toString();
            if (i < styles.size() - 1) stylesH += ",";
        }
        return "\"styles\":{"
                + "\"pagination\":\"" + pagination.toString() + "\","
                + "\"styles\":[" + stylesH + "]"
                + "}";
    }

    public class Pagination {
        @SerializedName("currentPage")
        @Expose
        private Integer currentPage;
        @SerializedName("pageSize")
        @Expose
        private Integer pageSize;
        @SerializedName("totalPages")
        @Expose
        private Integer totalPages;
        @SerializedName("totalItems")
        @Expose
        private Integer totalItems;

        public Integer getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(Integer currentPage) {
            this.currentPage = currentPage;
        }

        public Integer getPageSize() {
            return pageSize;
        }

        public void setPageSize(Integer pageSize) {
            this.pageSize = pageSize;
        }

        public Integer getTotalPages() {
            return totalPages;
        }

        public void setTotalPages(Integer totalPages) {
            this.totalPages = totalPages;
        }

        public Integer getTotalItems() {
            return totalItems;
        }

        public void setTotalItems(Integer totalItems) {
            this.totalItems = totalItems;
        }

        @Override
        public String toString() {
            return "{"
                    + "\"currentPage\":\"" + currentPage + "\","
                    + "\"pageSize\":\"" + pageSize + "\""
                    + "\"totalPages\":\"" + totalPages + "\""
                    + "\"totalItems\":\"" + totalItems + "\""
                    + "}";
        }
    }
}
