package code.challenge.filehandler.service.impl;

import code.challenge.filehandler.constant.ErrorMessage;
import code.challenge.filehandler.entity.Line;
import code.challenge.filehandler.excetption.DbException;
import code.challenge.filehandler.repository.LineRepository;
import code.challenge.filehandler.service.LineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LineServiceImpl implements LineService {
    private final LineRepository lineRepository;

    @Override
    public Line save(Line line) {
        log.debug("save: {}", line);
        try {
            return lineRepository.save(line);
        } catch (Exception e) {
            log.warn("failed to save new line content ({}); rsn: {}", line, e.getMessage());
            throw new DbException(ErrorMessage.DB_ERROR);
        }
    }

    @Override
    public Optional<Line> findOneRandomFromAll() {
        log.debug("find random line");
        try {
            var lines = lineRepository.findRandomLine(1);

            if (lines != null && !lines.isEmpty()) {
                return Optional.of(lines.iterator().next());
            } else return Optional.empty();
        } catch (Exception e) {
            log.warn("failed to fetch a random line; rsn: {}", e.getMessage());
            throw new DbException(ErrorMessage.DB_ERROR);
        }
    }

    @Override
    public List<Line> findLongest(int count) {
        log.debug("find longest lines; count: {}", count);
        try {
            if (count == 0) count = 1;

            var list = lineRepository.findLongest(count);
            return list != null ? list : Collections.emptyList();
        } catch (Exception e) {
            log.warn("failed to fetch fetch longest lines; rsn: {}", e.getMessage());
            throw new DbException(ErrorMessage.DB_ERROR);
        }
    }
}
