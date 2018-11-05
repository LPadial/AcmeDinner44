package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.PersonalSection;


@Component
@Transactional
public class PersonalSectionToStringConverter implements Converter<PersonalSection, String> {

	@Override
	public String convert(PersonalSection personalSection) {
		String res;
		if (personalSection == null) {
			res = null;
		} else {
			res = String.valueOf(personalSection.getId());
		}
		return res;
	}

}