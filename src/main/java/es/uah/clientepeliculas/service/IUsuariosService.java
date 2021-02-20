package es.uah.clientepeliculas.service;

import es.uah.clientepeliculas.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUsuariosService {

    Page<Usuario> buscarTodos(Pageable pageable);

    Usuario buscarUsuarioPorId(Integer idUsuario);

    Page<Usuario> buscarUsuarioPorNombre(String nombre, Pageable pageable);

    Usuario buscarUsuarioPorCorreo(String correo);

    Page<Usuario> buscarUsuarioPorCorreo(String correo, Pageable pageable);

    Usuario login(String correo, String clave);

    void guardarUsuario(Usuario usuario);

    void eliminarUsuario(Integer idUsuario);

}
