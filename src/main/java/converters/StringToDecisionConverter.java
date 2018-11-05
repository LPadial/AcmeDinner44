package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Decision;

@Component
@Transactional
public class StringToDecisionConverter implements Converter<String, Decision> {

	@Override
	public Decision convert(String text) {
		Decision result;

		try {
			result = new Decision();
			result.setValue(text);
		} catch (Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}

}