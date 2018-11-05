package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.SocialSection;

@Component
@Transactional
public class SocialSectionToStringConverter implements Converter<SocialSection, String> {

	@Override
	public String convert(SocialSection socialSection) {
		String res;
		if (socialSection == null) {
			res = null;
		} else {
			res = String.valueOf(socialSection.getId());
		}
		return res;
	}

}