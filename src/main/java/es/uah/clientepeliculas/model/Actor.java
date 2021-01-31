package es.uah.clientepeliculas.model;


import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Date;
import java.util.List;
import java.util.Objects;

public class Actor {

    private Integer idActor;
    private String nombre;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", locale = "es-ES", timezone = "Europe/Madrid")

    private Date fechaNacimiento;
    private String paisNacimiento;
    private List<Pelicula> peliculas;

    public Actor() {
    }

    public Integer getIdActor() {
        return idActor;
    }

    public void setIdActor(Integer idActores) {
        this.idActor = idActores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Date getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getPaisNacimiento() {
        return paisNacimiento;
    }

    public void setPaisNacimiento(String paisNacimiento) {
        this.paisNacimiento = paisNacimiento;
    }

    public List<Pelicula> getPeliculas() {
        return peliculas;
    }

    public void setPeliculas(List<Pelicula> peliculas) {
        this.peliculas = peliculas;
    }

    public void removePelicula(Pelicula pelicula) {
        if (pelicula != null) {
            pelicula.removeActor(this);
            getPeliculas().remove(pelicula);
        }
    }




}
