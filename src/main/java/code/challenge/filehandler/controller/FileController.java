package code.challenge.filehandler.controller;

import code.challenge.filehandler.entity.Document;
import code.challenge.filehandler.service.DocumentService;
import code.challenge.filehandler.service.UploadFileValidator;
import code.challenge.filehandler.util.MultipartFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("file")
public class FileController {
    private final UploadFileValidator fileValidator;
    private final DocumentService documentService;


    /**
     * Uploads file onto the server
     *
     * @param file file to be uploaded
     * @return Information about file
     */
    @PostMapping("/upload")
    public Document uploadFile(@RequestParam("file") MultipartFile file) {
        log.debug("upload file: {}", file.getOriginalFilename());

        fileValidator.validateTextFile(file);

        var document = Document.of(file.getOriginalFilename(), MultipartFileUtil.readLines(file));

        return documentService.save(document);
    }
}
