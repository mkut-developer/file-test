package code.challenge.filehandler.service;

import org.springframework.web.multipart.MultipartFile;

public interface UploadFileValidator {
    void validateTextFile(MultipartFile file);
}
