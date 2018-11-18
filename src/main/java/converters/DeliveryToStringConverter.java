package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Delivery;

@Component
@Transactional
public class DeliveryToStringConverter implements Converter<Delivery, String> {

	@Override
	public String convert(Delivery delivery) {
		String res;
		if (delivery == null) {
			res = null;
		} else {
			res = String.valueOf(delivery.getId());
		}
		return res;
	}

}