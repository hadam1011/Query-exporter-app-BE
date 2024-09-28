package net.javaguides.springboot.repository;

import net.javaguides.springboot.model.Database;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface DatabaseRepository extends JpaRepository<Database, Long> {
    Database findByName(String databaseNames);
    @Query(value = "SELECT db.id,db.name,db.dsn,db.labels,db.host_name,db.services_code,db.linkdb,db.connect_sql,db.keep_connect,db.auto_commit,db.user_id\n" +
            "FROM db \n" +
            "INNER JOIN users u \n" +
            "ON db.user_id = u.id \n" +
            "WHERE u.username = :username", nativeQuery = true)
    List<Database> findByUsernameFromJoinedTables(@Param("username") String username);
    boolean existsByName(String name);
}
