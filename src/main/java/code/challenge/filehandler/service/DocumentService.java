package code.challenge.filehandler.service;

import code.challenge.filehandler.entity.Document;

import java.util.List;
import java.util.Optional;

public interface DocumentService {
    Document save(Document document);

    List<Document> findAll();

    Optional<Document> findLast();

    List<Document> findRandom(int count);

    Optional<Document> findRandom();

    List<String> extractLongestLinesByDevice(Document document, int count);
}
