package co.edu.uniandes.dse.parcialprueba.services;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import jakarta.transaction.Transactional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;

import co.edu.uniandes.dse.parcialprueba.entities.MedicoEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(MedicoService.class)
class MedicoServiceTest {

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
		entityManager.getEntityManager().createQuery("delete from MedicoEntity");
		
	}


	private void insertData() {

		for (int i = 0; i < 3; i++) {
			MedicoEntity medicoEntity = factory.manufacturePojo(MedicoEntity.class);
			entityManager.persist(medicoEntity);
			medicoList.add(medicoEntity);
		}
	}




    @Test
	void testCreateMedico() throws IllegalOperationException {
		MedicoEntity nuevoMedicoEntity = factory.manufacturePojo(MedicoEntity.class);
        nuevoMedicoEntity.setRegistroMedico("RM1234");
		MedicoEntity medicoCreado = medicoService.createMedico(nuevoMedicoEntity);
		assertNotNull(medicoCreado);

		MedicoEntity medicoEncontrado = entityManager.find(MedicoEntity.class, medicoCreado.getId());
		assertEquals(nuevoMedicoEntity.getId(), medicoEncontrado.getId());
		assertEquals(nuevoMedicoEntity.getNombre(), medicoEncontrado.getNombre());
        assertEquals(nuevoMedicoEntity.getApellido(), medicoEncontrado.getApellido());
        assertEquals(nuevoMedicoEntity.getRegistroMedico(), medicoEncontrado.getRegistroMedico());

	}


    @Test
	void testCreateMedicoConRMInvalido() {
		assertThrows(IllegalOperationException.class, () -> {
			MedicoEntity nuevoMedicoEntity = factory.manufacturePojo(MedicoEntity.class);
			nuevoMedicoEntity.setRegistroMedico("AA1234");
			medicoService.createMedico(nuevoMedicoEntity);
		});
	}

}








