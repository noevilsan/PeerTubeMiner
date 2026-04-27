package aiss.peertubeminer.service;

import aiss.peertubeminer.model.peertube.OwnerAccount;
import aiss.peertubeminer.model.peertube.PeerTubeVideoList;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PeertubeService {
    private RestTemplate restTemplate = new RestTemplate();

    public OwnerAccount getAccount(String accountName){
        String url = "https://framatube.org/api/v1/accounts/" + accountName;
        return restTemplate.getForObject(url, OwnerAccount.class);
    }

    public PeerTubeVideoList getVideosFromAccount(String nameAccount){
        String url = "https://framatube.org/api/v1/accounts/" + nameAccount +  "/videos";
        return restTemplate.getForObject(url, PeerTubeVideoList.class);
    }
}
