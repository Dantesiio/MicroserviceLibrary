package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.model.Email;
import co.analisys.biblioteca.model.Usuario;
import co.analisys.biblioteca.model.UsuarioId;
import co.analisys.biblioteca.service.UsuarioService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @Operation(
            summary = "Consultar información de un usuario",
            description = "Este endpoint permite obtener los datos de un usuario registrado en el sistema mediante su ID."
    )
    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ROLE_LIBRARIAN', 'ROLE_USER')")
    public Usuario obtenerUsuario(@PathVariable String id) {
        return usuarioService.obtenerUsuario(new UsuarioId(id));
    }

    @Operation(
            summary = "Actualizar el correo electrónico de un usuario",
            description = "Este endpoint permite cambiar el correo electrónico de un usuario. " +
                    "Solo los bibliotecarios pueden realizar esta acción."
    )
    @PutMapping("/{id}/email")
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public void cambiarEmail(@PathVariable String id, @RequestBody String nuevoEmail) {
        usuarioService.cambiarEmailUsuario(new UsuarioId(id), new Email(nuevoEmail));
    }
}
