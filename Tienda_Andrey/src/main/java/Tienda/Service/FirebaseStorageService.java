/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */

package Tienda.Service;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
/**
 *
 * @author isaac
 */

@Service
public interface FirebaseStorageService {
    
   String cargarImagen(MultipartFile archivoLocalCliente, String carpeta, Long id);
    
    //El BucketName es el <id_del_proyecto> + ".appspot.com"
    final String bucketName = "techshop-355cb.appspot.com";
    
    //Esta es la ruta básica de este proyecto Techshop
    final String rutaSuperioreStorage = "techshop";
    
    //Ubicación donde se encuentra el archivo de configuración JSON
    final String rutaJsonFile = "firebase";
    
    //El nombre del archivo JSON  
    final String archivoJsonFile = "techshop-355cb-firebase-adminsdk-fbsvc-5a7281e8db";
}
