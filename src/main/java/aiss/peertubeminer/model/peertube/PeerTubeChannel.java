package aiss.peertubeminer.model.peertube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PeerTubeChannel {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("displayName")
    private String name; // Mapeamos displayName a name para VideoMiner

    @JsonProperty("description")
    private String description;

    @JsonProperty("ownerAccount")
    private OwnerAccount ownerAccount;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public OwnerAccount getOwnerAccount() {
        return ownerAccount;
    }

    public void setOwnerAccount(OwnerAccount ownerAccount) {
        this.ownerAccount = ownerAccount;
    }

    @Override
    public String toString() {
        return "PeerTubeChannel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}