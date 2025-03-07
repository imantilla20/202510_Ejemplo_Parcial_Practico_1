package co.edu.uniandes.dse.parcialprueba.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;


public interface MedicoRepository extends JpaRepository<MedicoEntity, Long> {
    boolean existsByRegistro(String registro);
}
