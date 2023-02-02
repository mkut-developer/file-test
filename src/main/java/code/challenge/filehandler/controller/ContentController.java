package code.challenge.filehandler.controller;

import code.challenge.filehandler.entity.Document;
import code.challenge.filehandler.entity.Line;
import code.challenge.filehandler.service.DocumentService;
import code.challenge.filehandler.service.LineService;
import code.challenge.filehandler.util.ResponseBuilder;
import code.challenge.filehandler.util.StringReverser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@RestController()
@RequestMapping("content")
public class ContentController {
    private final DocumentService documentService;
    private final LineService lineService;


    @GetMapping("/document/all")
    public List<Document> findAllDocuments() {
        log.debug("fetch all files");

        return documentService.findAll();
    }

    @GetMapping(value = "/raw/by-one/last-random",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE, MediaType.TEXT_PLAIN_VALUE}
    )
    public ResponseEntity<?> findRandomLineFromLastUploaded(@RequestHeader HttpHeaders headers) {
        log.debug("fetch random line from the last file");

        var document = documentService.findLast().orElse(null);
        if (document == null || document.getLines() == null) {
            log.debug("db is empty");
            return null;
        }

        var mediaType = headers.getContentType();

        var httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(mediaType);

        var line = chooseRandom(document);
        var body = ResponseBuilder.buildResponseOfLineByMediaType(mediaType, line, document);

        return new ResponseEntity(body, httpHeaders, 200);
    }

    @GetMapping(value = "/raw/by-all/random-backwards")
    public String findRandomLineBackwards() {
        log.debug("fetch random line backwards");

        var optionalLine = lineService.findOneRandomFromAll();

        return optionalLine.map(line -> StringReverser.reverse(line.getContent())).orElse(null);
    }

    @GetMapping(value = "/raw/by-all/longest")
    public List<String> findLongestLinesFromAll(@RequestParam(name = "count", defaultValue = "100") Integer count) {
        log.debug("fetch longest lines of all files; count: {}", count);

        return lineService.findLongest(count)
                .stream()
                .map(Line::getContent)
                .collect(Collectors.toList());
    }

    @GetMapping(value = "/raw/by-one/longest")
    public List<String> findLongestLinesFromOne(@RequestParam(name = "last", required = false) Boolean isLastUploaded,
                                                @RequestParam(name = "count", defaultValue = "20") Integer count) {
        log.debug("fetch longest lines of one file; isLastUploaded: {}", isLastUploaded);

        Optional<Document> optional;
        if (Boolean.TRUE.equals(isLastUploaded)) {
            optional = documentService.findLast();
        } else {
            optional = documentService.findRandom();
        }

        var document = optional.orElse(null);
        if (document == null || document.getLines() == null) {
            return Collections.emptyList();
        }

        return documentService.extractLongestLinesByDevice(document, count);
    }


    private static Line chooseRandom(Document document) {
        var lines = document.getLines();
        int index = new Random(System.currentTimeMillis()).nextInt(lines.size());
        return lines.get(index);
    }
}
