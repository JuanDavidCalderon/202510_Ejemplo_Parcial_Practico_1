package co.edu.uniandes.dse.parcialprueba.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;

import co.edu.uniandes.dse.parcialprueba.repositories.MedicoRepository;


import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
public class MedicoService {

    @Autowired
    MedicoRepository medicoRepository;


    @Transactional
    public MedicoEntity createMedico(MedicoEntity medicoEntity) throws IllegalOperationException {
        String registroMedico=medicoEntity.getRegistroMedico();
        registroMedico.toCharArray();

        
        if(registroMedico.charAt(0)!='R' || registroMedico.charAt(1)!='M'){
            throw new IllegalOperationException("RM no son los dos primero caracteres");
        }
        
        return medicoRepository.save(medicoEntity);
        


}



}