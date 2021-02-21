package es.uah.clientepeliculas.service;


import es.uah.clientepeliculas.model.Actor;
import es.uah.clientepeliculas.model.Pelicula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IPeliculasService {

    Page<Pelicula> buscarTodas(Pageable pageable);

    Pelicula buscarPeliculaPorId(Integer idPelicula);

    Pelicula buscarPeliculaPorTitulo(String titulo);

    Page<Pelicula> buscarPeliculaPorTitulo(String titulo, Pageable pageable);

    Page<Pelicula> buscarPeliculaPorGenero(String genero, Pageable pageable);

    /* Page<Pelicula> buscarPeliculaPorActor(String nombreActor, Pageable pageable);*/

    void guardarPelicula(Pelicula pelicula);

    void eliminarPelicula(Integer idPelicula);

}
