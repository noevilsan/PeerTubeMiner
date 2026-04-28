package aiss.peertubeminer.service;

import aiss.peertubeminer.model.peertube.OwnerAccount;
import aiss.peertubeminer.model.peertube.PeerTubeCaption;
import aiss.peertubeminer.model.peertube.PeerTubeComment;
import aiss.peertubeminer.model.peertube.PeerTubeVideoList;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PeertubeServiceTest {

    @Autowired
    PeertubeService service;

    @Test
    @DisplayName("Probar que recupera la cuenta de Framasoft")
    void getAccountTest() {
        OwnerAccount account = service.getAccount("Framasoft");
        assertNotNull(account, "La cuenta no debería ser nula");
        assertEquals("Framasoft", account.getName(), "El nombre debería coincidir");
        System.out.println(account.toString());
    }

    @Test
    @DisplayName("Probar que recupera vídeos de la cuenta Framasoft")
    void getVideosFromAccountTest() {
        // 1. Llamamos al metodo con una cuenta que sabemos que existe
        PeerTubeVideoList videoList = service.getVideosFromAccount("Framasoft");

        // 2. Comprobaciones de seguridad (Assertions)
        assertNotNull(videoList, "La respuesta no debería ser nula");
        assertNotNull(videoList.getData(), "La lista 'data' de vídeos no debería ser nula");

        // 3. Verificamos que realmente ha traído vídeos
        assertFalse(videoList.getData().isEmpty(), "La lista de vídeos no debería estar vacía");

        // 4. Print de control para que veas algo en la consola de IntelliJ
        System.out.println("Vídeos encontrados: " + videoList.getData().size());
        System.out.println("Nombre del primer vídeo: " + videoList.getData().get(0).getName());
    }

    @Test
    @DisplayName("Probar que recupera comentarios de un vídeo (Comments)")
    void getCommentsTest() {
        // 1. Sacamos primero un vídeo real
        PeerTubeVideoList videoList = service.getVideosFromAccount("framasoft");
        assertFalse(videoList.getData().isEmpty(), "No hay vídeos para hacer la prueba");
        String realVideoId = videoList.getData().get(0).getId();

        // 2. Pedimos los comentarios usando ese ID real
        PeerTubeComment comments = service.getComments(realVideoId);

        // 3. Comprobaciones
        assertNotNull(comments, "El envoltorio de los comentarios no debería ser nulo");
        System.out.println("Comentarios recuperados para el vídeo: " + realVideoId);
    }

    @Test
    @DisplayName("Probar que recupera subtítulos de un vídeo (Captions)")
    void getCaptionsTest() {
        // 1. Volvemos a usar el ID de un vídeo real
        PeerTubeVideoList videoList = service.getVideosFromAccount("framasoft");
        assertFalse(videoList.getData().isEmpty(), "No hay vídeos para hacer la prueba");
        String realVideoId = videoList.getData().get(0).getId();

        // 2. Pedimos los subtítulos
        PeerTubeCaption captions = service.getCaptions(realVideoId);

        // 3. Comprobaciones
        assertNotNull(captions, "El objeto de subtítulos no debería ser nulo");
        System.out.println("Subtítulos recuperados para el vídeo: " + realVideoId);
    }
}