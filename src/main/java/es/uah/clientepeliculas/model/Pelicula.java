package es.uah.clientepeliculas.model;


import java.util.ArrayList;
import java.util.List;

public class Pelicula {

    private Integer idPelicula;
    private String titulo;
    private String annio;
    private String duracion;
    private String pais;
    private String direccion;
    private String genero;
    private String sinopsis;
    private String imagen;
    private List<Actor> actores;

    public Pelicula() {
        this.actores = new ArrayList<>();
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPeliculas) {
        this.idPelicula = idPeliculas;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAnnio() {
        return annio;
    }

    public void setAnnio(String annio) {
        this.annio = annio;
    }

    public String getDuracion() {
        return duracion;
    }

    public void setDuracion(String duracion) {
        this.duracion = duracion;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getSinopsis() {
        return sinopsis;
    }

    public void setSinopsis(String sinopsis) {
        this.sinopsis = sinopsis;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }


    public List<Actor> getActores() {
        return actores;
    }

    public void setActores(List<Actor> actores) {
        this.actores = actores;
    }

    public void addActor(Actor actor) {
        if (actor != null) {
            this.actores.add(actor);
        }
    }

    public void removeActor(Actor actor) {
        if (actor != null) {
            this.actores.remove(actor);
        }
    }

    public List<String> getNombresActores() {
        List<String> list = new ArrayList<>();
        for (Actor actor : this.actores) {
            list.add(actor.getNombre());
        }
        return list;
    }
}
