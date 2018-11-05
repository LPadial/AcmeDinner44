package converters;

import javax.transaction.Transactional;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import repositories.DinerRepository;

import domain.Diner;

@Component
@Transactional
public class StringToDinerConverter implements Converter<String, Diner> {

	@Autowired
	DinerRepository dinerRepository;

	@Override
	public Diner convert(String text) {
		Diner result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = dinerRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}
