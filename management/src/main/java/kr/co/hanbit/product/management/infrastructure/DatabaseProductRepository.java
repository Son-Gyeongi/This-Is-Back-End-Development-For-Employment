package kr.co.hanbit.product.management.infrastructure;

import kr.co.hanbit.product.management.domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

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
                .update("INSERT INTO products (name, price, amount) VALUES (:name, :price, :amount)",
                        namedParameter,
                        keyHolder);

        // update 메서드에 매개변수로 넘겨준 keyHolder 에 id 가 담겨 온다.
        Long generatedId = keyHolder.getKey().longValue(); // Long 타입의 id 값을 가져온다.
        product.setId(generatedId);

        return product;
    }

    public Product findById(Long id) {
        /*
        SqlParameterSource 로 id 를 매핑시켜 줘야 하므로 MapSqlParameterSource 를 사용했다.

        MapSqlParameterSource 는 BeanPropertySqlParameterSource 와 다르게 Product 같은 객체를 매핑하는 것이 아니라
        Map 형태로 Key-Value 형태를 매핑할 수 있다. id 만 매핑해 주면 되므로 MapSqlParameterSource 를 사용하는 것이 적절하다.
         */
        SqlParameterSource namedParameter = new MapSqlParameterSource("id", id);

        /*
        아래와 같은 간단한 코드만으로 데이터베이스에 저장된 데이터를 가져와서 자바의 인스턴스를 만들 수 있다.

        queryForObject
        상품 추가에 사용했던 update 와 비슷하게 첫 번째 인자로 SQL 쿼리를, 두 번째 인자로 namedParameter 를 받는다.
        그리고 세 번째 인자로는 조회된 상품 정보를 Product 인스턴스로 변환해 주는 BeanPropertyRowMapper 가 들어간다.

        BeanPropertyRowMapper 가 정상적으로 작동하려면 해야하는 일이 있다.
        BeanPropertyRowMapper 가 데이터베이스에서 조회된 상품 정보를 Product 인스턴스로 만들기 위해서는 두 가지 과정을 거친다.
        1. Product 의 인자가 없는 생성자로 Product 인스턴스를 생성한다. -> 인자가 없는 생성자가 반드시 필요하다.
        2. 생성된 Product 인스턴스의 setter 로 필드를 초기화한다. -> setter 가 반드시 필요하다.
         */
        // 하나의 Product 를 조회 시 queryForObject 사용
        Product product = namedParameterJdbcTemplate.queryForObject(
                "SELECT id, name, price, amount FROM products WHERE id = :id",
                namedParameter,
                new BeanPropertyRowMapper<>(Product.class)
        ); // BeanPropertyRowMapper 는 데이터베이스에서 조회된 데이터를 Product.class 로 변환

        return product;
    }

    public List<Product> findAll() {
        // List 조회 시 query 사용
        // 전체 목록을 조회할 경우 매개변수 필요없음 - MapSqlParameterSource 를 생성하지 않고, namedParameter 도 인자로 넣어 주지 않음
        List<Product> products = namedParameterJdbcTemplate.query(
                "SELECT * FROM products",
                new BeanPropertyRowMapper<>(Product.class)
        );

        return products;
    }

    public List<Product> findByNameContaining(String name) {
        // MapSqlParameterSource 로 검색하려는 name 매핑
        SqlParameterSource namedParameter = new MapSqlParameterSource("name", "%" + name + "%");

        // SELECT * FROM products WHERE name LIKE '%검색어%'; -> %검색어% 는 앞, 뒤 문자열 상관없이 검색어가 포함된 문자열을 검색
        List<Product> products = namedParameterJdbcTemplate.query(
                "SELECT * FROM products WHERE name LIKE :name",
                namedParameter,
                new BeanPropertyRowMapper<>(Product.class)
        );

        return products;
    }

    /*
    id 로 상품을 찾고 수정하는 기능 추가하기
    - 상품 번호(id)를 기준으로 수정할 상품을 찾고, 상품 번호를 제외한 나머지 정보를 수정하는 것을 말한다.
     */
    public Product update(Product product) {
        SqlParameterSource namedParameter = new BeanPropertySqlParameterSource(product);

        namedParameterJdbcTemplate.update("UPDATE products SET name = :name, price = :price, amount = :amount WHERE id = :id",
                namedParameter);

        return product;
    }

    public void delete(Long id) {
        // do nothing
    }
}
