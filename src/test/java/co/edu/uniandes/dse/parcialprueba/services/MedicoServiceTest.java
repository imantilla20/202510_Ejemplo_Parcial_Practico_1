package co.edu.uniandes.dse.parcialprueba.services;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;
import jakarta.transaction.Transactional;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MedicoService.class)
public class MedicoServiceTest {
    
    @Autowired
    private MedicoService medicoService;

    @Autowired
    private TestEntityManager entityManager;

    private PodamFactory factory = new PodamFactoryImpl();
    private List<MedicoEntity> medicoList = new ArrayList<>();

    @BeforeEach
    void setUp() {
        clearData();
        insertData();
    }

    private void clearData() {
        entityManager.getEntityManager().createQuery("DELETE FROM MedicoEntity").executeUpdate();
        entityManager.getEntityManager().createQuery("DELETE FROM EspecialidadEntity").executeUpdate();
    }

    private void insertData() {
        for (int i = 0; i < 3; i++) {
            MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
            medicoEntity.setRegistro("RM" + (1000 + i)); // Asegurar que el registro sea válido
            entityManager.persist(medicoEntity);
            medicoList.add(medicoEntity);
        }
    }

    /**
     * Prueba para verificar que un médico con registro inválido no se puede crear.
     */
    @Test
    void testCreateMedicoRegistroInvalido() {
        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setRegistro("ABC123"); // Registro inválido

        Exception exception = assertThrows(IllegalOperationException.class, () -> {
            medicoService.createMedicos(newEntity);
        });

        assertEquals("El registro médico debe iniciar con 'RM' seguido de números.", exception.getMessage());
    }

    /**
     * Prueba para verificar que no se puede crear un médico con un registro duplicado.
     */
    @Test
    void testCreateMedicoRegistroDuplicado() {
        MedicoEntity existingMedico = medicoList.get(0);

        MedicoEntity newEntity = factory.manufacturePojo(MedicoEntity.class);
        newEntity.setRegistro(existingMedico.getRegistro()); // Registro duplicado

        Exception exception = assertThrows(IllegalOperationException.class, () -> {
            medicoService.createMedicos(newEntity);
        });

        assertEquals("Ya existe un médico con este registro.", exception.getMessage());
    }
}
