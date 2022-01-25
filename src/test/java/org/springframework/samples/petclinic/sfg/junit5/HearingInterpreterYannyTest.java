package org.springframework.samples.petclinic.sfg.junit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.sfg.BaseConfig;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.YannyConfig;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@ActiveProfiles("base-test")
//BaseConfig.classとYannyConfig.classの２つをspring contextに投入
@SpringJUnitConfig(classes= {BaseConfig.class, YannyConfig.class})
class HearingInterpreterYannyTest {

	@Autowired
	HearingInterpreter hearingInterpreter;
	
	@Test
	void testWhatIheard() {
		String word = hearingInterpreter.whatIheard();
		assertEquals("Yanny",word);
	}

}
