package hello.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import hello.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(nativeQuery = true,
            value = "SELECT 1 FROM dual WHERE EXISTS (SELECT id FROM category WHERE id IN :categoryIds)")
    Integer checkExist(@Param("categoryIds") List<Integer> categoryIds);
}
