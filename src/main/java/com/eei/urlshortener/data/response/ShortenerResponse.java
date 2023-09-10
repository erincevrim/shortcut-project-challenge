package com.eei.urlshortener.data.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShortenerResponse {
    private String shortenUrl;
}
