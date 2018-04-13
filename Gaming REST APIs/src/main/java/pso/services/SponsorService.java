package pso.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pso.model.Address;
import pso.model.Sponsor;
import pso.repositories.SponsorRepository;
import javax.transaction.Transactional;

/**
 *Sponsor service
 *
 */
@Service
@Transactional
public class SponsorService {
	@Autowired
	private SponsorRepository SponsorRepository;

	/**
	 * get sponsor
	 * @param id
	 * @return Sponsor
	 */
	public Sponsor getSponsor(Long id) {
		if (SponsorRepository.exists(id))
			return SponsorRepository.findOne(id);
		else
			return null;
	}

	/**
	 * create a sponsor
	 * @param name
	 * @param desc
	 * @param address
	 * @return Sponsor
	 */
	public Sponsor createSponsor(String name, String desc, Address address) {
		Sponsor created = new Sponsor();
		created.setName(name);
		created.setDescription(desc);
		created.setAddress(address);
		SponsorRepository.save(created);
		return created;

	}

	/**
	 * update sponsor
	 * @param id
	 * @param name
	 * @param desc
	 * @param address
	 * @return Sponsor
	 */
	public Sponsor updateSponsor(Long id, String name, String desc, Address address) {
		Sponsor updated = SponsorRepository.findOne(id);
		if (updated == null) {
			return updated;
		}
		updated.setName(name);
		updated.setDescription(desc);
		updated.setAddress(address);
		SponsorRepository.save(updated);
		return updated;
	}

	/**
	 * delete a sponsor
	 * @param id
	 * @return Sponsor
	 */
	public Sponsor deleteSponsor(Long id) {
		Sponsor deleted = SponsorRepository.findOne(id);
		if (deleted == null) {
			return deleted;
		}
		SponsorRepository.delete(deleted);
		return deleted;
		
	}
}
