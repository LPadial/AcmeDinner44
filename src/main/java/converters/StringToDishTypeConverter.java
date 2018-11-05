package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.DishTypeRepository;

import domain.DishType;


@Component
@Transactional
public class StringToDishTypeConverter implements Converter<String, DishType> {

	@Autowired
	DishTypeRepository dishTypeRepository;


	@Override
	public DishType convert(String text) {
		DishType result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = dishTypeRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
