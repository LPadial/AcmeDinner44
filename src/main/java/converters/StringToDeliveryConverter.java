package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.DeliveryRepository;
import domain.Delivery;

@Component
@Transactional
public class StringToDeliveryConverter implements Converter<String, Delivery> {

	@Autowired
	DeliveryRepository deliveryRepository;


	@Override
	public Delivery convert(String text) {
		Delivery result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = deliveryRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
