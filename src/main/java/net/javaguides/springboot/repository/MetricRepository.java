package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Database;
import net.javaguides.springboot.model.Metric;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MetricRepository extends JpaRepository<Metric, Long> {
   Metric findByName(String metrics);
   @Query(value = "SELECT m.id,m.name,m.type,m.description,m.labels,m.buckets,m.states,m.expiration,m.increment,m.user_id\n" +
           "FROM metrics as m\n" +
           "inner join users as u\n" +
           "on m.user_id = u.id\n" +
           "where u.username = :username", nativeQuery = true)
   List<Metric> findByUsernameFromJoinedTables(@Param("username") String username);
   boolean existsByName(String name);

}
