package com.example;

import org.bson.types.ObjectId;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.core.convert.CustomConversions;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { DemoApplication.class, LoadLocaleWithCustomConverter.AddCustomConverterConfiguration.class })
@IntegrationTest("spring.data.mongodb.port=0")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class LoadLocaleWithCustomConverter {

	@Autowired
	LocaleRepository languageRepository;

	@Before
	public void setup() {
		Language l1 = new Language("deDE", "de-DE");
		Language l2 = new Language("enEN", "en-EN");

		languageRepository.save(l1);
		languageRepository.save(l2);
	}

	@Test
	public void LoadsTheLanguageUsingItsLocaleString() {
		Language language = languageRepository.findOneByLocale("de-DE");

		Assert.assertNotNull(language);
		Assert.assertEquals("deDE", language.getId());
	}

	@Configuration
	public static class AddCustomConverterConfiguration {

		@Bean
		public CustomConversions customConversions() {

			List<Object> converters = new ArrayList<>();
			converters.add(ObjectIdToStringConverter.INSTANCE);
			converters.add(StringToObjectIdConverter.INSTANCE);

			return new CustomConversions(converters);
		}

	}

	/**
	 * Simple singleton to convert {@link ObjectId}s to their {@link String} representation.
	 *
	 * @author Oliver Gierke
	 */
	public static enum ObjectIdToStringConverter implements Converter<ObjectId, String> {
		INSTANCE;

		public String convert(ObjectId id) {
			return id == null ? null : id.toString();
		}
	}

	/**
	 * Simple singleton to convert {@link String}s to their {@link ObjectId} representation.
	 *
	 * @author Oliver Gierke
	 */
	public static enum StringToObjectIdConverter implements Converter<String, ObjectId> {
		INSTANCE;

		public ObjectId convert(String source) {
			return StringUtils.hasText(source) ? new ObjectId(source) : null;
		}
	}

}
