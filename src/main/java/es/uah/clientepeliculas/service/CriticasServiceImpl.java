package es.uah.clientepeliculas.service;


import es.uah.clientepeliculas.model.Critica;
import es.uah.clientepeliculas.model.Pelicula;
import es.uah.clientepeliculas.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class CriticasServiceImpl implements ICriticasService {

    @Autowired
    RestTemplate template;

    @Autowired
    IPeliculasService peliculasService;

    @Autowired
    IUsuariosService usuariosService;

    @Autowired
    IActoresService actoresService;

    String url = "http://localhost:8090/api/zcriticas/criticas";

    @Override
    public Page<Critica> buscarTodas(Pageable pageable) {
        Critica[] criticas = template.getForObject(url, Critica[].class);
        List<Critica> criticaList = Arrays.asList(criticas);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Critica> list;

        if(criticaList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, criticaList.size());
            list = criticaList.subList(startItem, toIndex);
        }
        Page<Critica> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), criticaList.size());
        return page;
    }

    @Override
    public Page<Critica> buscarCriticasPorIdPelicula(Integer idPelicula, Pageable pageable) {
        Critica[] criticas = template.getForObject(url+"/pelicula/"+idPelicula, Critica[].class);
        List<Critica> criticaList = Arrays.asList(criticas);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Critica>list;

        if(criticaList.size() <startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, criticaList.size());
            list = criticaList.subList(startItem, toIndex);
        }
        Page<Critica> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), criticaList.size());
        return page;
    }

    @Override
    public Page<Critica> buscarCriticasPorIdUsuario(Integer idUsuario, Pageable pageable) {
        Critica[] criticas = template.getForObject(url+"/usuario/"+idUsuario, Critica[].class);
        List<Critica> criticaList = Arrays.asList(criticas);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Critica>list;

        if(criticaList.size() <startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, criticaList.size());
            list = criticaList.subList(startItem, toIndex);
        }
        Page<Critica> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), criticaList.size());
        return page;
    }

    public List<Critica> buscarCriticasPorIdPelicula(Integer idPelicula) {
        Critica[] criticas = template.getForObject(url+"/pelicula/"+idPelicula, Critica[].class);
        return Arrays.asList(criticas);
    }

    @Override
    public Critica buscarCriticaPorId(Integer idCritica) {
        Critica critica = template.getForObject(url+"/"+idCritica, Critica.class);
        return critica;
    }

    @Override
    public String guardarCritica(Critica critica) {
        if (critica.getIdCritica() != null && critica.getIdCritica() > 0) {
            template.postForObject(url, critica, String.class);
           return "Los datos de la critica fueron actualizados!";
        } else {
            template.postForObject(url, critica, String.class);
            return "Los datos de la critica fueron guardados!";
        }
    }

    @Override
    public void eliminarCritica(Integer idCritica) {
        template.delete(url+ "/" +  idCritica);
    }

}
