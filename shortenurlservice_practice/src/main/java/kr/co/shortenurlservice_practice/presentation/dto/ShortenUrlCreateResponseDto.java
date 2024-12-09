package kr.co.shortenurlservice_practice.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShortenUrlCreateResponseDto {
    private String originalUrl;
    private String shortenUrlKey;
}
