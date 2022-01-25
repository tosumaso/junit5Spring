package org.springframework.samples.petclinic.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.PetType;
import org.springframework.samples.petclinic.repository.PetRepository;

@ExtendWith(MockitoExtension.class)
class ClinicServiceImplTest {

	@Mock
	PetRepository petRepository;
	
	@InjectMocks
	ClinicServiceImpl service;
	
	@Test
	void testFindPetTypes() {
		//given
		List<PetType> lists = new ArrayList<PetType>();
		lists.add(new PetType());
		when(petRepository.findPetTypes()).thenReturn(lists);
		//when
		List<PetType> types = (List<PetType>) service.findPetTypes();
		//then
		verify(petRepository).findPetTypes();
		assertThat(types).hasSize(1);
		assertThat(types).isNotNull();
	}

}
