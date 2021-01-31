package es.uah.clientepeliculas.controller;


import es.uah.clientepeliculas.model.Actor;
import es.uah.clientepeliculas.model.Pelicula;
import es.uah.clientepeliculas.paginator.PageRender;
import es.uah.clientepeliculas.service.IActoresService;
import es.uah.clientepeliculas.service.IPeliculasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/cactores")
public class ActoresController {

    @Autowired
    IActoresService actoresService;

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/nuevoActor")
    public String nuevo(Model model) {
        model.addAttribute("titulo", "Nuevo actor");
        Actor actor = new Actor();
        model.addAttribute("actor", actor);
        return "actores/formActor";
    }

    @GetMapping("/actores")
    public String listadoActores(Model model, @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Actor> listado = actoresService.buscarTodos(pageable);
        PageRender<Actor> pageRender = new PageRender<Actor>("/cactores/actores", listado);
        model.addAttribute("titulo", "Listado de todos los actores");
        model.addAttribute("origen", "home");
        model.addAttribute("listadoActores", listado);
        model.addAttribute("page", pageRender);
        return "actores/listActor";
    }

    @GetMapping("/idactor/{id}")
    public String buscarActorPorId(Model model, @PathVariable("id") Integer id) {
        Actor actor = actoresService.buscarActorPorId(id);
        model.addAttribute("pelicula", actor);
        return "actores/formActor";
    }


    @PostMapping("/guardar/")
    public String guardarActor(Model model, Actor actor, RedirectAttributes attributes) {
    	if(actor != null) {
    		System.out.println(actor.getNombre());
    	}
        actoresService.guardarActor(actor);
        model.addAttribute("titulo", "Nuevo actor");
        attributes.addFlashAttribute("msg", "Los datos del actor fueron guardados!");
        return "redirect:/cactores/actores";
    }

    @GetMapping("/editar/{id}")
    public String editarActor(Model model, @PathVariable("id") Integer id) {
        Actor actor = actoresService.buscarActorPorId(id);
        model.addAttribute("titulo", "Editar actor");
        model.addAttribute("actor", actor);
        return "actores/formActor";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarActor(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        actoresService.eliminarActor(id);
        attributes.addFlashAttribute("msg", "Los datos del actor fueron borrados!");
        return "redirect:/cactores/actores";
    }

}
