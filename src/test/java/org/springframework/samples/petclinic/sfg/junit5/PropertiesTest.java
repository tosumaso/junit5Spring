package org.springframework.samples.petclinic.sfg.junit5;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.samples.petclinic.sfg.HearingInterpreter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

//@TestPropertySource("classpath:resourcesからの相対パス")で指定したpropertiesファイルを参照する
@TestPropertySource("classpath:yanny.properties")
@ActiveProfiles("externalized")
@SpringJUnitConfig(classes= PropertiesTest.TestConfig.class)
class PropertiesTest implements BeanFactoryAware{

	@Configuration
	@ComponentScan("org.springframework.samples.petclinic.sfg")
	static class TestConfig {

	}

	private DefaultListableBeanFactory beanFactory;
	
	@Autowired
	HearingInterpreter hearingInterpreter;

	@Test
	void testWhatIheard() {
		for (String name : beanFactory.getBeanDefinitionNames()) {
			System.out.println(name);
		}
		String word = hearingInterpreter.whatIheard();
		assertEquals("YaNNY", word);
	}
	
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (DefaultListableBeanFactory)beanFactory;
		
	}
}
