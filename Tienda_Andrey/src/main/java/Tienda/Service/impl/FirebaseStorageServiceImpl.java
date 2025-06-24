/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Tienda.Service.impl;


/**
 *
 * @author isaac
 */

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.google.cloud.storage.*;
import com.google.firebase.FirebaseOptions;
import Tienda.Service.FirebaseStorageService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {
    
    private static final String BucketName = "techshop-355cb.appspot.com";
    private static final String rutaSuperiorStorage = "techshop";
    
@Override
public String cargarImagen(MultipartFile archivoLocalCliente, String carpeta, Long id) {
        try {
            String extension = archivoLocalCliente.getOriginalFilename();
            String fileName = "img" + sacaNumero(id) + extension;
            File file = this.convertToFile(archivoLocalCliente);
            String URL = this.uploadFile(file, carpeta, fileName);
            file.delete();
            return URL;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String uploadFile(File file, String carpeta, String fileName) throws IOException {
        ClassPathResource json = new ClassPathResource("firebase/techshop-355cb.json");

        BlobId blobId = BlobId.of(BucketName, rutaSuperiorStorage + "/" + carpeta + "/" + fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("image/jpg").build();

        Credentials credentials = GoogleCredentials
                .fromStream(json.getInputStream())
                .createScoped(Arrays.asList("https://www.googleapis.com/auth/cloud-platform"));
        Storage storage = StorageOptions.newBuilder()
                .setCredentials(credentials).build().getService();

        storage.create(blobInfo, Files.readAllBytes(file.toPath()));

        return storage.signUrl(blobInfo,
                360, TimeUnit.DAYS,
                Storage.SignUrlOption.signWith((ServiceAccountCredentials) credentials))
                .toString();
    }

    private File convertToFile(MultipartFile archivoLocalCliente) throws IOException {
        File tempFile = File.createTempFile("img", null);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(archivoLocalCliente.getBytes());
        }
        return tempFile;
    }

    private String sacaNumero(Long id) {
        return String.format("%04d", id);
    }
}
