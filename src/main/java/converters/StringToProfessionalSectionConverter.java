package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.ProfessionalSectionRepository;

import domain.ProfessionalSection;


@Component
@Transactional
public class StringToProfessionalSectionConverter implements Converter<String, ProfessionalSection> {

	@Autowired
	ProfessionalSectionRepository professionalSectionRepository;


	@Override
	public ProfessionalSection convert(String text) {
		ProfessionalSection result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = professionalSectionRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}