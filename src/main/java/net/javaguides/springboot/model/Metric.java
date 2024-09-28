package net.javaguides.springboot.model;

import javax.persistence.*;

@Entity
@Table(name = "metrics")
public class Metric {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "type")
	private String type;

	@Column(name = "description")
	private String description;

	@Column(name = "labels")
	private String labels;
	@Column(name = "buckets")
	private String buckets;

	@Column(name = "states")
	private String states;

	@Column(name = "expiration")
	private String expiration;
	@Column(name = "increment")
	private String increment;

	@ManyToOne
	@JoinColumn(name = "user_id") // This specifies the foreign key column in the Post table
	private UserEntity user;

	public Metric() {

	}

	public Metric(long id, String name, String type, String description, String labels, String buckets, String states, String expiration, String increment) {
		this.id = id;
		this.name = name;
		this.type = type;
		this.description = description;
		this.labels = labels;
		this.buckets = buckets;
		this.states = states;
		this.expiration = expiration;
		this.increment = increment;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public String getBuckets() {
		return buckets;
	}

	public void setBuckets(String buckets) {
		this.buckets = buckets;
	}

	public String getStates() {
		return states;
	}

	public void setStates(String states) {
		this.states = states;
	}

	public String getExpiration() {
		return expiration;
	}

	public void setExpiration(String expiration) {
		this.expiration = expiration;
	}

	public String getIncrement() {
		return increment;
	}

	public void setIncrement(String increment) {
		this.increment = increment;
	}
}
