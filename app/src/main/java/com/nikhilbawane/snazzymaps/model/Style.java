package com.nikhilbawane.snazzymaps.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Data model for styles
 *
 * Created by Nikhil on 08-08-2017.
 */

public class Style {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("url")
    @Expose
    private String url;
    @SerializedName("imageUrl")
    @Expose
    private String imageUrl;
    @SerializedName("json")
    @Expose
    private String json;
    @SerializedName("views")
    @Expose
    private Integer views;
    @SerializedName("favorites")
    @Expose
    private Integer favorites;
    @SerializedName("createdBy")
    @Expose
    private CreatedBy createdBy;
    @SerializedName("createdOn")
    @Expose
    private String createdOn;
    @SerializedName("tags")
    @Expose
    private List<String> tags = null;
    @SerializedName("colors")
    @Expose
    private List<String> colors = null;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Integer getViews() {
        return views;
    }

    public void setViews(Integer views) {
        this.views = views;
    }

    public Integer getFavorites() {
        return favorites;
    }

    public void setFavorites(Integer favorites) {
        this.favorites = favorites;
    }

    public CreatedBy getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(CreatedBy createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<String> getColors() {
        return colors;
    }

    public void setColors(List<String> colors) {
        this.colors = colors;
    }

    @Override
    public String toString() {
        return "{"
                + "\"id\":\"" + id + "\","
                + "\"name\":\"" + name + "\""
                + "\"desciption\":\"" + description + "\""
                + "\"url\":\"" + url + "\""
                + "\"imageUrl\":\"" + imageUrl + "\""
                + "\"json\":\"" + json + "\""
                + "\"views\":\"" + views + "\""
                + "\"favorites\":\"" + favorites + "\""
                + "\"createdBy\":\"" + createdBy.toString() + "\""
                + "\"createdOn\":\"" + url + "\""
                + "\"tags\":\"" + tags.toString() + "\""
                + "\"colors\":\"" + colors.toString() + "\""
                +"}";
    }

    public class CreatedBy {
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("url")
        @Expose
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "{"
                    + "\"name\":\"" + name + "\","
                    + "\"url\":\"" + url + "\""
                    +"}";
        }
    }
}
