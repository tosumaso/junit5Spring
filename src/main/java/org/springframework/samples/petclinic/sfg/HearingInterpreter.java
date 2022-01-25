package org.springframework.samples.petclinic.sfg;

import org.springframework.stereotype.Service;

@Service
public class HearingInterpreter {

	// WordProducerを実装したクラスが複数ある。@Primaryの実装クラスが優先してDIされる
	private final WordProducer wordProducer;

	//コンストラクタインジェクションでは@Autowiredを省略できる 
	public HearingInterpreter(WordProducer wordProducer) {
		this.wordProducer = wordProducer;
	}
	
	public String whatIheard() {
		String word = wordProducer.getWord();
		System.out.println(word);
		return word;
	}
}
