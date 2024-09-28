package net.javaguides.springboot.repository;
import net.javaguides.springboot.model.Queries;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QueryRepository extends JpaRepository<Queries, Long> {

    @Query(value = "SELECT q.id,q.name,q.interval_time,q.timeout,q.database_name,q.metrics,q.command,q.parameters,q.schedule,q.user_id\n" +
            "FROM queries as q\n" +
            "inner join users as u\n" +
            "on q.user_id = u.id\n" +
            "where u.username = :username", nativeQuery = true)
    List<Queries> findByUsernameFromJoinedTables(@Param("username") String username);

    boolean existsByName(String name);
}
