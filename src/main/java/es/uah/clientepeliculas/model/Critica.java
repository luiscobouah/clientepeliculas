package es.uah.clientepeliculas.model;

import java.io.Serializable;
import java.sql.Date;
import java.util.Objects;

public class Critica implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer idCritica;
    private Integer idPelicula;
    private String valoracion;
    private Integer nota;
    private Date fecha;

    private Usuario usuario;

    public Critica() {
    }

    public Critica(Integer idPelicula, Usuario usuario) {
        this.idCritica = idPelicula;
        this.usuario = usuario;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getIdCritica() {
        return idCritica;
    }

    public void setIdCritica(Integer idCritica) {
        this.idCritica = idCritica;
    }

    public Integer getIdPelicula() {
        return idPelicula;
    }

    public void setIdPelicula(Integer idPelicula) {
        this.idPelicula = idPelicula;
    }

    public String getValoracion() {
        return valoracion;
    }

    public void setValoracion(String valoracion) {
        this.valoracion = valoracion;
    }

    public Integer getNota() {
        return nota;
    }

    public void setNota(Integer nota) {
        this.nota = nota;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Critica)) return false;
        Critica critica = (Critica) o;
        return Objects.equals(idCritica, critica.idCritica);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idCritica);
    }

}
