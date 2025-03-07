package co.edu.uniandes.dse.parcialprueba.services;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService {

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Autowired
    private MedicoRepository medicoRepository;

    @Transactional
    public MedicoEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException {
        log.info("Inicia proceso de asociar especialidad con médico: medicoId={}, especialidadId={}", medicoId, especialidadId);

        Optional<MedicoEntity> medicoEntity = medicoRepository.findById(medicoId);
        Optional<EspecialidadEntity> especialidadEntity = especialidadRepository.findById(especialidadId);

        if (medicoEntity.isEmpty()) {
            throw new EntityNotFoundException("No se encontró el médico con ID: " + medicoId);
        }

        if (especialidadEntity.isEmpty()) {
            throw new EntityNotFoundException("No se encontró la especialidad con ID: " + especialidadId);
        }

        medicoEntity.get().getEspecialidades().add(especialidadEntity.get());
        log.info("Finaliza proceso de asociar especialidad con médico");

        return medicoEntity.get();
    }
}
