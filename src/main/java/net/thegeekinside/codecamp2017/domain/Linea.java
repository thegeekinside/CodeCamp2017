package net.thegeekinside.codecamp2017.domain;

import java.util.ArrayList;
import java.util.List;

public class Linea {
    public String nombre;
    public List<Estacion> estaciones = new ArrayList<Estacion>();

    @Override
    public boolean equals(Object obj) {
        Linea a = (Linea) obj;

        return this.nombre.equals(a.nombre);
    }
}
