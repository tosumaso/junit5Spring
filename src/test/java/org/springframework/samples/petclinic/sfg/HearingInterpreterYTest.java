package org.springframework.samples.petclinic.sfg;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@ActiveProfiles("base-test")
@RunWith(SpringRunner.class)
@ContextConfiguration(classes= {BaseConfig.class, YannyConfig.class})
public class HearingInterpreterYTest {

	@Autowired
	HearingInterpreter hearingInterpreter;
	
	@Test
	public void whatIheard() {
		String word = hearingInterpreter.whatIheard();
		assertEquals("Yanny",word);
	}

}
