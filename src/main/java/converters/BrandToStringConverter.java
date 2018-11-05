package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Brand;

@Component
@Transactional
public class BrandToStringConverter implements Converter<Brand, String> {

	@Override
	public String convert(Brand brand) {
		String result;
		if (brand == null)
			result = null;
		else
			result = brand.getValue().toString();

		return result;
	}

}