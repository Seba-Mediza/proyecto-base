
package com.aydsii.demo.controller;

import com.aydsii.demo.dto.APIResponse;
import com.aydsii.demo.interfaces.IPersonaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/personas")
@RequiredArgsConstructor
@Tag(name = "Personas", description = "CU-03 Consultar puntos")
public class PersonaController {

    private final IPersonaService service;

    @Operation(
            summary = "Consultar puntos de un cliente",
            description = "Recupera el total de puntos acumulados en la cuenta de una persona."
    )
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "Puntos recuperados correctamente"),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "404",
                    description = "No se encontró la persona")
    })
    @GetMapping("/{id}/puntos")
    public ResponseEntity<APIResponse<Integer>> consultarPuntos(
            @PathVariable Integer id) {

        Integer puntos = service.consultarPuntos(id);

        return ResponseEntity.ok(
                APIResponse.ok("Puntos recuperados correctamente", puntos)
        );
    }
}