package aiss.peertubeminer.service;

import aiss.peertubeminer.model.videominer.Channel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VideoMinerService {
    private final RestTemplate restTemplate = new RestTemplate();

    public Channel postChannel(Channel channel){
        return restTemplate.postForObject("http://localhost:8080/videominer/api/channels", channel, Channel.class);
    }
}
