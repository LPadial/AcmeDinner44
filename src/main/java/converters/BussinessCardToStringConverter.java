package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.BussinessCard;

@Component
@Transactional
public class BussinessCardToStringConverter implements Converter<BussinessCard, String> {

	@Override
	public String convert(BussinessCard bussinessCard) {
		String res;
		if (bussinessCard == null) {
			res = null;
		} else {
			res = String.valueOf(bussinessCard.getId());
		}
		return res;
	}

}