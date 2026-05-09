package aiss.peertubeminer.service;

import aiss.peertubeminer.model.videominer.Channel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VideoMinerService {
    private final RestTemplate restTemplate = new RestTemplate();
    // Si lo abrimos desde Railway el post va a VIDEOMINER y si no, al localhost
    @Value("${VIDEOMINER_URL:http://localhost:8080}")
    private String videominerUrl;

    public Channel postChannel(Channel channel){
        // La dirección a la que vamos a enviar el canal
        String url = videominerUrl + "/videominer/api/channels";

        HttpHeaders headers = new HttpHeaders();
        // Metemos la clave que el Interceptor está esperando
        headers.set("X-API-KEY", "trabajoAiss123");

        HttpEntity<Channel> request = new HttpEntity<>(channel, headers);

        return restTemplate.postForObject(url, request, Channel.class);
    }
}