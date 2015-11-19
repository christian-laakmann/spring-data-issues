package com.example;

import org.bson.types.ObjectId;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = { DemoApplication.class })
@IntegrationTest("spring.data.mongodb.port=0")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class SlowSerialisationTest {

	private final String SUB_ID = new ObjectId().toString();

	@Autowired
	MongoTemplate template;

	@Test
	public void SaveMasterDocuments() {

		List<String> messages = new ArrayList<>();

		for(int i = 0; i < 10; i++) {

			long withId = timeInserts(EmbeddedWithId::new);
			long withRef = timeInserts(EmbeddedWithRef::new);

			messages.add(String.format("with id:\t%3dms", withId / 1_000_000));
			messages.add(String.format("with ref:\t%3dms", withRef / 1_000_000));

			template.dropCollection(Master.class);
		}

		messages.forEach(System.out::println);
	}

	private long timeInserts(Function<String, Embedded> factory) {
		Master master = createMaster(factory);

		long start = System.nanoTime();
		template.save(master);
		long end = System.nanoTime();

		return end - start;
	}

	private Master createMaster(Function<String, Embedded> factory) {
		Master master = new Master();
		master.setId(null);

		List<Embedded> subs = new ArrayList<>();
		for (int i = 0; i < 10000; i++) {
			Embedded sub = factory.apply(SUB_ID);
			subs.add(sub);
		}

		master.setSub(subs);
		return master;
	}

}
