package pso.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pso.model.Opponents;
import pso.repositories.OpponentRepository;
import pso.repositories.PlayerRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Opponent service class
 *
 */
@Service
@Transactional
public class OpponentService {

	@Autowired
	private OpponentRepository opponentRepository;
	@Autowired
	private PlayerRepository playerRepository;

	/**
	 * create new opponents
	 * 
	 * @param id1
	 * @param id2
	 * @return String
	 */
	public String createOpponents(Long id1, Long id2) {
		if (playerRepository.exists(id1) && playerRepository.exists(id2)) {

			List<Opponents> p1list = opponentRepository.findByplayerId(id1);
			for (Opponents o : p1list) {
				Long opponentId = o.getOpponentId();
				if (opponentId == id2)
					return "relationship exists";
			}
			Opponents relation1 = new Opponents();
			relation1.setplayerId(id1);
			relation1.setOpponent(id2);
			opponentRepository.save(relation1);
			Opponents relation2 = new Opponents();
			relation2.setplayerId(id2);
			relation2.setOpponent(id1);
			opponentRepository.save(relation2);
			return "new relationship established";

		} else
			return "one or both player(s) not available";
	}

	/**
	 * delete existing opponents
	 * 
	 * @param id1
	 * @param id2
	 * @return String
	 */
	public String deleteOpponents(Long id1, Long id2) {
		if (playerRepository.exists(id1) && playerRepository.exists(id2)) {
			List<Opponents> p1list = opponentRepository.findByplayerId(id1);
			List<Opponents> p2list = opponentRepository.findByplayerId(id2);
			if (p1list.size() == 0 && p2list.size() == 0) {
				return "Players are not opponents";
			} else {
				for (Opponents o : p1list) {
					Long opponentId = o.getOpponentId();
					if (opponentId == id2) {
						opponentRepository.delete(o.getId());
					}
				}

				for (Opponents o : p2list) {
					Long opponentId = o.getOpponentId();
					if (opponentId == id1) {
						opponentRepository.delete(o.getId());
					}
				}
				return "opponents removed";
			}
		} else
			return "one or both player(s) not available";
	}

}
