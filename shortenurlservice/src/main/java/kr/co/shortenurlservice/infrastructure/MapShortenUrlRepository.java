package kr.co.shortenurlservice.infrastructure;

import kr.co.shortenurlservice.domain.ShortenUrl;
import kr.co.shortenurlservice.domain.ShortenUrlRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 과제 테스트 - 7. 레포지토리 코드 추가
 *
 * 요구사항
 * - 데이터베이스 없이 컬렉션을 활용하여 데이터를 저장해야 한다.
 */
@Repository
public class MapShortenUrlRepository implements ShortenUrlRepository {

    /*
    단축 URL 키를 통해 단축 URL 을 찾아내기 떄문에 Map 이 적절하다.
     */
    private Map<String, ShortenUrl> shortenUrls = new ConcurrentHashMap<>();

    /*
    단축 URL 생성 기능 추가 - 생성된 단축 URL 저장 로직
     */
    @Override
    public void saveShortenUrl(ShortenUrl shortenUrl) {
        shortenUrls.put(shortenUrl.getShortenUrlKey(), shortenUrl);
    }

    /*
    단축 URL 정보 조회 기능 추가
     */
    @Override
    public ShortenUrl findShortenUrlByShortenUrlKey(String shortenUrlKey) {
        ShortenUrl shortenUrl = shortenUrls.get(shortenUrlKey);
        return shortenUrl;
    }
}
