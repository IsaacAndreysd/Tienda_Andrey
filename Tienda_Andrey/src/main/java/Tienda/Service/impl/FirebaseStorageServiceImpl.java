/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Tienda.Service.impl;

import com.google.auth.Credentials;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import com.google.cloud.storage.StorageOptions;
import Tienda.Service.FirebaseStorageService;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author isaac
 */
@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {

    @Override
    public String cargarImagen(MultipartFile archivoLocalCliente, String carpeta, long id) {
        try {
            // El nombre original del archivo local del cliente
            String extension = archivoLocalCliente.getOriginalFilename();
            
            // Se genera el nuevo nombre según el código del archivo
            String fileName = "img" + sacaNumero(id) + extension;
            
            // Se convierte/sube el archivo desde el equipo local del cliente a un archivo temporal en el servidor
            File file = this.convertToFile(archivoLocalCliente, fileName);
            
            // Se sube el archivo temporal del servidor a Firebase y se obtiene el url válido
            String URL = this.uploadFile(file, fileName);
            
            // Se elimina el archivo temporal cargas desde el cliente
            file.delete();
            
            return URL;
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    private String uploadFile(File file, 
                            String fileName) throws IOException {
        // Se define el lugar y acceso al archivo .json
        ClassPathResource json = new ClassPathResource(rutaJsonFile + File.separator + archivoJsonFile);
        BlobId blobId = BlobId.of(bucketName, rutaSuperioreStorage + "/" + fileName);
        BlobInfo blobInfo = BlobInfo.newBuilder(blobId).setContentType("media").build();
        
        // Se establecen las credenciales de acceso a Firebase
        Credentials credentials = GoogleCredentials
                .fromStream(json.getInputStream());
        Storage storage = StorageOptions.newBuilder().setCredentials(credentials).build().getService();
        storage.create(blobInfo, 
                Files.readAllBytes(file.toPath()));
        
        // Se genera y retorna la URL válida 
        String url = String.format(format, "https://firebasestorage.googleapis.com/v0/b/%s/o/%s?alt=media", bucketName, rutaSuperioreStorage + "/" + fileName);
        
        return url;
    }

    // Método utilitario que convierte el archivo desde el equipo local
    // del usuario a un archivo temporal en el servidor
    private File convertToFile(MultipartFile archivoLocalCliente) throws IOException {
        File tempFile = File.createTempFile("img", "img", suffix, null);
        try (FileOutputStream fos = new FileOutputStream(tempFile)) {
            fos.write(archivoLocalCliente.getBytes());
            fos.close();
        }
        return tempFile;
    }

    // Método utilitario para obtener un string con ceros....
    private String sacaNumero(long id) {
        return String.format("%04d", args, id);
    }
}
