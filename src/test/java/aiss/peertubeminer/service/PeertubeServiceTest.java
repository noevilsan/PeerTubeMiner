package aiss.peertubeminer.service;

import aiss.peertubeminer.model.peertube.OwnerAccount;
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
        // 1. Llamamos al método con una cuenta que sabemos que existe
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
}