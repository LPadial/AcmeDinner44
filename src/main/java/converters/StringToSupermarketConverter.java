package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SupermarketRepository;

import domain.Supermarket;


@Component
@Transactional
public class StringToSupermarketConverter implements Converter<String, Supermarket>{
	
	@Autowired
	SupermarketRepository supermarketRepository;


	@Override
	public Supermarket convert(String text) {
		Supermarket result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = supermarketRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}


}


