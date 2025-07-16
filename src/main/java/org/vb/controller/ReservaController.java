package org.vb.controller;

import com.github.fge.jsonpatch.JsonPatchException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.vb.dto.request.CreateReservaDTO;
import org.vb.dto.request.UpdateReservaDTO;
import org.vb.dto.response.ReservaResponseDTO;
import org.vb.service.ReservaService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = {"/reservas", "/reservas/"})
public class ReservaController {
    private final ReservaService reservaService;

    public ReservaController(ReservaService reservaService) {
        this.reservaService = reservaService;
    }

    @Operation(summary = "Crear una reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva creada correctamente"),
            @ApiResponse(responseCode = "400", description = "Ocurrió un error")
    })
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> createReserva(@Valid @RequestBody CreateReservaDTO reserva) {
        ReservaResponseDTO nuevaReserva = reservaService.createReserva(reserva);
        return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
    }

    @Operation(summary = "Listar reservas de un cliente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas del cliente recuperadas correctamente")
    })
    @GetMapping("/cliente/{cliente_id}")
    public List<ReservaResponseDTO> getAllReservasPorCliente(@PathVariable String cliente_id) {
        return reservaService.getReservasPorCliente(cliente_id);
    }

    @Operation(summary = "Listar reservas de un entrenador")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reservas del entrenador recuperadas correctamente")
    })
    @GetMapping("/entrenador/{entrenador_id}")
    public List<ReservaResponseDTO> getAllReservasPorEntrenador(@PathVariable String entrenador_id) {
        return reservaService.getReservasPorEntrenador(entrenador_id);
    }

    @Operation(summary = "Listar datos de una reserva por ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva obtenido correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> getReservaById(@PathVariable UUID id) {
        ReservaResponseDTO reserva = reservaService.getReservaById(id);
        return ResponseEntity.ok(reserva);
    }

    @Operation(summary = "Actualizar datos de una reserva")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Reserva actualizada correctamente"),
            @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @PatchMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> updateReserva(@PathVariable UUID id, @Valid @RequestBody UpdateReservaDTO reservaDetails) throws JsonPatchException, IOException {
        ReservaResponseDTO updatedReserva = reservaService.updateReserva(id, reservaDetails);
        return ResponseEntity.ok(updatedReserva);
    }
}
