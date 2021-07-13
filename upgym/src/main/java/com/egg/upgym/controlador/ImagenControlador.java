
package com.egg.upgym.controlador;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
@Controller
public class ImagenControlador {
    
    @GetMapping("/obtener-imagen/{imagen:.+}")
    public ResponseEntity<Resource> obtenerFoto(@PathVariable String imagen) {
        Path rutaDeArchivo = Paths.get("src//main//resources//static//imagenes").resolve(imagen).toAbsolutePath();
        Resource recurso = null;

        try {
            recurso = new UrlResource(rutaDeArchivo.toUri());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        if (!recurso.exists()|| !recurso.isReadable()) {
            throw new RuntimeException("Error al cargar la imagen");
        }

        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"").body(recurso);
    }
    
}
