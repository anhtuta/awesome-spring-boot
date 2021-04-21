package hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import hello.entity.Category;

@Repository
public interface CategoryRepository
        extends JpaRepository<Category, Integer>, QuerydslPredicateExecutor<Category> {

}
