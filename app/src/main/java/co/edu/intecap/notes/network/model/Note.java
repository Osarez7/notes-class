package co.edu.intecap.notes.network.model;

import com.google.gson.annotations.SerializedName;
import java.util.Date;

import androidx.room.Ignore;

public class Note {
    private String id;
    private String name;
    private String content;
    @SerializedName("is_favorite")
    private boolean isFavorite;
    @Ignore
    private Date createdDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
