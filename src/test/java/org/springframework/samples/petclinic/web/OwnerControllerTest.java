package org.springframework.samples.petclinic.web;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Map;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.model.Owner;
import org.springframework.samples.petclinic.service.ClinicService;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

//xmlファイルからMockitoのbeanを読み込んでもmockの初期化が行われていない: 初期化が必要
//src/resourcesからの相対パスでxmlファイルを読み込む
@ExtendWith(MockitoExtension.class)
@SpringJUnitWebConfig(locations= {"classpath:spring/mvc-test-config.xml", "classpath:spring/mvc-core-config.xml"})
class OwnerControllerTest {

	@Autowired
	OwnerController ownerController;
	
	@Autowired
	ClinicService clinicService;
	
	@Mock
	Map<String,Object> model;
	
	@Captor
	ArgumentCaptor<String> stringArgumentCaptor;
	
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(ownerController).build();
	}
	
	//TooManyActualInvocationsを防ぐためにmockオブジェクトを@Testごとにリセットする
	@AfterEach
	void tearDown() {
		reset(clinicService);
	}
	
	@Test
	void testNewOwnerPostValid() throws Exception {
		//postmethodで値を登録するときのvalidation
		//.param()で複数のパラメーターを送れる
		mockMvc.perform(post("/owners/new")
			.param("firstName", "Jimmy")
			.param("lastName", "Buffett")
			.param("Address", "123 Duval St")
			.param("city", "Key West")
			.param("telephone","1234567890"))
		.andExpect(status().is3xxRedirection());
	}
	
	@Test
	void testNewOwnerPostNotValid() throws Exception {
		//パラメータが不足することでFormオブジェクトに例外が発生する
		mockMvc.perform(post("/owners/new")
			.param("firstName", "Jimmy")
			.param("lastName", "Buffett")
			.param("city", "Key West"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("owner")) //Formオブジェクト"owner"にエラーがあること立証
		.andExpect(model().attributeHasFieldErrors("owner", "address")) //Formオブジェクトのフィールドにエラーがあることを立証
		.andExpect(model().attributeHasFieldErrors("owner","telephone"))
		.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}
	
	@Test
	void testNewOwnerPostOk() throws Exception {
		//post("url",URLparameter) : {}で囲った変数に第二引数のパラメータを代入する
		mockMvc.perform(post("/owners/{ownerId}/edit",1)
			.param("firstName", "Jimmy")
			.param("lastName", "Buffett")
			.param("Address", "123 Duval ST")
			.param("city", "Key West")
			.param("telephone", "1234567890"))
		.andExpect(status().is3xxRedirection())
		.andExpect(view().name("redirect:/owners/{ownerId}"));
	}
	
	@Test
	void testNewOwnerPostNotOk() throws Exception {
		mockMvc.perform(post("/owners/{ownerId}/edit",1)
			.param("firstName", "Jimmy")
			.param("lastName", "Buffett")
			.param("Address", "123 Duval ST")
			.param("telephone", "1234567890"))
		.andExpect(status().isOk())
		.andExpect(model().attributeHasErrors("owner"))
		.andExpect(model().attributeHasFieldErrors("owner", "city"))
		.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}
	
	@Test
	void testFindByNameNotFound() throws Exception{
		mockMvc.perform(get("/owners")
			.param("lastName", "Dont find me")) //param(name,value) :HttpRequestを送るときのパラメータを設定
			.andExpect(status().isOk())
			.andExpect(view().name("owners/findOwners"));
	}
	
	@Test
	void testReturnListOfOwners() throws Exception{
		//given
		when(clinicService.findOwnerByLastName("")).thenReturn(Lists.newArrayList(new Owner(), new Owner()));
		//when
		mockMvc.perform(get("/owners"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/ownersList"));
		//then
		//mockmethodを呼び出すための引数を取得し,assertで証明する
		verify(clinicService).findOwnerByLastName(stringArgumentCaptor.capture());
		assertThat(stringArgumentCaptor.getValue()).isEqualToIgnoringCase("");
	}
	
	@Test
	void testFindOwnerOneResult() throws Exception{
		Owner owner = new Owner();
		owner.setId(1);
		final String findOne = "FindOne";
		owner.setLastName(findOne);
		
		when(clinicService.findOwnerByLastName(findOne)).thenReturn(Lists.newArrayList(owner));
		
		mockMvc.perform(get("/owners")
			.param("lastName", findOne))
			.andExpect(status().is3xxRedirection()) //redirectを証明
			.andExpect(view().name("redirect:/owners/1"));
		
		verify(clinicService).findOwnerByLastName(anyString());
	}
	
	@Test
	void initCreationForm() throws Exception{	
		mockMvc.perform(get("/owners/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("owner"))
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

}
