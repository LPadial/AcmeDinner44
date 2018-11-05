package converters;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import repositories.SocialSectionRepository;

import domain.SocialSection;


@Component
@Transactional
public class StringToSocialSectionConverter implements Converter<String, SocialSection> {

	@Autowired
	SocialSectionRepository socialSectionRepository;


	@Override
	public SocialSection convert(String text) {
		SocialSection result;
		int id;
		try {
			if (StringUtils.isEmpty(text)) {
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = socialSectionRepository.findOne(id);
			}
		} catch (Exception oops) {
			throw new IllegalArgumentException(oops);
		}
		return result;
	}

}