package pso.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.mysql.jdbc.exceptions.MySQLIntegrityConstraintViolationException;

import pso.model.Address;
import pso.model.Player;
import pso.services.PlayerService;

import java.util.List;

/**
 * Player controller
 *
 */
@RestController
@RequestMapping("/player")
public class PlayerController {

	@Autowired
	private PlayerService playerService;

	// creating a new player
	/**
	 * createPlayer method
	 * 
	 * @param fname
	 * @param lname
	 * @param email
	 * @param desc
	 * @param sponsor
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param opponents
	 * @return ResponseEntity<Player>
	 */
	@RequestMapping(method = RequestMethod.POST, value = "")
	public ResponseEntity<Player> createPlayer(@RequestParam(value = "firstname") String fname,
			@RequestParam(value = "lastname") String lname, @RequestParam(value = "email") String email,
			@RequestParam(value = "description", required = false, defaultValue = "N/A") String desc,
			@RequestParam(value = "sponsor", required = false) String sponsor,
			@RequestParam(value = "street", required = false, defaultValue = "N/A") String street,
			@RequestParam(value = "city", required = false, defaultValue = "N/A") String city,
			@RequestParam(value = "state", required = false, defaultValue = "N/A") String state,
			@RequestParam(value = "zip", required = false, defaultValue = "N/A") String zip,
			@RequestParam(value = "opponents", required = false) String opponents) {
		if (opponents != null) {
			return new ResponseEntity<Player>(HttpStatus.BAD_REQUEST);
		}
		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		Player created = playerService.createPlayer(fname, lname, email, desc, address, sponsor);
		if (created == null) {
			return new ResponseEntity<Player>(HttpStatus.BAD_REQUEST);
		} else {
			List<Long> opps = playerService.opponents(created.getId());
			created.setOpponents(opps);
			return new ResponseEntity<Player>(created, HttpStatus.OK);
		}
	}

	// retrieving an existing player
	/**
	 * getPlayer method
	 * 
	 * @param id
	 * @return ResponseEntity<Player>
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Player> getPlayer(@PathVariable("id") Long id) throws JsonProcessingException {
		Player retrieved = playerService.getPlayer(id);
		if (retrieved == null) {
			return new ResponseEntity<Player>(HttpStatus.NOT_FOUND);
		}
		List<Long> opps = playerService.opponents(id);
		retrieved.setOpponents(opps);
		return new ResponseEntity<Player>(retrieved, HttpStatus.OK);

	}

	// updating an existing player
	/**
	 * updatePlayer method
	 * 
	 * @param id
	 * @param fname
	 * @param lname
	 * @param email
	 * @param desc
	 * @param sponsor
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @param opponents
	 * @return ResponseEntity<Player>
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/{id}")
	public ResponseEntity<Player> updatePlayer(@PathVariable("id") Long id,
			@RequestParam(value = "firstname") String fname, @RequestParam(value = "lastname") String lname,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "description", required = false, defaultValue = "N/A") String desc,
			@RequestParam(value = "sponsor", required = false) String sponsor,
			@RequestParam(value = "street", required = false, defaultValue = "N/A") String street,
			@RequestParam(value = "city", required = false, defaultValue = "N/A") String city,
			@RequestParam(value = "state", required = false, defaultValue = "N/A") String state,
			@RequestParam(value = "zip", required = false, defaultValue = "N/A") String zip,
			@RequestParam(value = "opponents", required = false) String opponents) {
		if (opponents != null) {
			return new ResponseEntity<Player>(HttpStatus.BAD_REQUEST);
		}
		Address address = new Address();
		address.setCity(city);
		address.setState(state);
		address.setStreet(street);
		address.setZip(zip);
		try {
			Player updated = playerService.updatePlayer(id, fname, lname, email, desc, address, sponsor);
			if (updated == null) {
				return new ResponseEntity<Player>(HttpStatus.NOT_FOUND);
			}
			if (updated.getSponsor() != null && updated.getSponsor().getId() == -1L) {
				return new ResponseEntity<Player>(HttpStatus.BAD_REQUEST);
			}
			List<Long> opps = playerService.opponents(id);
			updated.setOpponents(opps);
			return new ResponseEntity<Player>(updated, HttpStatus.OK);
		} catch (MySQLIntegrityConstraintViolationException e) {
			return new ResponseEntity<Player>(HttpStatus.BAD_REQUEST);
		}
	}

	// deleting an existing player
	/**
	 * deletePlayer method
	 * 
	 * @param id
	 * @return ResponseEntity<Player>
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<Player> deletePlayer(@PathVariable("id") Long id) {

		Player deleted = playerService.deletePlayer(id);
		if (deleted == null) {
			return new ResponseEntity<Player>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<Player>(deleted, HttpStatus.OK);
	}
}
