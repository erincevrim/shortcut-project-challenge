package com.eei.urlshortener.service;

import com.eei.urlshortener.data.response.ShortenerResponse;
import com.eei.urlshortener.entity.ShortUrl;
import com.eei.urlshortener.exception.UrlRecordNotFoundException;
import com.eei.urlshortener.repository.UrlRepository;
import com.google.common.hash.Hashing;
import java.nio.charset.Charset;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
@Slf4j
public class UrlService {

  private final UrlRepository urlRepository;

  @Transactional
  public ShortenerResponse createShortUrl(String url, String baseUrl) {
    log.info("Short URl create operation started.");
    String key = Hashing.murmur3_32().hashString(url, Charset.defaultCharset()).toString();
    urlRepository
        .findUrlByKey(key)
        .ifPresentOrElse(
            shortUrl -> log.info("Short Url already found in DB"),
            () -> urlRepository.save(ShortUrl.builder().url(url).key(key).build()));
    log.info("Short Url create operation  finished.");
    return ShortenerResponse.builder().shortenUrl(baseUrl + key).build();
  }

  public String getShortenUrl(String key) throws UrlRecordNotFoundException {
    log.info("Ull retrieve operation started.");
    ShortUrl shortUrl =
        urlRepository.findUrlByKey(key).orElseThrow(() -> new UrlRecordNotFoundException(key));
    log.info("Ull retrieve operation finished.");
    return shortUrl.getUrl();
  }
}
