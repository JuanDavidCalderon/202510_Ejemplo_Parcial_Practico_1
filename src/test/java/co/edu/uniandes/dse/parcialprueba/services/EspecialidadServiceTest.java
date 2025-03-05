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

import co.edu.uniandes.dse.parcialprueba.entities.EspecialidadEntity;
import co.edu.uniandes.dse.parcialprueba.exceptions.IllegalOperationException;

import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

@DataJpaTest
@Transactional
@Import(EspecialidadService.class)
class EspecialidadServiceTest {

	@Autowired
	private EspecialidadService especialidadService;

	@Autowired
	private TestEntityManager entityManager;

	private PodamFactory factory = new PodamFactoryImpl();

	private List<EspecialidadEntity> especialidadList = new ArrayList<>();


	@BeforeEach
	void setUp() {
		clearData();
		insertData();
	}


	private void clearData() {
		
		entityManager.getEntityManager().createQuery("delete from EspecialidadEntity");
	}


	private void insertData() {

		for (int i = 0; i < 3; i++) {
			EspecialidadEntity especialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
			entityManager.persist(especialidadEntity);
			especialidadList.add(especialidadEntity);
		}
	}




    @Test
	void testCreateEspecialidad() throws IllegalOperationException {
		EspecialidadEntity nuevoEspecialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
        nuevoEspecialidadEntity.setDescripcion("abcdefcghijklm");
		EspecialidadEntity especialidadCreado = especialidadService.createEspecialidad(nuevoEspecialidadEntity);
		assertNotNull(especialidadCreado);

		EspecialidadEntity especialidadEncontrado = entityManager.find(EspecialidadEntity.class, especialidadCreado.getId());
		assertEquals(nuevoEspecialidadEntity.getId(), especialidadEncontrado.getId());
		assertEquals(nuevoEspecialidadEntity.getNombre(), especialidadEncontrado.getNombre());
        assertEquals(nuevoEspecialidadEntity.getDescripcion(), especialidadEncontrado.getDescripcion());
        
	}


    @Test
	void testCreateEspecialidadDescripcionInvalida() {
		assertThrows(IllegalOperationException.class, () -> {
			EspecialidadEntity nuevoEspecialidadEntity = factory.manufacturePojo(EspecialidadEntity.class);
			nuevoEspecialidadEntity.setDescripcion("ABCD");
			especialidadService.createEspecialidad(nuevoEspecialidadEntity);
		});
	}

}









