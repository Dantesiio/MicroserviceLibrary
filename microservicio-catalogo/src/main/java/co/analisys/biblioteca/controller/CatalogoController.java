package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.model.Libro;
import co.analisys.biblioteca.model.LibroId;
import co.analisys.biblioteca.service.CatalogoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/libros")
public class CatalogoController {
    private final CatalogoService catalogoService;

    @Autowired
    public CatalogoController(CatalogoService catalogoService) {
        this.catalogoService = catalogoService;
    }

    @Operation(
            summary = "Obtener información de un libro",
            description = "Este endpoint permite obtener los detalles de un libro específico mediante su ID."
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_USER')")
    public Libro obtenerLibro(@PathVariable String id) {
        return catalogoService.obtenerLibro(new LibroId(id));
    }

    @Operation(
            summary = "Verificar disponibilidad de un libro",
            description = "Este endpoint permite comprobar si un libro específico está disponible para préstamo."
    )
    @GetMapping("/{id}/disponible")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_USER')")
    public boolean isLibroDisponible(@PathVariable String id) {
        Libro libro = catalogoService.obtenerLibro(new LibroId(id));
        return libro != null && libro.isDisponible();
    }

    @Operation(
            summary = "Actualizar la disponibilidad de un libro",
            description = "Este endpoint permite modificar el estado de disponibilidad de un libro. " +
                    "Solo los bibliotecarios pueden realizar esta acción."
    )
    @PutMapping("/{id}/disponibilidad")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public void actualizarDisponibilidad(@PathVariable String id, @RequestBody boolean disponible) {
        catalogoService.actualizarDisponibilidad(new LibroId(id), disponible);
    }

    @Operation(
            summary = "Buscar libros por criterio",
            description = "Este endpoint permite buscar libros en el catálogo mediante un criterio de búsqueda."
    )
    @GetMapping("/buscar")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_USER')")
    public List<Libro> buscarLibros(@RequestParam String criterio) {
        return catalogoService.buscarLibros(criterio);
    }
}
