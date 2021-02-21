package es.uah.clientepeliculas.controller;

import es.uah.clientepeliculas.model.Actor;
import es.uah.clientepeliculas.model.Critica;
import es.uah.clientepeliculas.model.Pelicula;
import es.uah.clientepeliculas.model.Usuario;
import es.uah.clientepeliculas.paginator.PageRender;
import es.uah.clientepeliculas.service.ICriticasService;
import es.uah.clientepeliculas.service.IPeliculasService;
import es.uah.clientepeliculas.service.IUsuariosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.security.Principal;
import java.util.List;


@Controller
@RequestMapping("/ccriticas")
public class CriticasController {
    @Autowired
    ICriticasService criticasService;

    @Autowired
    IUsuariosService usuariosService;

    @Autowired
    IPeliculasService peliculasService;

    @GetMapping("/listado")
    public String listadoCriticas(Model model,Principal principal,  @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Critica> listado;
        //Usuario usuario = usuariosService.buscarUsuarioPorCorreo(principal.getName());
        listado = criticasService.buscarTodas(pageable);
        PageRender<Critica> pageRender = new PageRender<Critica>("/ccriticas/listado", listado);
        for (Critica critica : listado) {
            Pelicula pelicula = peliculasService.buscarPeliculaPorId(critica.getIdPelicula());
            critica.setPelicula(pelicula);
        }
       /* if (usuario.getRoles().get(0).getAuthority().equals("ROLE_ADMIN")) {
            listado = criticasService.buscarTodas(pageable);
        } else {
            listado = criticasService.buscarCriticasPorIdUsuario(usuario.getIdUsuario(), pageable);
        }
        PageRender<Critica> pageRender = new PageRender<Critica>("/ccriticas/listado", listado);
        for (Critica critica : listado) {
            Pelicula pelicula = peliculasService.buscarPeliculaPorId(critica.getIdPelicula());
            critica.setPelicula(pelicula);
        }*/
        model.addAttribute("titulo", "Listado de todas las criticas");
        model.addAttribute("listadoCriticas", listado);
        model.addAttribute("page", pageRender);
        return "criticas/listCriticas";
    }

    @GetMapping("/pelicula")
    public String buscarCriticasPorPelicula(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("pelicula") String nombrePelicula, @RequestParam("origen") String origen) {
        Pageable pageable = PageRequest.of(page, 5);
        Pelicula pelicula = peliculasService.buscarPeliculaPorTitulo(nombrePelicula);
        Integer idPelicula;
        if(pelicula == null ){
            idPelicula = 0;
        } else {
            idPelicula = pelicula.getIdPelicula();
        }
        Page<Critica> listado = criticasService.buscarCriticasPorIdPelicula(idPelicula, pageable);
        for (Critica critica : listado) {
            critica.setPelicula(pelicula);
        }
        PageRender<Critica> pageRender = new PageRender<Critica>("/ccriticas/listado", listado);
        model.addAttribute("titulo", "Listado de todas las criticas");
        model.addAttribute("origen", origen);
        model.addAttribute("listadoCriticas", listado);
        model.addAttribute("page", pageRender);
        return "criticas/listCriticas";
    }

    @GetMapping("/nueva/{idPelicula}")
    public String nuevo(@PathVariable("idPelicula") Integer idPelicula,Model model) {
        Critica critica = new Critica();
        critica.setIdPelicula(idPelicula);
        model.addAttribute("titulo", "Nueva Critica");
        model.addAttribute("critica", critica);
        return "criticas/formCritica";
    }

    @PostMapping("/guardar/")
    public String guardarCritica(Model model,Principal principal, Critica critica, RedirectAttributes attributes) {
        Usuario usuario = usuariosService.buscarUsuarioPorCorreo(principal.getName());
        critica.setUsuario(usuario);
        String resultado = criticasService.guardarCritica(critica);
        model.addAttribute("titulo", "Nueva critica");
        attributes.addFlashAttribute("msg", resultado);
        return "redirect:/cpeliculas/ver/"+critica.getIdPelicula();
    }

    @GetMapping("/editar/{id}")
    public String editarCritica(Model model, @PathVariable("id") Integer id) {
        Critica critica = criticasService.buscarCriticaPorId(id);
        model.addAttribute("titulo", "Editar critica");
        model.addAttribute("critica", critica);
        return "criticas/formCritica";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarCritica(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        Critica critica = criticasService.buscarCriticaPorId(id);
        if (critica != null) {
            criticasService.eliminarCritica(id);
            attributes.addFlashAttribute("msg", "La critica fue borrada!");
        } else {
            attributes.addFlashAttribute("msg", "critica no encontrada!");
        }
        return "redirect:/cpeliculas/ver/"+critica.getIdPelicula();
    }

}
