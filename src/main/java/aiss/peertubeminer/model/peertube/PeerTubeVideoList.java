package aiss.peertubeminer.model.peertube;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PeerTubeVideoList {

    @JsonProperty("data")
    private List<DatumVideoList> data;

    public List<DatumVideoList> getData() {
        return data;
    }

    public void setData(List<DatumVideoList> data) {
        this.data = data;
    }
}