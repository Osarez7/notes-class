package co.edu.intecap.notes.api;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Note {
    @SerializedName("_id")
    private int id;
    private String name;
    private String content;
    @SerializedName("image_url")
    private String imageUrl;
    @SerializedName("is_favorite")
    private boolean isFavorite;
//    private Date createdDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

}
