
package com.aydsii.demo.service;

import com.aydsii.demo.exception.PersonaNotFoundException;
import com.aydsii.demo.interfaces.IPersonaService;
import com.aydsii.demo.repository.IPersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PersonaService implements IPersonaService {

    private final IPersonaRepository personaRepository;

    @Override
    @Transactional(readOnly = true)
    public Integer consultarPuntos(Integer id) {
        Integer puntos = personaRepository.getPuntosById(id);

        if (puntos == null) {
            throw new PersonaNotFoundException("El id de la persona no existe");
        }

        return puntos;
    }
}

