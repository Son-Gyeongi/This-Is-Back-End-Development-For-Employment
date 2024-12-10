package kr.co.shortenurlservice_practice.infrastructure;

import kr.co.shortenurlservice_practice.domain.ShortenUrl;
import kr.co.shortenurlservice_practice.domain.ShortenUrlRepository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class MapShortenUrlRepository implements ShortenUrlRepository {

    private Map<String, ShortenUrl> shortenUrls = new ConcurrentHashMap<>();

    @Override
    public void saveShortenUrl(ShortenUrl shortenUrl) {
        shortenUrls.put(shortenUrl.getShortenUrlKey(), shortenUrl);
    }
}
