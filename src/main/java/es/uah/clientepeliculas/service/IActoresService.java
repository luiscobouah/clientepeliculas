package es.uah.clientepeliculas.service;

import es.uah.clientepeliculas.model.Actor;
import es.uah.clientepeliculas.model.Pelicula;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IActoresService {

    Page<Actor> buscarTodos(Pageable pageable);

    List<Actor> buscarTodos();

    Actor buscarActorPorId(Integer idActor);

    List<Actor> buscarActorPorNombre(String nombre);

//  List<Actor> buscarActoresPorPelicula(String pelicula);

    void guardarActor(Actor actor);

    void eliminarActor(Integer idActor);

   void inscribirActor(Integer idActor, Integer idPelicula);

}
