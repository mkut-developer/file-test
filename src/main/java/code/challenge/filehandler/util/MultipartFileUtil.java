package code.challenge.filehandler.util;

import code.challenge.filehandler.constant.ErrorMessage;
import code.challenge.filehandler.excetption.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class MultipartFileUtil {

    private MultipartFileUtil() {
    }

    public static List<String> readLines(MultipartFile multipartFile) {
        var encoding = detectCharset(multipartFile);

        try (var reader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream(), encoding))) {
            return reader.lines()
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.warn("failed to extract input stream from file {}; rsn: {}", multipartFile.getOriginalFilename(), e.getMessage());
            throw new BadRequestException(ErrorMessage.BAD_CONTENT);
        }
    }

    public static String detectCharset(MultipartFile multipartFile) {
        try (var stream = multipartFile.getInputStream()) {
            var encoding = UniversalDetector.detectCharset(stream);

            if (encoding == null) {
                log.info("unknown charset encoding; file: {}", multipartFile.getOriginalFilename());
            } else {
                log.debug("encoding is {}", encoding);
                return encoding;
            }
        } catch (IOException e) {
            log.warn("failed to extract input stream from file {}; rsn: {}", multipartFile.getOriginalFilename(), e.getMessage());
        }
        throw new BadRequestException(ErrorMessage.BAD_CONTENT);
    }
}
