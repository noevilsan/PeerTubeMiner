package aiss.peertubeminer.model.peertube;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OwnerAccount {

    @JsonProperty("id")
    private Integer id; // VideoMiner usa String, pero la API nos da Integer.

    @JsonProperty("displayName")
    private String name; // La API lo llama displayName, nosotros lo guardamos como name para el User.

    @JsonProperty("description")
    private String description;

    @JsonProperty("url")
    private String user_link; // La API lo llama url, nosotros lo guardamos como user_link.

    // Necesitamos la lista de avatares para luego sacar el picture_link
    @JsonProperty("avatars")
    private List<Avatar> avatars;

    @JsonProperty("createdAt")
    private String createdAt;

    // --- GETTERS Y SETTERS ---

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

    public String getUser_link() {
        return user_link;
    }

    public void setUser_link(String user_link) {
        this.user_link = user_link;
    }


    public List<Avatar> getAvatars() {
        return avatars;
    }

    public void setAvatars(List<Avatar> avatars) {
        this.avatars = avatars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @Override
    public String toString() {
        return "OwnerAccount{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", user_link='" + user_link + '\'' +
                ", avatars=" + avatars +
                ", createdAt='" + createdAt + '\'' +
                '}';
    }
}