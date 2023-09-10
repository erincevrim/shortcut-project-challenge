package com.eei.urlshortener.repository;

import com.eei.urlshortener.entity.ShortUrl;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UrlRepository extends CrudRepository<ShortUrl, Long> {
    Optional<ShortUrl> findUrlByKey(String key);

}

