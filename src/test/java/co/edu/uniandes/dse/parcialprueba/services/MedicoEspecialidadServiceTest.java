package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.dse.parcialprueba.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;

@DataJpaTest
@Transactional
@Import(MedicoEspecialidadService.class)
public class MedicoEspecialidadServiceTest {

    @Autowired
	private TestEntityManager entityManager;

	@Autowired
	private MedicoEspecialidadService medicoEspecialidadService;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<MedicoEntity> medicoList = new ArrayList<>();
	private List<EspecialidadEntity> especialidadList = new ArrayList<>();




@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}


	private void clearData() {
		
		entityManager.getEntityManager().createQuery("delete from EspecialidadEntity").executeUpdate();
		entityManager.getEntityManager().createQuery("delete from MedicoEntity").executeUpdate();
	}


	private void insertData() {

		for (int i = 0; i < 3; i++) {
			EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
			entityManager.persist(especialidadEntity);
			especialidadList.add(especialidadEntity);
		}

        for (int i = 0; i < 3; i++) {
			MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
			entityManager.persist(medicoEntity);
			medicoList.add(medicoEntity);
		}
	}


    @Test
	void testAddEspecialidad() throws EntityNotFoundException{
		MedicoEntity nuevoMedico = factory.manufacturePojo(MedicoEntity.class);
		entityManager.persist(nuevoMedico);
		
		EspecialidadEntity especialidad = factory.manufacturePojo(EspecialidadEntity.class);
		entityManager.persist(especialidad);
		
		medicoEspecialidadService.addEspecialidad(nuevoMedico.getId(), especialidad.getId());
		
	
	}

    @Test
	void testAddEspecialidadInvalidMedico() {
		assertThrows(EntityNotFoundException.class, ()->{
			MedicoEntity nuevoMedico = factory.manufacturePojo(MedicoEntity.class);
			entityManager.persist(nuevoMedico);
			medicoEspecialidadService.addEspecialidad(nuevoMedico.getId(), 0L);
		});
	}
	
	
	@Test
	void testAddInvalidEspecialidad() {
		assertThrows(EntityNotFoundException.class, ()->{
			EspecialidadEntity especialidad= factory.manufacturePojo(EspecialidadEntity.class);
			entityManager.persist(especialidad);
			medicoEspecialidadService.addEspecialidad(0L, especialidad.getId());
		});
	}


	


}
