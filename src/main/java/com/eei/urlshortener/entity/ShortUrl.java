package com.eei.urlshortener.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "url", indexes = {
        @Index(name = "index_url_key", columnList = "url_key")
})
public class ShortUrl {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "url_key", length = 32, nullable = false, unique = true)
    private String key;

    @Column(length = 2083, nullable = false)
    private String url;
}