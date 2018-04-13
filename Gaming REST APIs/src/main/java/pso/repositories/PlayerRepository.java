package pso.repositories;

import org.springframework.data.repository.CrudRepository;
import pso.model.Player;

/**
 * Player Repository class
 *
 */
public interface PlayerRepository extends CrudRepository<Player, Long> {
	 public Player findByEmail(String email);
	
}
