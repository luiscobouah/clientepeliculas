package es.uah.clientepeliculas.service;


import es.uah.clientepeliculas.model.Critica;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICriticasService {

    Page<Critica> buscarTodas(Pageable pageable);

    Page<Critica> buscarCriticasPorIdPelicula(Integer idPelicula, Pageable pageable);

    List<Critica> buscarCriticasPorIdPelicula(Integer idPelicula);

    Critica buscarCriticaPorId(Integer idCritica);

    String guardarCritica(Critica critica);

    void eliminarCritica(Integer idCritica);

}
