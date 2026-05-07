package aiss.peertubeminer.controller;

import aiss.peertubeminer.model.videominer.Channel;
import aiss.peertubeminer.service.PeertubeService;
import aiss.peertubeminer.etl.Transformer;
import aiss.peertubeminer.service.VideoMinerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.graphql.GraphQlProperties;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/peertubeminer/channel")
public class PeertubeController {

    @Autowired
    PeertubeService peertubeService;

    @Autowired
    VideoMinerService  videoMinerService;

    @Autowired
    Transformer transformer;

    // POST - Obtiene canal de PeerTube y lo envía a VideoMiner
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}")
    public Channel send(
            @PathVariable String id,
            @RequestParam(defaultValue = "10") Integer maxVideos,
            @RequestParam(defaultValue = "2") Integer maxComments) {

        // Llamamos al servicio con los 3 parámetros
        Channel channel = transformer.buildChannel(id, maxVideos, maxComments);
        return videoMinerService.postChannel(channel);
    }

    @GetMapping("/{id}")
    public Channel get(
            @PathVariable String id,
            @RequestParam(defaultValue="10") Integer maxVideos,
            @RequestParam(defaultValue="2") Integer maxComments) {

        // Igual para la prueba
        return transformer.buildChannel(id, maxVideos, maxComments);
    }
}
