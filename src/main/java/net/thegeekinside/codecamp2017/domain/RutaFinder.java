package net.thegeekinside.codecamp2017.domain;

import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

public class RutaFinder {
    private static final int REVERSE = 1;
    private static final int FORWARD = 0;
    private LineasExtractor lineasExtractor= new LineasExtractor();
    private RedMetro redMetro;

    public RutaFinder(){
        redMetro =  lineasExtractor.extract();
    }

    public List<Estacion> find (String origen, String destino){
        // Buscar el punto del origen
        // Buscar el punto del destino
        Estacion estacionOrigen = redMetro.estaciones.stream().filter( e -> e.nombre.equals(origen)).findFirst().get();
        Estacion estacionDestino = redMetro.estaciones.stream().filter(e -> e.nombre.equals(destino)).findFirst().get();

        Point puntoOrigen = estacionOrigen.coordenadas;
        Point puntoDestino = estacionDestino.coordenadas;

        List<Estacion> estaciones = null;

        // Identificar la linea del origen
        Linea lineaOrigen = estacionOrigen.transbordes.stream().filter(t -> {
            Linea l = estacionDestino.transbordes.stream().filter(ld -> ld.nombre.equals(t.nombre)).findFirst().orElse(null);
            return l != null;
        }).findFirst().orElse(null);

        Linea lineaDestino = estacionDestino.transbordes.stream().filter(t -> {
            Linea l = estacionDestino.transbordes.stream().filter(ld -> ld.nombre.equals(t.nombre)).findFirst().orElse(null);
            return l != null;
        }).findFirst().orElse(null);


        if (lineaDestino != null) {
            // Si son iguales trazar la ruta
            estaciones = basicFind(estacionOrigen, estacionDestino, lineaOrigen);
        } else {
            Estacion e = null;
            Estacion trans = null;
            Linea linea = null;

            // Buscar en los transbordes la estacion
            for (Estacion est: lineaOrigen.estaciones) {
                if(est.transbordes.size() > 1) {
                    for (Linea la:est.transbordes) {
                        e = la.estaciones.stream().filter(esta -> esta.equals(estacionDestino)).findFirst().orElse(null);
                        if (e == null) {
                            trans = est;
                            linea = la;
                            break;
                        }
                    }
                }

                if (e == null) {
                    break;
                }
            }

            List<Estacion> rec1= basicFind(estacionOrigen, trans, lineaOrigen);
            List<Estacion> rec2= basicFind(trans, estacionDestino, linea);

            estaciones.addAll(rec1);
            estaciones.addAll(rec2);
        }

        return estaciones;
    }

    private List<Estacion> basicFind(Estacion estacionOrigen, Estacion estacionDestino, Linea lineaOrigen) {
        List<Estacion> estaciones = new ArrayList<Estacion>();

        int idxOrigen = lineaOrigen.estaciones.indexOf(estacionOrigen);
        int idxDestino = lineaOrigen.estaciones.indexOf(estacionDestino);

        int direccion = idxOrigen > idxDestino ? REVERSE : FORWARD;

        if (direccion == REVERSE) {
            for (int i = idxOrigen; i >= idxDestino; i--) {
                estaciones.add(lineaOrigen.estaciones.get(i));
            }
        } else {
            for (int i = idxOrigen; i <= idxDestino; i++) {
                estaciones.add(lineaOrigen.estaciones.get(i));
            }
        }

        return estaciones;
    }
}
