package vision.training;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UpdateTrainingClassCommand {

    //private Long Id;

    @Name
    private String name;

    @IsValidInterval
    private Interval interval;

    public UpdateTrainingClassCommand(String name) {
        this.name = name;
    }

    public UpdateTrainingClassCommand(String name, Interval interval) {
        this.name = name;
        this.interval = interval;
    }
}
