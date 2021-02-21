package es.uah.clientepeliculas.service;


import es.uah.clientepeliculas.model.Pelicula;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class PeliculasServiceImpl implements es.uah.clientepeliculas.service.IPeliculasService {

    @Autowired
    RestTemplate template;

    @Autowired
    IActoresService actoresService;


    String url = "http://localhost:8090/api/zpeliculas/peliculas";

    @Override
    public Page<Pelicula> buscarTodas(Pageable pageable) {
        Pelicula[] peliculas = template.getForObject(url, Pelicula[].class);
        List<Pelicula> peliculasList = Arrays.asList(peliculas);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Pelicula> list;

        if (peliculasList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, peliculasList.size());
            list = peliculasList.subList(startItem, toIndex);
        }

        Page<Pelicula> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), peliculasList.size());
        return page;
    }

    @Override
    public Pelicula buscarPeliculaPorId(Integer idPelicula) {
        Pelicula pelicula = template.getForObject(url + "/" + idPelicula, Pelicula.class);
        return pelicula;
    }

    @Override
    public Page<Pelicula> buscarPeliculaPorTitulo(String titulo, Pageable pageable) {
        Pelicula[] peliculas = template.getForObject(url + "/titulo/" + titulo, Pelicula[].class);
        List<Pelicula> lista = Arrays.asList(peliculas);
        Page<Pelicula> page = new PageImpl<>(lista, pageable, lista.size());
        return page;
    }
    @Override
    public Pelicula buscarPeliculaPorTitulo(String titulo) {
        Pelicula[] peliculas = template.getForObject(url + "/titulo/" + titulo, Pelicula[].class);
        List<Pelicula> lista = Arrays.asList(peliculas);

        if (lista.size() == 0 ){
            return null;
        } else {
            return lista.get(0);
        }

    }

    @Override
    public Page<Pelicula> buscarPeliculaPorGenero(String genero, Pageable pageable) {
        Pelicula[] peliculas = template.getForObject(url + "/genero/" + genero, Pelicula[].class);
        List<Pelicula> lista = Arrays.asList(peliculas);
        Page<Pelicula> page = new PageImpl<>(lista, pageable, lista.size());
        return page;
    }
    @Override
    public void guardarPelicula(Pelicula pelicula) {
        if (pelicula.getIdPelicula() != null && pelicula.getIdPelicula() > 0) {
            template.put(url, pelicula);
        } else {
            pelicula.setIdPelicula(0);
            template.postForObject(url, pelicula, String.class);
        }
    }

    @Override
    public void eliminarPelicula(Integer idPelicula) {
        template.delete(url + "/" + idPelicula);
    }
}
