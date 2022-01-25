package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Profile("base-test")
@Configuration
public class BaseConfig {

	//引数のWordProducerは実装クラスのLaurelWordProducerのbeanを検索し、
	//HearingInterpreterのbeanを作成する引数として使われる?
	@Bean
	HearingInterpreter hearingInterpreter(WordProducer wordProducer) {
		return new HearingInterpreter(wordProducer);
	}
}
