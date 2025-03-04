package co.analisys.biblioteca.controller;

import co.analisys.biblioteca.dto.NotificacionDTO;
import co.analisys.biblioteca.service.NotificacionService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notificar")
public class NotificacionController {
    @Autowired
    private NotificacionService notificacionService;

    @Operation(
            summary = "Enviar una notificación",
            description = "Este endpoint permite a los bibliotecarios enviar notificaciones a los usuarios del sistema."
    )
    @PostMapping
    @PreAuthorize("hasRole('ROLE_LIBRARIAN')")
    public void enviarNotificacion(@RequestBody NotificacionDTO notificacion) {
        notificacionService.enviarNotificacion(notificacion);
    }
}