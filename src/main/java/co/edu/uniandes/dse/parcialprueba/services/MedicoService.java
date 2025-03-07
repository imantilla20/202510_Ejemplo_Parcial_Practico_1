package co.edu.uniandes.dse.parcialprueba.services;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public MedicoEntity createMedicos(MedicoEntity medicoEntity) throws IllegalOperationException {
        log.info("Inicia proceso de creación del medico");

        if (medicoEntity.getNombre() == null || medicoEntity.getNombre().isEmpty()) {
            throw new IllegalOperationException("Un medico debe tener un nombre.");
        }

        if (medicoEntity.getApellido() == null || medicoEntity.getApellido().isEmpty()) {
            throw new IllegalOperationException("Un medico debe tener un apellido.");
        }

        if (medicoEntity.getRegistro() == null || !medicoEntity.getRegistro().matches("^RM\\d+$")) {
            throw new IllegalOperationException("El registro médico debe iniciar con 'RM' seguido de números.");
        }

        if (medicoRepository.existsByRegistro(medicoEntity.getRegistro())) {
            throw new IllegalOperationException("Ya existe un médico con este registro.");
        }

        log.info("Finaliza proceso de creación del medico");
        return medicoRepository.save(medicoEntity);
    }
}
