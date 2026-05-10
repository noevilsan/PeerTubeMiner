package aiss.peertubeminer.service;

import aiss.peertubeminer.exception.ChannelNotFoundException;
import aiss.peertubeminer.model.peertube.*;
import aiss.peertubeminer.model.videominer.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PeertubeService {
    private RestTemplate restTemplate = new RestTemplate();

    public OwnerAccount getAccount(String accountName) {
        String url = "https://peertube.tv/api/v1/accounts/" + accountName;
        return restTemplate.getForObject(url, OwnerAccount.class);
    }

    public PeerTubeChannel getChannel(String channelId) {
        String url = "https://peertube.tv/api/v1/video-channels/" + channelId;
        // return restTemplate.getForObject(url, PeerTubeChannel.class);
        try {
            return restTemplate.getForObject(url, PeerTubeChannel.class);
        } catch (HttpClientErrorException e) {
            throw new ChannelNotFoundException(channelId);
        }
    }

    // Este metodo busca los vídeos en la CUENTA, no en el canal
    public PeerTubeVideoList getVideosFromAccount(String nameAccount, Integer maxVideos) {
        String url = "https://peertube.tv/api/v1/accounts/" + nameAccount + "/videos?count=" + maxVideos;
        return restTemplate.getForObject(url, PeerTubeVideoList.class);
    }

    public PeerTubeVideoList getVideoFromChannel(String channelId, Integer maxVideos) {
        String url = "https://peertube.tv/api/v1/video-channels/" + channelId + "/videos?count=" + maxVideos;
        return restTemplate.getForObject(url, PeerTubeVideoList.class);
    }

    public PeerTubeComment getComments(String videoId, Integer maxComments) {
        String url = "https://peertube.tv/api/v1/videos/" + videoId + "/comment-threads?count=" + maxComments;
        return restTemplate.getForObject(url, PeerTubeComment.class);
    }

    public PeerTubeCaption getCaptions(String videoId) {
        String url = "https://peertube.tv/api/v1/videos/" + videoId + "/captions";
        return restTemplate.getForObject(url, PeerTubeCaption.class);
    }
}