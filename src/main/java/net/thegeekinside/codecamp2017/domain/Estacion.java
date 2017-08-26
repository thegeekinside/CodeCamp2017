package net.thegeekinside.codecamp2017.domain;

import org.springframework.data.geo.Point;

import java.util.ArrayList;
import java.util.List;

public class Estacion {
    public String nombre;
    public String descripcion;
    public Point coordenadas;

    public List<Linea> transbordes = new ArrayList<Linea>();

    @Override
    public boolean equals(Object obj) {
        Estacion a = (Estacion) obj;
        return this.nombre == a.nombre;
    }
}
