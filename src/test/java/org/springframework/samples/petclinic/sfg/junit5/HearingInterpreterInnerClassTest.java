package org.springframework.samples.petclinic.sfg.junit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.samples.petclinic.sfg.LaurelWordProducer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ActiveProfiles("inner-class")
@ExtendWith(SpringExtension.class)
//Testクラス内で定義したConfigクラスを読み込んでいる。簡単なConfigクラスを読み込むのに便利
@ContextConfiguration(classes= {HearingInterpreterInnerClassTest.TestConfig.class})
class HearingInterpreterInnerClassTest {

	@Profile("inner-class")
	//static class クラス名でConfigクラスを定義、通常のBean登録もできる
	@Configuration
	static class TestConfig {
		@Bean
		HearingInterpreter hearingInterpreter() {
			return new HearingInterpreter(new LaurelWordProducer());
		}
	}
	
	@Autowired
	HearingInterpreter hearingInterpreter;
	
	@Test
	void testWhatIheard() {
		String word = hearingInterpreter.whatIheard();
		assertEquals("Laurel",word);
	}

}
