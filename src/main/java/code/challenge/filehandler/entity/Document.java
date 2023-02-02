package code.challenge.filehandler.entity;

import code.challenge.filehandler.excetption.BadArgumentException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "documents")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false)
    private String name;
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name = "created", nullable = false)
    private LocalDateTime created;
    @JsonIgnore
    @OneToMany(mappedBy = "document", cascade = CascadeType.ALL)
    private List<Line> lines;


    public void addLine(String content) {
        if (content == null) return;
        if (lines == null) lines = new ArrayList<>();

        var line = Line.of(content, lines.size(), this);
        lines.add(line);
    }

    public static Document of(String name, List<String> content) {
        if (name == null) throw new BadArgumentException("document does not contain name.");
        if (content == null) throw new BadArgumentException("document does not contain content.");

        var document = Document
                .builder()
                .name(name)
                .created(LocalDateTime.now())
                .build();

        content.forEach(document::addLine);

        return document;
    }
}
