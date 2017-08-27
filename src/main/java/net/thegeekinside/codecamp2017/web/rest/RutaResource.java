package net.thegeekinside.codecamp2017.web.rest;

import com.codahale.metrics.annotation.Timed;
import io.swagger.annotations.ApiParam;
import net.thegeekinside.codecamp2017.domain.Estacion;
import net.thegeekinside.codecamp2017.domain.RutaFinder;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
public class RutaResource {
    RutaFinder rutaFinder = new RutaFinder();

    @GetMapping("/ruta/{origen}/{destino}")
    public List<Estacion> getEstaciones(@PathVariable String origen, @PathVariable String destino) {
        return rutaFinder.find(origen, destino);
    }

    @GetMapping("/ruta")
    @Timed
    public List<Estacion> getAllUsers(@ApiParam Pageable pageable) {
        return rutaFinder.find("Acatitla", "Mixiuhca");
    }

}
