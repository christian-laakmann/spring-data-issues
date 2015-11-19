package com.example;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author novomind AG
 */
public interface LocaleRepository extends MongoRepository<Language, String> {

  Language findOneByLocale(String locale);

}
