package vision.training;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateTrainingClassCommand {

    //private Long Id;

    @Name
    @Schema(description = "Name of Traning Classes", example = "Java", defaultValue = "Training360")
    private String name;

    @IsValidInterval
    private Interval interval;
}
