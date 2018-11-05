package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.DishType;

@Component
@Transactional
public class DishTypeToStringConverter implements Converter<DishType, String> {

	@Override
	public String convert(DishType dishType) {
		String res;
		if (dishType == null) {
			res = null;
		} else {
			res = String.valueOf(dishType.getId());
		}
		return res;
	}

}