package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Dish;


@Component
@Transactional
public class DishToStringConverter implements Converter<Dish, String> {

	@Override
	public String convert(Dish dish) {
		String res;
		if (dish == null) {
			res = null;
		} else {
			res = String.valueOf(dish.getId());
		}
		return res;
	}

}
