package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.ShoppingCart;

@Component
@Transactional
public class ShoppingCartToStringConverter implements Converter<ShoppingCart, String> {

	@Override
	public String convert(ShoppingCart shoppingCart) {
		String res;
		if (shoppingCart == null) {
			res = null;
		} else {
			res = String.valueOf(shoppingCart.getId());
		}
		return res;
	}

}