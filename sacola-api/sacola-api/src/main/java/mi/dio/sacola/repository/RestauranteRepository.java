package mi.dio.sacola.repository;

import mi.dio.sacola.model.Restaurante;
import org.springframework.stereotype.Repository;

@Repository
public interface RestauranteRepository extends JpaRepository<Restaurante,Long> {
}
