package co.edu.uniandes.dse.parcialprueba.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToMany;
import lombok.Data;
import uk.co.jemos.podam.common.PodamExclude;


@Data
@Entity

public class EspecialidadEntity extends BaseEntity {

    private String nombre;
    private String descripcion;

    @PodamExclude
    @ManyToMany(mappedBy = "especialidades", fetch = FetchType.LAZY)
    private List<MedicoEntity> medicos = new ArrayList<>();
}
