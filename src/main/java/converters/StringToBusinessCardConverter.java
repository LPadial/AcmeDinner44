package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.BusinessCardRepository;

import domain.BusinessCard;

@Component
@Transactional
public class StringToBusinessCardConverter implements Converter<String, BusinessCard> {

	@Autowired
	BusinessCardRepository bussinessCardRepository;


	@Override
	public BusinessCard convert(String text) {
		BusinessCard result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = bussinessCardRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}