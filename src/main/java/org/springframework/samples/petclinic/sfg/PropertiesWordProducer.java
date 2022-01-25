package org.springframework.samples.petclinic.sfg;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Component
@Profile({"externalized","laurel-properties"})
@Primary
public class PropertiesWordProducer implements WordProducer{

	private String word;
	
	//@Value("${propertiesファイルで宣言した変数}") :@TestPropertySource("classpath:resourcesからの相対パス")で指定したpropertiesファイルから変数を取得、引数や変数にインジェクトする 
	@Value("${say.word}")
	public void setWord(String word) {
		this.word = word;
	}
	
	@Override
	public String getWord() {
		return word;
	}

	
}
