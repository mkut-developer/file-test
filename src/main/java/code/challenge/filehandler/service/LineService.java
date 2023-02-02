package code.challenge.filehandler.service;

import code.challenge.filehandler.entity.Line;

import java.util.List;
import java.util.Optional;

public interface LineService {
    Line save(Line line);

    Optional<Line> findOneRandomFromAll();

    List<Line> findLongest(int count);
}
