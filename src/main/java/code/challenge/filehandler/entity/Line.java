package code.challenge.filehandler.entity;

import code.challenge.filehandler.constant.ErrorMessage;
import code.challenge.filehandler.excetption.BadArgumentException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.Length;

@Entity
@Table(name = "content")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Line implements Comparable<Line> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private Long id;
    @Column(nullable = false, length = Length.LOB_DEFAULT)
    private String content;
    @Column(nullable = false)
    private Integer length;
    @Column(nullable = false, name = "index_num")
    private Integer index;
    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;


    @Override
    public int compareTo(Line another) {
        return index - another.index;
    }

    public static Line of(String content, Integer index, Document document) {
        if (content == null || index == null || document == null) {
            throw new BadArgumentException(ErrorMessage.WRONG_LINE);
        }

        return Line.builder()
                .content(content)
                .length(content.length())
                .index(index)
                .document(document)
                .build();
    }
}
