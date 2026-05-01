package aiss.peertubeminer.service;

import aiss.peertubeminer.model.peertube.*;
import aiss.peertubeminer.model.videominer.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeertubeService {
    private RestTemplate restTemplate = new RestTemplate();

    public OwnerAccount getAccount(String accountName){
        String url = "https://framatube.org/api/v1/accounts/" + accountName;
        return restTemplate.getForObject(url, OwnerAccount.class);
    }

    public PeerTubeChannel getChannel(String channelId){
        String url = "https://framatube.org/api/v1/channels/" + channelId;
        return restTemplate.getForObject(url, PeerTubeChannel.class);
    }

    public PeerTubeVideoList getVideosFromAccount(String nameAccount){
        String url = "https://framatube.org/api/v1/accounts/" + nameAccount +  "/videos";
        return restTemplate.getForObject(url, PeerTubeVideoList.class);
    }

    public PeerTubeVideoList getVideoFromChannel(String channelId, Integer maxVideos) {
        String url = "https://framatube.org/api/v1/channels/" + channelId + "/videos?count=" + maxVideos;
        return restTemplate.getForObject(url, PeerTubeVideoList.class);
    }

    public PeerTubeComment getComments(String videoId, Integer maxComments) {
        String url = "https://framatube.org/api/v1/videos/" + videoId + "/comment-threads?count=" + maxComments;
        return restTemplate.getForObject(url, PeerTubeComment.class);
    }

    public PeerTubeCaption getCaptions(String videoId) {
        String url = "https://framatube.org/api/v1/videos/" + videoId + "/captions";
        return restTemplate.getForObject(url, PeerTubeCaption.class);
    }

    public Channel buildChannel(String channelId, Integer maxVideos, Integer maxComments) {

        // 1. Datos del canal
        PeerTubeChannel ptChannel = getChannel(channelId);

        Channel channel = new Channel(); // Se crea una nueva instancia de canal
        channel.setId(String.valueOf(ptChannel.getId()));
        channel.setName(ptChannel.getName());
        channel.setDescription(ptChannel.getDescription());
        OwnerAccount owner = ptChannel.getOwnerAccount();
        channel.setCreatedTime(owner != null ? owner.getUser_link() : "Unknown");

        // 2. Videos
        PeerTubeVideoList ptVideoList = getVideoFromChannel(channelId, maxVideos);
        List<Video> videos = new ArrayList<>();

        if (ptVideoList != null && ptVideoList.getData() != null) {
            for (DatumVideoList ptVideo : ptVideoList.getData()) {
                Video video = new Video();
                video.setId(ptVideo.getId());
                video.setName(ptVideo.getName());
                video.setDescription(ptVideo.getDescription());
                video.setReleaseTime(ptVideo.getCreatedAt() != null ? ptVideo.getCreatedAt() : "unknown");

                // 2a. User/autor del video
                if (owner != null) {
                    User user = new User();
                    user.setName(owner.getName());
                    user.setUser_link(owner.getUser_link());
                    // Cogemos la url del primer avatar si existe
                    if (owner.getAvatars() != null && !owner.getAvatars().isEmpty()) {
                        user.setPicture_link(owner.getAvatars().getFirst().getFileUrl());
                    }
                    video.setAuthor(user);
                }

                // 2b. Comentarios
                PeerTubeComment ptComments = getComments(video.getId(), maxComments);
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

                // 2c. Captions
                PeerTubeCaption ptCaptions = getCaptions(ptVideo.getId());
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
