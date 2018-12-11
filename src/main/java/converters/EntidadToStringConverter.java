package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Entidad;

@Component
@Transactional
public class EntidadToStringConverter implements Converter<Entidad, String> {
	@Override
	public String convert(Entidad entidad) {
		String res;
		if (entidad == null) {
			res = null;
		} else {
			res = String.valueOf(entidad.getId());
		}
		return res;
	}

}
