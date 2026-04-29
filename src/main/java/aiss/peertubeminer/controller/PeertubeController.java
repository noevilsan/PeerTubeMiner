package aiss.peertubeminer.controller;

import aiss.peertubeminer.model.videominer.Channel;
import aiss.peertubeminer.service.PeertubeService;
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

    // POST - Obtiene canal de PeerTube y lo envía a VideoMiner
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/{id}")
    public Channel send(
            @PathVariable String id,
            @RequestParam(defaultValue = "10") Integer maxVideos,
            @RequestParam(defaultValue = "2") Integer maxComments) {

        // 1. Obtener datos del canal desde PeerTube y mapearlo al modelo de VideoMiner
        Channel channel = peertubeService.buildChannel(id, maxVideos, maxComments);
        return videoMinerService.postChannel(channel);
    }

    // GET - Solo lectura para pruebas, sin enviar a VideoMiner
    @GetMapping("/{id}")
    public Channel get(
            @PathVariable String id,
            @RequestParam(defaultValue="10") Integer maxVideos,
            @RequestParam(defaultValue="2") Integer maxComments) {
        return peertubeService.buildChannel(id, maxVideos, maxComments);
    }
}
