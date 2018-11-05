package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Supermarket;

@Component
@Transactional
public class SupermarketToStringConverter implements Converter<Supermarket, String>{
	
	@Override
	public String convert(Supermarket s) {
		String res;
		if (s == null) {
			res = null;
		} else {
			res = String.valueOf(s.getId());
		}
		return res;
	}

}
