package pso.repositories;

import org.springframework.data.repository.CrudRepository;
import pso.model.Opponents;

import java.util.List;

/**
 * Opponent Repository class
 *
 */
public interface OpponentRepository extends CrudRepository<Opponents, Long> {

	List<Opponents> findByplayerId(Long playerId);
}
