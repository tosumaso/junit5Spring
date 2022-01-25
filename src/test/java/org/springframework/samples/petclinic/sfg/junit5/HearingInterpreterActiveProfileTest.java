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
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
//@ActiveProfiles(name) : @Profile(name)で指定したBeanを有効化する(@Profileで指定したBeanは@ActiveProfilesで指定しない限りBeanに登録されない)
@ActiveProfiles("yanny")
@SpringJUnitConfig(classes= HearingInterpreterActiveProfileTest.TestConfig.class)
//BeanFactoryAware : ApplicationContextに登録されているBeanを操作できる。
class HearingInterpreterActiveProfileTest implements BeanFactoryAware{

	@Configuration
	@ComponentScan("org.springframework.samples.petclinic.sfg")
	static class TestConfig {
		
	}
	
	//DefaultListableBeanFactory.getBeanDefinitionNames() : Contextに登録されているbeanの名前の一覧を取得
	private DefaultListableBeanFactory beanFactory;
	
	@Autowired
	HearingInterpreter hearingInterpreter;
	
	@Test
	void testWhatIheard() {
		for (String name: beanFactory.getBeanDefinitionNames()) {
			System.out.println(name);
		}
		String word = hearingInterpreter.whatIheard();
		assertEquals("Yanny",word);
	}

	//BeanFactory : SpringContainerにアクセスするためのsetterメソッドをオーバーライド
	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = (DefaultListableBeanFactory)beanFactory;
		
	}

}
