package es.uah.clientepeliculas.controller;

import es.uah.clientepeliculas.model.Critica;
import es.uah.clientepeliculas.model.Usuario;
import es.uah.clientepeliculas.paginator.PageRender;
import es.uah.clientepeliculas.service.ICriticasService;
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


@Controller
@RequestMapping("/ccriticas")
public class CriticasController {
    @Autowired
    ICriticasService criticasService;

    @Autowired
    IUsuariosService usuariosService;

    @GetMapping("/listado")
    public String listadoCriticas(Model model, @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Critica> listado = criticasService.buscarTodas(pageable);
        PageRender<Critica> pageRender = new PageRender<Critica>("/ccriticas/listado", listado);
        model.addAttribute("titulo", "Listado de todas las criticas");
        model.addAttribute("listadoCriticas", listado);
        model.addAttribute("page", pageRender);
        return "ccriticas/listCriticas";
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
