package aiss.peertubeminer.etl;

import aiss.peertubeminer.model.peertube.*;
import aiss.peertubeminer.model.videominer.*;
import aiss.peertubeminer.service.PeertubeService;

import java.util.ArrayList;
import java.util.List;

public class Transformer {

    private final PeertubeService peertubeService;

    public Transformer(PeertubeService peertubeService) {
        this.peertubeService = peertubeService;
    }

    public Channel buildChannel(String accountName, Integer maxVideos, Integer maxCommments) {
        OwnerAccount ptAccount = peertubeService.getAccount(accountName);

        Channel channel = new Channel(); // Creamos un channel vacío
        channel.setId(String.valueOf(ptAccount.getId()));
        channel.setName(ptAccount.getName());
        channel.setDescription(ptAccount.getDescription());
        channel.setCreatedTime(ptAccount.getCreatedAt() != null ? ptAccount.getCreatedAt() : "Unknown"); // If createdAt is null, set to "Unknown"

        PeerTubeVideoList ptVideos = peertubeService.getVideosFromAccount(accountName, maxVideos);
        List<Video> videos = new ArrayList<>();

        if (ptVideos != null // Si existe ptVideos
        && ptVideos.getData() != null // Si el contenido de los videos no es vacío
        ) {
            for (DatumVideoList ptVideo : ptVideos.getData()) {
                // Mapeamos los videos de la lista de video
                Video video = new Video();
                video.setId(ptVideo.getId());
                video.setName(ptVideo.getName());
                video.setDescription(ptVideo.getDescription());
                video.setReleaseTime(ptVideo.getCreatedAt() != null ? ptVideo.getCreatedAt() : "Unknown");
                if (ptAccount != null) {
                    User user = new User();
                    user.setId(Long.valueOf(ptAccount.getId()));
                    user.setName(ptAccount.getName());
                    user.setUser_link(ptAccount.getUser_link());
                    if (ptAccount.getAvatars() != null && !ptAccount.getAvatars().isEmpty()) {
                        user.setPicture_link(ptAccount.getAvatars().get(0).getFileUrl());
                        // Se pone .get(0) porque  la API de PeerTube devuelve una lista de avatares en distintos tamaños, y todos representan la misma imagen del usuario
                    }
                    video.setAuthor(user);
                }
                PeerTubeComment ptComments = peertubeService.getComments(ptVideo.getId(), maxCommments);
                List<Comment> comments = new ArrayList<>();
                if (ptComments != null && ptComments.getData() != null) {
                    for (DatumComment ptComment : ptComments.getData()) {
                        Comment comment = new Comment();
                        comment.setId(String.valueOf(ptComment.getId()));
                        comment.setText(ptComment.getText());
                        comment.setCreatedOn(ptComment.getCreatedAt());
                        comments.add(comment);
                    }
                }
                video.setComments(comments);
                PeerTubeCaption ptCaptions = peertubeService.getCaptions(ptVideo.getId());
                List<Caption> captions = new ArrayList<>();
                if (ptCaptions != null && ptCaptions.getData() != null) {
                    for (DatumCaption ptCaption : ptCaptions.getData()) {
                        Caption caption = new Caption();
                        caption.setId(ptCaption.getFileUrl());
                        caption.setName(ptCaption.getFileUrl());
                        caption.setLanguage(ptCaption.getLanguage() != null ? ptCaption.getLanguage().getId() : null);
                        captions.add(caption);
                    }
                }
                video.setCaptions(captions);
                videos.add(video);
            }
        }
        channel.setVideos(videos);
        return channel;
    }
}
