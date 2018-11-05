package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Diner;


@Component
@Transactional
public class DinerToStringConverter implements Converter<Diner, String> {

	@Override
	public String convert(Diner diner) {
		String res;
		if (diner == null) {
			res = null;
		} else {
			res = String.valueOf(diner.getId());
		}
		return res;
	}

}