package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Brand;


@Component
@Transactional
public class StringToBrandConverter implements Converter<String, Brand> {

	@Override
	public Brand convert(String text) {
		Brand result;

		try {
			result = new Brand();
			result.setValue(text);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}