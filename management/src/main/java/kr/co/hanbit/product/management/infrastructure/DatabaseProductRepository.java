package kr.co.hanbit.product.management.infrastructure;

import kr.co.hanbit.product.management.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;

// ListProductRepository 가 하는 일을 모두 할 수 있어야 한다.
@Repository
public class DatabaseProductRepository {

    // 의존성 주입
    // 데이터베이스에 SQL 을 전송하려면 JdbcTemplate 이라는 의존성을 사용하면 된다.
//    private JdbcTemplate jdbcTemplate;
    /*
    NamedParameterJdbcTemplate
    - SQL 쿼리를 보낼 때 물음표로 매개변수를 매핑하지 않고 매개변수의 이름을 통해 SQL 쿼리와 값을 매핑한다.
    - 매개변수 순서가 바뀌거나 매개변수의 수가 많은 경우에도 헷갈리지 않는다.
     */
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Autowired
    public DatabaseProductRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    public Product add(Product product) {
        // 데이터베이스에 상품 정보 저장
        /* JdbcTemplate 을 통해 데이터베이스로 INSERT SQL 쿼리를 보낸야 한다.
        jdbcTemplate
                .update("INSERT INTO products (name, price, amount) VALUES (?, ?, ?)",
                        product.getName(), product.getPrice(), product.getAmount());
         */

        // 저장된 Product id 받아오기
        KeyHolder keyHolder = new GeneratedKeyHolder();

        // BeanPropertySqlParameterSource 는 Product 의 getter 를 통해 SQL 쿼리의 매개변수를 매핑시켜 주는 객체이다.
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product);

        namedParameterJdbcTemplate
                .update("INSERT INTO products (name, price, amount) VALUES (:name, :price, :amount)", namedParameter, keyHolder);

        // update 메서드에 매개변수로 넘겨준 keyHolder 에 id 가 담겨 온다.
        Long generatedId = keyHolder.getKey().longValue(); // Long 타입의 id 값을 가져온다.
        product.setId(generatedId);

        return product;
    }

    public Product findById(Long id) {
        return null;
    }

    public List<Product> findAll() {
        return Collections.EMPTY_LIST;
    }

    public List<Product> findByNameContaining(String name) {
        return Collections.EMPTY_LIST;
    }

    public Product update(Product product) {
        return null;
    }

    public void delete(Long id) {
        // do nothing
    }
}
