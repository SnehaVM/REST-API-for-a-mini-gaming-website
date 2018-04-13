package pso.model;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Entity class for opponents
 *
 */
@Entity
@Table(name="opponents")
public class Opponents {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Long id;

	@Column(name="player_Id")
	private Long playerId;

	@Column(name="opponent_Id")
	private Long opponentId;

	//get id
	public Long getId() {
		return id;
	}
	//set id
	public void setId(Long id) {
		this.id = id;
	}
	//get player
	public Long getplayerId() {
		return playerId;
	}
	//set player
	public void setplayerId(Long playerId) {
		this.playerId = playerId;
	}
	//get opponent
	public Long getOpponentId() {
		return opponentId;
	}
	//set opponent
	public void setOpponent(Long opponentId) {
		this.opponentId = opponentId;
	}
}
