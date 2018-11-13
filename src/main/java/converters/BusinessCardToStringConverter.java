package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.BusinessCard;

@Component
@Transactional
public class BusinessCardToStringConverter implements Converter<BusinessCard, String> {

	@Override
	public String convert(BusinessCard bussinessCard) {
		String res;
		if (bussinessCard == null) {
			res = null;
		} else {
			res = String.valueOf(bussinessCard.getId());
		}
		return res;
	}

}