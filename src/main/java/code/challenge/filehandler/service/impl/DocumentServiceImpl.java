package code.challenge.filehandler.service.impl;

import code.challenge.filehandler.constant.ErrorMessage;
import code.challenge.filehandler.entity.Document;
import code.challenge.filehandler.entity.Line;
import code.challenge.filehandler.excetption.DbException;
import code.challenge.filehandler.repository.DocumentRepository;
import code.challenge.filehandler.service.DocumentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentServiceImpl implements DocumentService {
    private final DocumentRepository documentRepository;

    @Override
    public Document save(Document document) {
        log.debug("save: {}", document);
        try {
            return documentRepository.save(document);
        } catch (Exception e) {
            log.warn("failed to save new document ({}); rsn: {}", document, e.getMessage());
            throw new DbException(ErrorMessage.DB_ERROR);
        }
    }

    @Override
    public List<Document> findAll() {
        log.debug("extract all documents from db");
        try {
            return documentRepository.findAll();
        } catch (Exception e) {
            log.warn("failed to extract all documents; rsn: {}", e.getMessage());
            throw new DbException(ErrorMessage.DB_ERROR);
        }
    }

    @Override
    public Optional<Document> findLast() {
        log.debug("extract the last document from db");
        try {
            return documentRepository.findFirstByOrderByCreatedDesc();
        } catch (Exception e) {
            log.warn("failed to extract the last document; rsn: {}", e.getMessage());
            throw new DbException(ErrorMessage.DB_ERROR);
        }
    }

    @Override
    public List<Document> findRandom(int count) {
        log.debug("extract random document from db; count: {}", count);

        try {
            var documents = documentRepository.findRandomDocument(count != 0 ? count : 1);
            return documents != null ? documents : Collections.emptyList();
        } catch (Exception e) {
            log.warn("failed to extract document; rsn: {}", e.getMessage());
            throw new DbException(ErrorMessage.DB_ERROR);
        }
    }

    @Override
    public Optional<Document> findRandom() {
        log.debug("extract random document from db");
        var documents = findRandom(1);

        return !documents.isEmpty() ? Optional.of(documents.iterator().next()) : Optional.empty();
    }

    @Override
    public List<String> extractLongestLinesByDevice(Document document, int count) {
        return document.getLines()
                .stream()
                .sorted((doc1, doc2) -> doc2.getLength() - doc1.getLength())
                .limit(count != 0 ? count : 1)
                .map(Line::getContent)
                .collect(Collectors.toList());
    }
}
