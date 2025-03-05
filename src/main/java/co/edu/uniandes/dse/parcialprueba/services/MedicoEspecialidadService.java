package co.edu.uniandes.dse.parcialprueba.services;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;
import co.edu.uniandes.dse.parcialprueba.repositories.EspecialidadRepository;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import java.util.Optional;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MedicoEspecialidadService {

    @Autowired
    private MedicoRepository medicoRepository;

    @Autowired
    private EspecialidadRepository especialidadRepository;

    @Transactional
    public EspecialidadEntity addEspecialidad(Long medicoId, Long especialidadId) throws EntityNotFoundException {
        Optional<MedicoEntity> medicoExistente= medicoRepository.findById(medicoId);
        if (medicoExistente.isEmpty()){
            throw new EntityNotFoundException("Medico No ENocntrado");
        }

        Optional<EspecialidadEntity> especialidadExistente= especialidadRepository.findById(especialidadId);
        if (especialidadExistente.isEmpty()){
            throw new EntityNotFoundException("Especialidad No Encontrada");
        }

        medicoExistente.get().getEspecialidades().add(especialidadExistente.get());

        return especialidadExistente.get();



    }
}
