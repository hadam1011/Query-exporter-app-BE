package net.javaguides.springboot.model;


import javax.persistence.*;

@Entity
@Table(name = "queries")
public class Queries {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "timeout")
	private int timeout;
	@Column(name = "parameters")
	private String parameters;
	@Column(name = "schedule")
	private String schedule;
	@Column(name = "interval_time")
	private int interval;

	@Column(name = "command")
	private String sql;

	@Column(name = "database_name")
	private String databases;

	@Column(name = "metrics")
	private String metrics;

	@ManyToOne
	@JoinColumn(name = "user_id") // This specifies the foreign key column in the Post table
	private UserEntity user;

	public Queries() {

	}

	public Queries(long id, String name, int timeout, int interval, String sql, String databases, String metrics) {
		this.id = id;
		this.name = name;
		this.timeout = timeout;
		this.interval = interval;
		this.sql = sql;
		this.databases = databases;
		this.metrics = metrics;
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

	public int getTimeout() {
		return timeout;
	}

	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}

	public String getParameters() {
		return parameters;
	}

	public void setParameters(String parameters) {
		this.parameters = parameters;
	}

	public String getSchedule() {
		return schedule;
	}

	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}

	public int getInterval() {
		return interval;
	}

	public void setInterval(int interval) {
		this.interval = interval;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public String getDatabases() {
		return databases;
	}

	public void setDatabases(String databases) {
		this.databases = databases;
	}

	public String getMetrics() {
		return metrics;
	}

	public void setMetrics(String metrics) {
		this.metrics = metrics;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}
}
