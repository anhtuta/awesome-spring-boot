package hello.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import hello.entity.Store;

@Repository
public interface StoreRepository extends JpaRepository<Store, Integer> {

}
