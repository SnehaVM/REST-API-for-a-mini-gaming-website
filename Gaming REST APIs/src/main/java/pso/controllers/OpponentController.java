package pso.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import pso.services.OpponentService;

/**
 * Opponent controller
 *
 */
@RestController
@RequestMapping("/opponents")
public class OpponentController {

	@Autowired
	private OpponentService opponentService;
	/**
	 * createOpponents method
	 * @param id1
	 * @param id2
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "/{id1}/{id2}")
	public ResponseEntity<String> createOpponents(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
		if (id1 == id2) {
			return new ResponseEntity<String>("Invalid player/opponent id", HttpStatus.BAD_REQUEST);
		}
		String result = opponentService.createOpponents(id1, id2);
		if (result == "one or both player(s) not available")
			return new ResponseEntity<String>(result, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<String>(result, HttpStatus.OK);
	}

	/**
	 * deleteOpponents method
	 * @param id1
	 * @param id2
	 * @return ResponseEntity<String>
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id1}/{id2}")
	public ResponseEntity<String> deleteOpponents(@PathVariable("id1") Long id1, @PathVariable("id2") Long id2) {
		String result = opponentService.deleteOpponents(id1, id2);
		if (result == "one or both player(s) not available" || result == "Players are not opponents")
			return new ResponseEntity<String>(result, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<String>(result, HttpStatus.OK);
	}

}
