package pso.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import pso.model.Address;
import pso.model.Opponents;
import pso.model.Player;
import pso.model.Sponsor;
import pso.repositories.OpponentRepository;
import pso.repositories.PlayerRepository;
import pso.repositories.SponsorRepository;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
 * Player service method
 *
 */
@Service
@Transactional
public class PlayerService {

	@Autowired
	private PlayerRepository playerRepository;
	@Autowired
	private OpponentRepository opponentRepository;
	@Autowired
	private SponsorRepository sponsorRepository;
	@Autowired
	SponsorService sponsorService;

	/**
	 * get player
	 * @param id
	 * @return Player
	 */
	public Player getPlayer(Long id){
		List<Long> opp = new ArrayList<>();
		Player player = playerRepository.findOne(id);
		return player;
	}

	/**
	 * create a new player
	 * @param fname
	 * @param lname
	 * @param email
	 * @param desc
	 * @param address
	 * @param sponsor
	 * @return Player
	 */
	public Player createPlayer(String fname, String lname, String email, String desc, Address address, String sponsor){
		//TODO returning empty opponents list while player creation
		boolean s_exists;
		Player recexist = playerRepository.findByEmail(email);
		if (recexist != null) {
			return null;
		}
		Player created = new Player();
		created.setFirstname(fname);
		created.setLastname(lname);
		created.setEmail(email);
		created.setDescription(desc);
		created.setAddress(address);
		if(sponsor!=null)
		
		{
			Long sponsorId = Long.parseLong(sponsor);
			s_exists = sponsorRepository.exists(sponsorId);
				if (s_exists == true){
					Sponsor sponsor1 = sponsorService.getSponsor(sponsorId);
					created.setSponsor(sponsor1);
				}

				else {
						created = null;
						return created;
				}
		}
	
		playerRepository.save(created);
		return created;
	}

	/**
	 * update a player
	 * @param id
	 * @param fname
	 * @param lname
	 * @param email
	 * @param desc
	 * @param address
	 * @param sponsor
	 * @return Player
	 */
	public Player updatePlayer(Long id, String fname, String lname, String email, String desc, Address address, String sponsor) throws MySQLIntegrityConstraintViolationException{
		boolean s_exists;
		Player updated = playerRepository.findOne(id);
		if (updated == null) {
			return updated;
		}
		Player recexist = playerRepository.findByEmail(email);
		if (recexist != null && recexist.getId() != id) {
			throw new MySQLIntegrityConstraintViolationException("duplicate email");
		}	
		updated.setFirstname(fname);
		updated.setLastname(lname);
		updated.setEmail(email);
		updated.setDescription(desc);
		updated.setAddress(address);
		if(sponsor != null) {
			Long sponsorId = Long.parseLong(sponsor);
			s_exists = sponsorRepository.exists(sponsorId);

			if (s_exists == true){
				Sponsor sponsor1 = sponsorService.getSponsor(sponsorId);
				updated.setSponsor(sponsor1);
			}

			else {
					Sponsor invalid = new Sponsor(-1L, "invalid", "invalid", new Address());
					invalid.setId(-1L);
					Player dummy = new Player();
					dummy.setSponsor(invalid);
					return dummy;
			}
		}
		else
		{
			updated.setSponsor(null);
		}
		playerRepository.save(updated);
		return updated;
	}

	/**
	 * delete a player
	 * @param id
	 * @return Player
	 */
	public Player deletePlayer(Long id){
		Player deleted = playerRepository.findOne(id);
		if (deleted == null) {
			return deleted;
		}
		List<Long> opps = opponents(id);
		if(opps.size() > 0)
			deleted.setOpponents(opps);
		playerRepository.delete(id);
		return deleted;
	}

	/**
	 * get opponent list
	 * @param id
	 * @return  List<Long>
	 */
	public List<Long> opponents(Long id){
		List<Opponents> opponentsList = opponentRepository.findByplayerId(id);
		List<Long> oids = new ArrayList<>();
		if (opponentsList != null){
			for (Opponents o : opponentsList) {
				Long opponentId = o.getOpponentId();
				oids.add(opponentId);
			}
			return oids;
		}
		else return new ArrayList<>();

	}
}
