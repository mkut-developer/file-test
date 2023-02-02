package code.challenge.filehandler.service.impl;

import code.challenge.filehandler.constant.ErrorMessage;
import code.challenge.filehandler.excetption.BadRequestException;
import code.challenge.filehandler.service.UploadFileValidator;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileValidatorImpl implements UploadFileValidator {

    @Override
    public void validateTextFile(MultipartFile file) {
        if (!file.getResource().isReadable()) {
            throw new BadRequestException(ErrorMessage.BAD_CONTENT);
        } else if (file.getContentType() == null || file.getContentType().contains("image")) {
            throw new BadRequestException(ErrorMessage.NOT_TEXT_CONTENT);
        }
    }
}
