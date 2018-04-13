package pso.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pso.model.Address;
import pso.model.Sponsor;
import pso.services.SponsorService;

/**
 * Sponsor controller
 *
 */
@RestController
@RequestMapping("/sponsor")
public class SponsorController {

	@Autowired
	private SponsorService sponsorService;

	// creating a new sponsor
	/**
	 * createSponsor method
	 * @param name
	 * @param desc
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return Sponsor
	 */
	@RequestMapping(method = RequestMethod.POST, value = "")
	public Sponsor createSponsor(@RequestParam(value = "name") String name,
	                             @RequestParam(value = "description", required = false, defaultValue = "N/A") String desc,
	                             @RequestParam(value = "street", required = false, defaultValue = "N/A") String street,
	                             @RequestParam(value = "city", required = false, defaultValue = "N/A") String city,
	                             @RequestParam(value = "state", required = false, defaultValue = "N/A") String state,
	                             @RequestParam(value = "zip", required = false, defaultValue = "N/A") String zip){

		Address address = new Address();
		address.setCity(city); address.setState(state); address.setStreet(street); address.setZip(zip);
		Sponsor created = sponsorService.createSponsor(name, desc, address);
		return created;


	}

	/**
	 * getSponsor method
	 * @param id
	 * @return ResponseEntity<Sponsor> 
	 */
	@RequestMapping(method = RequestMethod.GET, value = "/{id}")
	public ResponseEntity<Sponsor> getSponsor(@PathVariable("id") Long id){
		Sponsor retrieved = sponsorService.getSponsor(id);
		if (retrieved == null)
			return new ResponseEntity<Sponsor>(HttpStatus.NOT_FOUND); 
		else
			
			return new ResponseEntity<Sponsor>(retrieved, HttpStatus.NOT_FOUND); 
	}

	// updating an existing sponsor
	/**
	 * @param id
	 * @param name
	 * @param desc
	 * @param street
	 * @param city
	 * @param state
	 * @param zip
	 * @return Sponsor
	 */
	@RequestMapping(method = RequestMethod.POST, value = "/{id}")
	public ResponseEntity<Sponsor> updateSponsor(@PathVariable("id") Long id,
	                             @RequestParam(value = "name") String name,
	                             @RequestParam(value = "description", required = false) String desc,
	                             @RequestParam(value = "street", required = false) String street,
	                             @RequestParam(value = "city", required = false) String city,
	                             @RequestParam(value = "state", required = false) String state,
	                             @RequestParam(value = "zip", required = false) String zip){

		Address address = new Address();
		address.setCity(city); address.setState(state); address.setStreet(street); address.setZip(zip);
		Sponsor updated = sponsorService.updateSponsor(id, name, desc, address);
		if (updated == null) {
			return new ResponseEntity<Sponsor>(HttpStatus.NOT_FOUND);
		} 
		return new ResponseEntity<Sponsor>(updated, HttpStatus.OK);

	}

	// deleting an existing sponsor
	/**
	 * @param id
	 * @return ResponseEntity<Sponsor>
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "/{id}")
	public ResponseEntity<?> deleteSponsor(@PathVariable("id") Long id){
		try {
			Sponsor deleted = sponsorService.deleteSponsor(id);
			if (deleted == null) {
				return new ResponseEntity<Sponsor>(deleted,HttpStatus.NOT_FOUND); 
			}
			else
				return new ResponseEntity<Sponsor>(deleted,HttpStatus.OK); 
		} catch (DataIntegrityViolationException e ) {
			// TODO: handle exception
			return new ResponseEntity<String>("Cannot perform delete. Player(s) belongs to this sponsor.",HttpStatus.BAD_REQUEST); 
		}	
			
	}
}
