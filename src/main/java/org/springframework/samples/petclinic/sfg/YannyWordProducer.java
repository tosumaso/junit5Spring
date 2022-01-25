package org.springframework.samples.petclinic.sfg;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

//同じ型のbeanを複数登録するとき、@Primaryがついたクラス、メソッドを優先して登録する
//@Profile(name)で
@Profile("yanny")
@Primary
@Component
public class YannyWordProducer implements WordProducer{

	@Override
	public String getWord() {
		return "Yanny";
	}

}
