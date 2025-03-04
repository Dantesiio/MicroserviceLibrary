package co.analisys.biblioteca.controller;
import co.analisys.biblioteca.model.LibroId;
import co.analisys.biblioteca.model.Prestamo;
import co.analisys.biblioteca.model.PrestamoId;
import co.analisys.biblioteca.model.UsuarioId;
import co.analisys.biblioteca.service.CirculacionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/circulacion")
public class CirculacionController {
    @Autowired
    private CirculacionService circulacionService;
    @Operation(
            summary = "Prestar un libro",
            description = "Este endpoint permite registrar el préstamo de un libro a un usuario. " +
                    "El usuario debe estar registrado y tener permisos para realizar préstamos."
    )
    @PostMapping("/prestar")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public void prestarLibro(@RequestParam String usuarioId, @RequestParam String libroId) {
        circulacionService.prestarLibro(new UsuarioId(usuarioId), new LibroId(libroId));
    }

    @Operation(
            summary = "Devolver un libro",
            description = "Este endpoint permite registrar la devolución de un libro previamente prestado. " +
                    "El préstamo debe existir en el sistema para poder ser devuelto."
    )
    @PostMapping("/devolver")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public void devolverLibro(@RequestParam String prestamoId) {
        circulacionService.devolverLibro(new PrestamoId(prestamoId));
    }

    @Operation(
            summary = "Consultar todos los préstamos",
            description = "Este endpoint permite obtener una lista de todos los préstamos registrados en el sistema. " +
            "Es importante que el cliente esté registrado previamente en la base de datos, " +
            "de lo contrario no podrá acceder a esta información."
    )
    @GetMapping("/prestamos")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_USER')")
    public List<Prestamo> obtenerTodosPrestamos() {
        return circulacionService.obtenerTodosPrestamos();
    }
    @GetMapping("/public/status")
    public String getPublicStatus() {
        return "El servicio de circulación está funcionando correctamente";
    }
}