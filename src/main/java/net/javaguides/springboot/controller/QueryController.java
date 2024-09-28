package net.javaguides.springboot.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.dataformat.yaml.YAMLGenerator;
import net.javaguides.springboot.dto.QueryDTO;
import net.javaguides.springboot.exception.ResourceNotFoundException;
import net.javaguides.springboot.mapper.QueryMapper;
import net.javaguides.springboot.model.Metric;
import net.javaguides.springboot.model.Queries;
import net.javaguides.springboot.model.UserEntity;
import net.javaguides.springboot.repository.DatabaseRepository;
import net.javaguides.springboot.repository.QueryRepository;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.security.JWTGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.stream;

@RestController
@RequestMapping("/api/v1")
public class QueryController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private JWTGenerator tokenGenerator;
	@Autowired
	private QueryRepository queryRepository;

	@Autowired
	private DatabaseRepository databaseRepository;

	
	// get all queries
	@GetMapping("/admin/queries")
	public List<Queries> getAllQueries(){
		return queryRepository.findAll();
	}

	@GetMapping("/queries")
	public List<Queries> getAllQueriesByUser(@RequestHeader("Authorization") String authorizationHeader){
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			authorizationHeader = authorizationHeader.substring(7); // Skip "Bearer " prefix
		}
		String username = tokenGenerator.getUsernameFromJWT(authorizationHeader);
		List<Queries> res = queryRepository.findByUsernameFromJoinedTables(username);
		return res;
	}


	// create query rest api
	@PostMapping("/queries")
	public ResponseEntity<Queries> createQuery(@RequestHeader("Authorization") String authorizationHeader,@RequestBody Queries query)  {
		String username = tokenGenerator.getUsernameFromJWT(authorizationHeader);
		UserEntity user = userRepository.findByUsername(username).get();
		query.setUser(user);
		queryRepository.save(query);
		return ResponseEntity.ok(query);
    }
	@PostMapping("/queries/test")
	public ResponseEntity<String> createQuery(@RequestHeader("Authorization") String authorizationHeader,@RequestBody QueryDTO queryDTO)  {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			authorizationHeader = authorizationHeader.substring(7); // Skip "Bearer " prefix
		}
		String username = tokenGenerator.getUsernameFromJWT(authorizationHeader);

		List<Queries> queries = queryRepository.findByUsernameFromJoinedTables(username);
		for (Queries query : queries) {
			if (query.getName().equals(queryDTO.getName()))
				throw new IllegalArgumentException("Query already exists.");
		}

		String databaseSave = "";
		for(int i = 0 ; i < queryDTO.databases.size() ; i ++ ){
			if(i == queryDTO.databases.size() - 1){
				databaseSave = databaseSave + queryDTO.databases.get(i).getName();
			}else{
				databaseSave = databaseSave + queryDTO.databases.get(i).getName() + "," ;
			}
		}
		String metricSave = "";
		for(int i = 0 ; i < queryDTO.metrics.size() ; i ++ ){
			if(i == queryDTO.metrics.size() - 1){
				metricSave = metricSave + queryDTO.metrics.get(i).getName();
			}else{
				metricSave = metricSave + queryDTO.metrics.get(i).getName() + "," ;
			}
		}
		Queries query = new Queries(queryDTO.getId(),queryDTO.getName(),queryDTO.getTimeout(),queryDTO.getInterval(),queryDTO.getSql(),databaseSave,metricSave);
		UserEntity user = userRepository.findByUsername(username).get();
		query.setUser(user);
		queryRepository.save(query);
		return ResponseEntity.ok("ok");
	}
	// get query by id rest api
	@GetMapping("/queries/{id}")
	public ResponseEntity<Queries> getQueryById(@PathVariable Long id) {
		Queries query = queryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Queries not exist with id :" + id));
		return ResponseEntity.ok(query);
	}
	
	// update query rest api
	@PutMapping("/queries/{id}")
	public ResponseEntity<Queries> updateQuery(@PathVariable Long id, @RequestBody QueryDTO queryDetails){
		Queries query = queryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Queries not exist with id :" + id));
		String databaseSave = "";
		for(int i = 0 ; i < queryDetails.databases.size() ; i ++ ){
			if(i == queryDetails.databases.size() - 1){
				databaseSave = databaseSave + queryDetails.databases.get(i).getName();
			}else{
				databaseSave = databaseSave + queryDetails.databases.get(i).getName() + "," ;
			}
		}
		String metricSave = "";
		for(int i = 0 ; i < queryDetails.metrics.size() ; i ++ ){
			if(i == queryDetails.metrics.size() - 1){
				metricSave = metricSave + queryDetails.metrics.get(i).getName();
			}else{
				metricSave = metricSave + queryDetails.metrics.get(i).getName() + "," ;
			}
		}
		query.setName(queryDetails.getName());
		query.setParameters(queryDetails.getParameters());
		query.setTimeout(queryDetails.getTimeout());
		query.setDatabases(databaseSave);
		query.setMetrics(metricSave);
		query.setSql(queryDetails.getSql());
		query.setSchedule(queryDetails.getSchedule());
		query.setInterval(queryDetails.getInterval());
		Queries updatedQuery = queryRepository.save(query);
		return ResponseEntity.ok(updatedQuery);
	}
	// delete query rest api
	@DeleteMapping("/queries/{id}")
	public ResponseEntity<Map<String, Boolean>> deleteQuery(@PathVariable Long id){
		Queries query = queryRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Queries not exist with id :" + id));
		
		queryRepository.delete(query);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return ResponseEntity.ok(response);
	}
	@GetMapping("/queries/yaml")
	public ResponseEntity<byte[]> downloadYaml() throws IOException {
		// Khởi tạo đối tượng YAMLFactory
		YAMLFactory yamlFactory = new YAMLFactory();
		yamlFactory.disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
		yamlFactory.disable(YAMLGenerator.Feature.SPLIT_LINES);
		yamlFactory.enable(YAMLGenerator.Feature.MINIMIZE_QUOTES);
		yamlFactory.enable(YAMLGenerator.Feature.ALWAYS_QUOTE_NUMBERS_AS_STRINGS);

		ObjectMapper objectMapper = new ObjectMapper(yamlFactory);
		List<Queries> allQueries = queryRepository.findAll();
		QueryMapper mapperQuery = new QueryMapper(queryRepository);
		Map<String,Map<String, Object>> queryMap = new HashMap<String, Map<String, Object>>();
		Map<String,Object> testDBMap = new HashMap<String, Object>();
		queryMap = mapperQuery.toYamlMapAdd(allQueries);

		try {
			objectMapper.writeValue(new File("/Users/phuongmai/Documents/test2.yaml"),queryMap );
			System.out.println(" Export to yaml success " );
		} catch (IOException e) {
			System.out.println(" Export to yaml failed " + e.toString() );
			throw new RuntimeException(e);
		}
		Path path = Paths.get("/Users/phuongmai/Documents/test.yaml");

		// Read the file content
		byte[] content = Files.readAllBytes(path);

		// Set the headers
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.parseMediaType("application/x-yaml"));
		headers.setContentDispositionFormData("attachment", "file.yaml");
		headers.setContentLength(content.length);
		return ResponseEntity.ok().headers(headers).body(content);
	}
	
}
