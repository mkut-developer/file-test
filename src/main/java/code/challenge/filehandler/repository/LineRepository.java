package code.challenge.filehandler.repository;

import code.challenge.filehandler.entity.Line;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LineRepository extends JpaRepository<Line, Long> {

    @Query(value = "SELECT * FROM content ORDER BY RAND() LIMIT :size", nativeQuery = true)
    List<Line> findRandomLine(@Param("size") int size);

    @Query(value = "SELECT * FROM content ORDER BY length DESC LIMIT :size", nativeQuery = true)
    List<Line> findLongest(@Param("size") int size);
}
