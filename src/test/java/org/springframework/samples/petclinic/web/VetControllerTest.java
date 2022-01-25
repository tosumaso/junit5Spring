package org.springframework.samples.petclinic.web;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.samples.petclinic.model.Vet;
import org.springframework.samples.petclinic.model.Vets;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@ExtendWith(MockitoExtension.class)
class VetControllerTest {
	
	@Mock
	ClinicService service;
	
	@Mock
	Map<String,Object> model;
	
	@InjectMocks
	VetController controller;
	
	List<Vet> vetsList = new ArrayList<Vet>();
	
	//MockMvc : SpringMVCの実行環境をMockするオブジェクト。Tomcat,JSP,Thymeleaf,html作成は使用できない
	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		vetsList.add(new Vet());
		when(service.findVets()).thenReturn(vetsList);
		//SpringMVCのテスト環境を設定する。 
		//standaloneSetup(controller): 軽い、単体のコントローラーをテスト、request,responseのテストがしやすい
		//webAppContextSetup(controller): applicationContextを読み込むため遅い、複数のコントローラーをテストできる、Springのconfigも使える
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
	}
	
	@Test
	void testControllerShowVetList() throws Exception{
		//perform(get(url)) getメソッドでリクエストを送る
		mockMvc.perform(get("/vets.html")) 
			.andExpect(status().isOk()) //statuscodeが200であることを確認
			.andExpect(model().attributeExists("vets")) //modelに指定した変数があることを確認
			.andExpect(view().name("vets/vetList")); //戻り値のview名を確認
	}
	
	@Test
	void testShowVetList() {
		//when
		String view = controller.showVetList(model);
		//then
		verify(service).findVets();
		verify(model).put(anyString(), any(Vets.class));
		assertThat("vets/vetList").isEqualToIgnoringCase(view);
	}

	@Test
	void testShowResourcesVetList() {
		//when
		Vets vets = controller.showResourcesVetList();
		//then
		verify(service).findVets();
		assertThat(vets.getVetList()).hasSize(1);
	}

}
