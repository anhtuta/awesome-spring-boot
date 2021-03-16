package hello.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import com.querydsl.core.types.Predicate;
import hello.entity.Staff;

@Repository
public interface StaffRepository
        extends JpaRepository<Staff, Integer>, QuerydslPredicateExecutor<Staff> {

    Page<Staff> findAll(Predicate predicate, Pageable pageable);

}
