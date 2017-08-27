package net.thegeekinside.codecamp2017.domain;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class RutaFinderTest {
    LineasExtractor lineasExtractor = new LineasExtractor();
    RutaFinder rutaFinder = new RutaFinder();

    @Before
    public void setUp() {
    }

    @Test
    public void simpleReverseFindTest (){
        List<Estacion> estaciones = rutaFinder.find("Acatitla", "Tepalcates");

        assertThat(estaciones.size() == 3).isTrue();
    }

    @Test
    public void simpleFindTest (){
        List<Estacion> estaciones = rutaFinder.find("Acatitla", "Los Reyes");

        assertThat(estaciones.size() == 2).isTrue();
    }

    @Test
    public void oneTransferFindTest (){
        List<Estacion> estaciones = rutaFinder.find("Acatitla", "Mixiuhca");

        assertThat(estaciones.size() == 10).isTrue();
    }

    @Test
    public void oneLongTransferFindTest (){
        List<Estacion> estaciones = rutaFinder.find("Barranca del Muerto", "Zaragoza");

        assertThat(estaciones.size() == 21).isTrue();
    }

    @Test
    public void sameFindTest (){
        List<Estacion> estaciones = rutaFinder.find("Acatitla", "Acatitla");

        assertThat(estaciones.size() == 0).isTrue();
    }

}
