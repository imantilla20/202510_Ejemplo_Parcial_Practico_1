package co.edu.uniandes.dse.parcialprueba.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class EspecialidadService {
    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional
    public EspecialidadEntity createEspecialidad(EspecialidadEntity especialidadEntity) throws IllegalOperationException {
        log.info("Inicia proceso de creación de la especialidad");

        if (especialidadEntity.getNombre() == null || especialidadEntity.getNombre().trim().isEmpty()) {
            throw new IllegalOperationException("Una especialidad debe tener un nombre.");
        }

        if (especialidadEntity.getDescripcion() == null || especialidadEntity.getDescripcion().trim().length() < 10) {
            throw new IllegalOperationException("La descripción debe tener al menos 10 caracteres.");
        }

        log.info("Finaliza proceso de creación de la especialidad");
        return especialidadRepository.save(especialidadEntity);
    }
}
