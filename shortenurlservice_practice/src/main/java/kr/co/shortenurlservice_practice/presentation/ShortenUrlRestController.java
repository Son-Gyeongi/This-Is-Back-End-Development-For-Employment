package kr.co.shortenurlservice_practice.presentation;

import jakarta.validation.Valid;
import kr.co.shortenurlservice_practice.presentation.dto.ShortenUrlCreateRequestDto;
import kr.co.shortenurlservice_practice.presentation.dto.ShortenUrlCreateResponseDto;
import kr.co.shortenurlservice_practice.presentation.dto.ShortenUrlInformationDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShortenUrlRestController {

    // 1. 단축 URL 생성 API - /shortenUrl, POST
    @PostMapping(value = "/shortenUrl")
    public ResponseEntity<ShortenUrlCreateResponseDto> createShortenUrl(
            @Valid @RequestBody ShortenUrlCreateRequestDto shortenUrlCreateRequestDto
    ) {
        return ResponseEntity.ok().body(null);
    }

    // 2. 단축 URL 리다이렉트 API - /{shortenUrlKey}, GET
    @GetMapping(value = "/{shortenUrlKey}")
    public ResponseEntity<?> redirectShortenUrl(
            @PathVariable String shortenUrlKey
    ) {
        return ResponseEntity.ok().body(null);
    }

    // 3. 단축 URL 정보 조회 API - /shortenUrl/{shortenUrlKey}, GET
    @GetMapping(value = "/shortenUrl/{shortenUrlKey}")
    public ResponseEntity<ShortenUrlInformationDto> getShortenUrlInformation(
            @PathVariable String shortenUrlKey
    ) {
        return ResponseEntity.ok().body(null);
    }
}
