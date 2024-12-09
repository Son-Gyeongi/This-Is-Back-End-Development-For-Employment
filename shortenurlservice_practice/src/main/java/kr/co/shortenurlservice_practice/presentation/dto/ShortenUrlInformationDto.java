package kr.co.shortenurlservice_practice.presentation.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ShortenUrlInformationDto {
    private String originalUrl;
    private String shortenUrlKey;
    private Long redirectCount;
}
