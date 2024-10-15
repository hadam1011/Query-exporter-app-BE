package net.javaguides.springboot.controller;


import net.javaguides.springboot.dto.MetricYaml;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.mapper.MetricMapper;
import net.javaguides.springboot.model.Metric;
import net.javaguides.springboot.model.UserEntity;
import net.javaguides.springboot.repository.MetricRepository;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class MetricController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JWTGenerator tokenGenerator;
	@Autowired
	private MetricRepository metricRepository;
	
	// get all metrics
	@GetMapping("/admin/metrics")
	public List<Metric> getAllMetrics(){
		return metricRepository.findAll();
	}

	@GetMapping("/metrics")
	public List<Metric> getAllMetricByUser(@RequestHeader("Authorization") String authorizationHeader){
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			authorizationHeader = authorizationHeader.substring(7); // Skip "Bearer " prefix
		}
		String username = tokenGenerator.getUsernameFromJWT(authorizationHeader);
		List<Metric> res = metricRepository.findByUsernameFromJoinedTables(username);
		return res;
	}

	// create metric rest api
	@PostMapping("/metrics")
	public Metric createMetric(@RequestHeader("Authorization") String authorizationHeader, @RequestBody Metric metric) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			authorizationHeader = authorizationHeader.substring(7); // Skip "Bearer " prefix
		}
		String username = tokenGenerator.getUsernameFromJWT(authorizationHeader);

		List<Metric> metrics = metricRepository.findByUsernameFromJoinedTables(username);
		for (Metric data : metrics) {
			if (data.getName().equals(metric.getName()))
				throw new IllegalArgumentException("Metric already exists.");
		}

		UserEntity user = userRepository.findByUsername(username).get();
		metric.setUser(user);
		System.out.println("User role: " + user.getRoles().get(0).getName());
		Metric savedMetric = metricRepository.save(metric);
		return savedMetric;
	}
	
	// get metric by id rest api
	@GetMapping("/metrics/{id}")
	public ResponseEntity<Metric> getMetricById(@PathVariable Long id) {
		Metric metric = metricRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Metric not exist with id :" + id));
		return ResponseEntity.ok(metric);
	}
	
	// update metric rest api
	
	@PutMapping("/metrics/{id}")
	public ResponseEntity<Metric> updateMetric(@PathVariable Long id, @RequestBody Metric metricDetails){
		Metric metric = metricRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Metric not exist with id :" + id));

		if (metricRepository.existsByName(metricDetails.getName()) && !metricDetails.getName().equals(metric.getName()))
			throw new IllegalArgumentException("Metric already exists.");

		metric.setName(metricDetails.getName());
		metric.setType(metricDetails.getType());
		metric.setDescription(metricDetails.getDescription());
		metric.setBuckets(metricDetails.getBuckets());
		metric.setExpiration(metricDetails.getExpiration());
		metric.setIncrement(metricDetails.getIncrement());
		metric.setLabels(metricDetails.getLabels());
		metric.setStates(metricDetails.getStates());
		Metric updatedMetric = metricRepository.save(metric);
		return ResponseEntity.ok(updatedMetric);
	}
	
	// delete metric rest api
	@DeleteMapping("/metrics/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteMetric(@PathVariable Long id){
		Metric metric = metricRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Metric not exist with id :" + id));
		
		metricRepository.delete(metric);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/metrics/yaml")
	public Map<String, MetricYaml> getMetricYaml() {
		List<Metric> allMetrics = metricRepository.findAll();
		MetricMapper mapper = new MetricMapper(metricRepository);
		return mapper.toYamlMap(allMetrics);
	}
	
}
