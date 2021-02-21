package es.uah.clientepeliculas.service;

import es.uah.clientepeliculas.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class UsuariosServiceImpl implements IUsuariosService {

    @Autowired
    RestTemplate template;

    String url = "http://localhost:8090/api/zcriticas/usuarios";

    @Override
    public Page<Usuario> buscarTodos(Pageable pageable) {
        Usuario[] usuarios = template.getForObject(url, Usuario[].class);
        List<Usuario> usuariosList = Arrays.asList(usuarios);

        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Usuario> list;

        if (usuariosList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, usuariosList.size());
            list = usuariosList.subList(startItem, toIndex);
        }

        Page<Usuario> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), usuariosList.size());
        return page;
    }

    @Override
    public Usuario buscarUsuarioPorId(Integer idUsuario) {
        Usuario usuario = template.getForObject(url + "/" + idUsuario, Usuario.class);
        return usuario;
    }

    @Override
    public Page<Usuario> buscarUsuarioPorNombre(String nombre, Pageable pageable) {
        Usuario usuario = template.getForObject(url+"/nombre/"+nombre, Usuario.class);
        List<Usuario> usuariosList = new ArrayList();
        if (usuario != null){
            usuariosList.add(usuario);
        }
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Usuario> list;

        if (usuariosList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, usuariosList.size());
            list = usuariosList.subList(startItem, toIndex);
        }

        Page<Usuario> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), usuariosList.size());
        return page;
    }

    @Override
    public Usuario buscarUsuarioPorCorreo(String correo) {
        Usuario usuario = template.getForObject(url+"/correo/"+correo, Usuario.class);
        return usuario;
    }

    @Override
    public Page<Usuario> buscarUsuarioPorCorreo(String correo, Pageable pageable) {
        Usuario usuario = template.getForObject(url+"/correo/"+correo, Usuario.class);
        List<Usuario> usuariosList = new ArrayList();
        if (usuario != null){
            usuariosList.add(usuario);
        }
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Usuario> list;

        if (usuariosList.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, usuariosList.size());
            list = usuariosList.subList(startItem, toIndex);
        }

        Page<Usuario> page = new PageImpl<>(list, PageRequest.of(currentPage, pageSize), usuariosList.size());
        return page;
    }

    @Override
    public Usuario login(String correo, String clave) {
        Usuario usuario = template.getForObject(url+"/login/"+correo+"/"+clave, Usuario.class);
        return usuario;

    }

    @Override
    public void guardarUsuario(Usuario usuario) {
        if (usuario.getIdUsuario() != null && usuario.getIdUsuario() > 0) {
            template.put(url, usuario);
        } else {
            usuario.setIdUsuario(0);
            template.postForObject(url, usuario, String.class);
        }
    }

    @Override
    public void eliminarUsuario(Integer idUsuario) {
        template.delete(url+"/"+idUsuario);
    }

 }
