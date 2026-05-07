package aiss.peertubeminer.service;

import aiss.peertubeminer.model.videominer.Channel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VideoMinerService {
    private final RestTemplate restTemplate = new RestTemplate();

    public Channel postChannel(Channel channel){
        String url = "http://localhost:8080/videominer/api/channels";

        // 1. Creamos las cabeceras y añadimos la API Key
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", "trabajoAiss123");

        // 2. Envolvemos el canal y las cabeceras en el objeto HttpEntity
        HttpEntity<Channel> request = new HttpEntity<>(channel, headers);

        // 3. Enviamos la petición POST completa
        return restTemplate.postForObject(url, request, Channel.class);
    }
    }
}
