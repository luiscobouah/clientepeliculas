package es.uah.clientepeliculas.controller;


import es.uah.clientepeliculas.model.Actor;
import es.uah.clientepeliculas.model.Critica;
import es.uah.clientepeliculas.model.Pelicula;
import es.uah.clientepeliculas.paginator.PageRender;
import es.uah.clientepeliculas.service.IActoresService;
import es.uah.clientepeliculas.service.ICriticasService;
import es.uah.clientepeliculas.service.IPeliculasService;
import es.uah.clientepeliculas.service.IUploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

@Controller
@RequestMapping("/cpeliculas")
public class PeliculasController {

    @Autowired
    IPeliculasService peliculasService;

    @Autowired
    IActoresService actoresService;

    @Autowired
    private IUploadFileService uploadFileService;

    @Autowired
    ICriticasService criticasService;

    @GetMapping("/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

        Resource recurso = null;

        try {
            recurso = uploadFileService.load(filename);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }

    @GetMapping(value = {"/", "/home", ""})
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/nuevaPelicula")
    public String nuevo(Model model) {
        List<Actor> listado = actoresService.buscarTodos();
        model.addAttribute("titulo", "Nueva película");
        Pelicula pelicula = new Pelicula();
        model.addAttribute("pelicula", pelicula);
        model.addAttribute("listadoActores", listado);
        return "peliculas/formPelicula";
    }

    @GetMapping("/buscarPeliculas")
    public String buscar(Model model,@RequestParam("origen") String origen) {
        List<Actor> listado = actoresService.buscarTodos();
        model.addAttribute("listadoActores", listado);
        model.addAttribute("origen", origen);
        return "peliculas/searchPelicula";
    }

    @GetMapping("/peliculas")
    public String listadoPeliculas(Model model, @RequestParam(name="page", defaultValue="0") int page) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listado = peliculasService.buscarTodas(pageable);
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/cpeliculas/peliculas", listado);
        List<Actor> listadoActores = actoresService.buscarTodos();
        model.addAttribute("listadoActores", listadoActores);
        model.addAttribute("titulo", "Listado de todas las películas");
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("origen", "home");
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula";
    }

    @GetMapping(value = "/ver/{id}")
    public String ver(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        Pelicula pelicula = peliculasService.buscarPeliculaPorId(id);
        Pageable pageable = PageRequest.of(0, 20);
        List<Critica> criticas = criticasService.buscarCriticasPorIdPelicula(id);
        Integer sumatoria = 0;
        for (Critica critica : criticas) {
            sumatoria = sumatoria + critica.getNota();
        }
        String notaMedia;
        if (criticas.size()==0){
            notaMedia = "";
        } else {
            notaMedia = String.valueOf(sumatoria/criticas.size());
        }

        model.addAttribute("pelicula", pelicula);
        model.addAttribute("criticas", criticas);
        model.addAttribute("notaMedia", notaMedia);
        model.addAttribute("titulo", "Detalle de la pelicula: " + pelicula.getTitulo());
        return "peliculas/verPelicula";
    }

    @GetMapping("/idpelicula/{id}")
    public String buscarPeliculaPorId(Model model, @PathVariable("id") Integer id) {
        Pelicula pelicula = peliculasService.buscarPeliculaPorId(id);
        model.addAttribute("pelicula", pelicula);
        return "peliculas/formPelicula";
    }

    @GetMapping("/titulo")
    public String buscarPeliculasPorTitulo(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("tituloPel") String tituloPel, @RequestParam("origen") String origen) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listado = peliculasService.buscarPeliculaPorTitulo(tituloPel, pageable);
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/cpeliculas/peliculas", listado);
        List<Actor> listadoActores = actoresService.buscarTodos();
        model.addAttribute("tituloPel", tituloPel);
        model.addAttribute("listadoActores", listadoActores);
        model.addAttribute("titulo", "Listado de peliculas por titulo");
        model.addAttribute("origen", origen);
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula";
    }

    @GetMapping("/genero")
    public String buscarPeliculasPorGenero(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("genero") String genero, @RequestParam("origen") String origen) {
        Pageable pageable = PageRequest.of(page, 5);
        Page<Pelicula> listado = peliculasService.buscarPeliculaPorGenero(genero, pageable);
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/cpeliculas/peliculas", listado);
        List<Actor> listadoActores = actoresService.buscarTodos();
        model.addAttribute("genero", genero);
        model.addAttribute("listadoActores", listadoActores);
        model.addAttribute("titulo", "Listado de peliculas por genero");
        model.addAttribute("origen", origen);
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula";
    }

    @GetMapping("/actor")
    public String buscarPeliculasPorActor(Model model, @RequestParam(name="page", defaultValue="0") int page, @RequestParam("idActor") Integer idActor, @RequestParam("origen") String origen) {
        Pageable pageable = PageRequest.of(page, 5);
        Actor actor = actoresService.buscarActorPorId(idActor);
        List<Pelicula> lista = actor.getPeliculas();
        Page<Pelicula> listado = new PageImpl<>(lista, pageable, lista.size());
        PageRender<Pelicula> pageRender = new PageRender<Pelicula>("/cpeliculas/peliculas", listado);
        List<Actor> listadoActores = actoresService.buscarTodos();
        model.addAttribute("idActor", idActor);
        model.addAttribute("listadoActores", listadoActores);
        model.addAttribute("titulo", "Listado de peliculas por actor");
        model.addAttribute("origen", origen);
        model.addAttribute("listadoPeliculas", listado);
        model.addAttribute("page", pageRender);
        return "peliculas/listPelicula";
    }

    @PostMapping("/guardar/")
    public String guardarPelicula(Model model, Pelicula pelicula, @RequestParam("file") MultipartFile foto, @RequestParam("idActor") List<Integer> idActor, RedirectAttributes attributes) {
    	if(pelicula != null) {
    		System.out.println(idActor);
    	}
        if (!foto.isEmpty()) {
            if (pelicula.getIdPelicula()!= null && pelicula.getIdPelicula() > 0 && pelicula.getImagen() != null
                    && pelicula.getImagen().length() > 0) {

                uploadFileService.delete(pelicula.getImagen());
            }
            String uniqueFilename = null;
            try {
                uniqueFilename = uploadFileService.copy(foto);
            } catch (IOException e) {
                e.printStackTrace();
            }
            attributes.addFlashAttribute("msg", "Has subido correctamente '" + uniqueFilename + "'");
            pelicula.setImagen(uniqueFilename);
        }
        for (Integer id : idActor) {
            pelicula.addActor(actoresService.buscarActorPorId(id));
        }
        peliculasService.guardarPelicula(pelicula);
        model.addAttribute("titulo", "Nueva pelicula");
        attributes.addFlashAttribute("msg", "Los datos de la pelicula fueron guardados!");
        return "redirect:/cpeliculas/peliculas";
    }

    @GetMapping("/editar/{id}")
    public String editarPelicula(Model model, @PathVariable("id") Integer id) {
        List<Actor> listadoTodos = actoresService.buscarTodos();
        Pelicula pelicula = peliculasService.buscarPeliculaPorId(id);
        model.addAttribute("listadoActores", listadoTodos);
        model.addAttribute("titulo", "Editar pelicula");
        model.addAttribute("pelicula", pelicula);
        return "peliculas/formPelicula";
    }

    @GetMapping("/borrar/{id}")
    public String eliminarPelicula(Model model, @PathVariable("id") Integer id, RedirectAttributes attributes) {
        peliculasService.eliminarPelicula(id);
        attributes.addFlashAttribute("msg", "Los datos de la pelicula fueron borrados!");
        return "redirect:/cpeliculas/peliculas";
    }

}
