package vision.syllabusses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SyllabussDto {

    private long id;

    private String name;

    public SyllabussDto(String name) {
        this.name = name;
    }
}
