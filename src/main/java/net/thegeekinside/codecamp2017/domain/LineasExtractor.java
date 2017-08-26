package net.thegeekinside.codecamp2017.domain;

import de.micromata.opengis.kml.v_2_2_0.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class LineasExtractor {

    public RedMetro extract(){
        final Kml kml = Kml.unmarshal(new File("/Users/thegeekinside/Temporal/CodeCamp2017/src/main/resources/Metro_CDMX.kml"));
        final Document document = (Document) kml.getFeature();
        List<Feature> documentFeature = document.getFeature();

        // Obtenemos las lineas
        Feature featurelineas = documentFeature.get(0);

        List<Linea> lineas = getLineas((Folder) featurelineas);

        // Obtenemos las estaciones
        List<Estacion> estaciones = getEstacions(documentFeature);

        for (Estacion e : estaciones) {
            for (Linea linea : lineas) {
                for (Estacion estacion : linea.estaciones) {
                    if (estacion.coordenadas.getX() == e.coordenadas.getX() &&
                        estacion.coordenadas.getY() == e.coordenadas.getY()){
                        estacion.nombre = e.nombre;
                        estacion.descripcion = e.descripcion;
                        e.transbordes.add(linea);
                        estacion.transbordes = e.transbordes;
                    }
                }
            }
        }

        RedMetro redMetro = new RedMetro();
        redMetro.estaciones = estaciones;
        redMetro.lineas = lineas;

        return redMetro;
    }

    private List<Estacion> getEstacions(List<Feature> t) {
        List<Estacion> estaciones = new ArrayList<Estacion>();
        Feature featureEstaciones = t.get(1);
        List<Feature> ef =  ((Folder) featureEstaciones).getFeature();

        for (Feature featureEstacion: ef) {
            Estacion es = new Estacion();
            es.nombre = featureEstacion.getName();
            es.descripcion = featureEstacion.getDescription();

            Geometry g = ((Placemark)featureEstacion).getGeometry();

            List<Coordinate> cl = ((Point)g).getCoordinates();
            Coordinate coordinate = cl.get(0);

            es.coordenadas = new org.springframework.data.geo.Point(coordinate.getLongitude(), coordinate.getLatitude());

            estaciones.add(es);
        }
        return estaciones;
    }

    private List<Linea> getLineas(Folder featurelineas) {
        List<Linea> lineas = new ArrayList<Linea>();
        List<Feature> lf =  featurelineas.getFeature();

        for (Feature ff: lf) {
            Linea l = new Linea();
            l.nombre = ff.getName();

            Geometry geometry = ((Placemark)ff).getGeometry();
            List<Coordinate> coordinateList = ((LineString)geometry).getCoordinates();

            for (Coordinate co: coordinateList) {
                Estacion estacion = new Estacion();
                estacion.coordenadas = new org.springframework.data.geo.Point(co.getLongitude(), co.getLatitude());

                l.estaciones.add(estacion);
            }

            lineas.add(l);
        }
        return lineas;
    }
}
