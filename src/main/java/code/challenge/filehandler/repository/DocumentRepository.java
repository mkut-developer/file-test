package code.challenge.filehandler.repository;

import code.challenge.filehandler.entity.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DocumentRepository extends JpaRepository<Document, Long> {
    Optional<Document> findFirstByOrderByCreatedDesc();

    @Query(value = "SELECT * FROM documents ORDER BY RAND() LIMIT :size", nativeQuery = true)
    List<Document> findRandomDocument(@Param("size") int size);
}
