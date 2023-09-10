package com.eei.urlshortener.controller;

import com.eei.urlshortener.data.request.ShortenerRequest;
import com.eei.urlshortener.data.response.ShortenerResponse;
import com.eei.urlshortener.exception.UrlRecordNotFoundException;
import com.eei.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping("/r")
@AllArgsConstructor
public class UrlController {

  private final UrlService urlService;

  @PostMapping
  public ResponseEntity<ShortenerResponse> createShortUrl(
      @RequestBody final ShortenerRequest shortenerRequest, HttpServletRequest request) {
    String baseUrl =
        ServletUriComponentsBuilder.fromRequestUri(request).replacePath(null).build().toUriString();
    return ResponseEntity.status(HttpStatus.OK)
        .body(urlService.createShortUrl(shortenerRequest.getUrl(), baseUrl + "/r/"));
  }

  @GetMapping(value = "/{key}")
  public RedirectView getShortenedUrl(@PathVariable final String key)
      throws UrlRecordNotFoundException {
    String url = urlService.getShortenUrl(key);
    RedirectView redirectView = new RedirectView();
    redirectView.setUrl(url);
    return redirectView;
  }
}
