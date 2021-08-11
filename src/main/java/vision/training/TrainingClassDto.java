package vision.training;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainingClassDto {

    private Long Id;

    private String name;

    private LocalDate startDate;

    private LocalDate endDate;

    public TrainingClassDto(String name) {
        this.name = name;
    }

    public TrainingClassDto(String name, LocalDate startDate, LocalDate endDate) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
