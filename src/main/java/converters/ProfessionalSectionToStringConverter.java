package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ProfessionalSection;


@Component
@Transactional
public class ProfessionalSectionToStringConverter implements Converter<ProfessionalSection, String> {

	@Override
	public String convert(ProfessionalSection professionalSection) {
		String res;
		if (professionalSection == null) {
			res = null;
		} else {
			res = String.valueOf(professionalSection.getId());
		}
		return res;
	}

}