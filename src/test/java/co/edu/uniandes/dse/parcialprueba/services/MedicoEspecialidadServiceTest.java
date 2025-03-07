package co.edu.uniandes.dse.parcialprueba.services;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MedicoEspecialidadService.class)
public class MedicoEspecialidadServiceTest {

    @Autowired
    private MedicoEspecialidadService medicoEspecialidadService;
    
    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();

    private MedicoEntity medico;
    private List<EspecialidadEntity> especialidadList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
    }

    private void insertData() {
        medico = factory.manufacturePojo(MedicoEntity.class);
        entityManager.persist(medico);

        for (int i = 0; i < 3; i++) {
            EspecialidadEntity entity = factory.manufacturePojo(EspecialidadEntity.class);
            entityManager.persist(entity);
            especialidadList.add(entity);
        }
    }

    @Test
    void testAddEspecialidad() throws EntityNotFoundException {
        EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
        entityManager.persist(especialidad);

        MedicoEntity result = medicoEspecialidadService.addEspecialidad(medico.getId(), especialidad.getId());

        assertTrue(result.getEspecialidades().contains(especialidad));
    }

    @Test
    void testAddEspecialidad_MedicoNotFound() {
        EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
        entityManager.persist(especialidad);

        assertThrows(EntityNotFoundException.class, () -> {
            medicoEspecialidadService.addEspecialidad(0L, especialidad.getId());
        });
    }

    @Test
    void testAddEspecialidad_EspecialidadNotFound() {
        assertThrows(EntityNotFoundException.class, () -> {
            medicoEspecialidadService.addEspecialidad(medico.getId(), 0L);
        });
    }
}
