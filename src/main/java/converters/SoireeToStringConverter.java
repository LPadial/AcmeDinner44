package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Soiree;


@Component
@Transactional
public class SoireeToStringConverter implements Converter<Soiree, String> {

	@Override
	public String convert(Soiree soiree) {
		String res;
		if (soiree == null) {
			res = null;
		} else {
			res = String.valueOf(soiree.getId());
		}
		return res;
	}

}