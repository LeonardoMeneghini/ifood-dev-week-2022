package mi.dio.sacola.repository;

import mi.dio.sacola.model.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository <Item,Long > {
}