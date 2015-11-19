package com.example;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { DemoApplication.class })
@IntegrationTest("spring.data.mongodb.port=0")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class LoadLocaleWithoutCustomConverter {

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


}
