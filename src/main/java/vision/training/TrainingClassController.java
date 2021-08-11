package vision.training;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.zalando.problem.Problem;
import org.zalando.problem.Status;
import vision.syllabusses.CreateSylabussCommand;
import vision.syllabusses.SylabussService;
import vision.syllabusses.SyllabussDto;
//import org.zalando.problem.violations.Violation;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Tag(name = "Training Classes")
@RestController
@RequestMapping("/api/trainingclasses")
public class TrainingClassController {

    private TrainingClassService trainingClassService;

    private SylabussService sylabussService;

    public TrainingClassController(TrainingClassService trainingClassService, SylabussService sylabussService) {
        this.trainingClassService = trainingClassService;
        this.sylabussService = sylabussService;
    }

    @PostMapping("/{id}/syllabuses")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Training classes are empty, you can add one sylabuss.",
            description = "Training classes are empty, you can add one sylabuss.")
    @ApiResponse(responseCode = "404", description = "There is not any of Vision.training!")
    public SyllabussDto addSlylabussToTraining(@PathVariable("training_id") long trainingId,
                                                   @PathVariable("sylabuss_id") long sylabussId){
        return sylabussService.addSlylabussToTraining(trainingId, sylabussId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Listing all Vision.training classes", description = "Be carefull, all of them will be listed.")
    @ApiResponse(responseCode = "404", description = "There is not any of Vision.training!")
    public List<TrainingClassDto> getAllTraning(){
        return trainingClassService.getAllTraning();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "One class is listing", description = "One of them will be listed")
    @ApiResponse(responseCode = "404", description = "Training Classes not found")
    public TrainingClassDto getTrainingById(@PathVariable("id") long id) {
        return trainingClassService.getTrainingById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Training classes can be created.", description = "Training classes can be created with 3 parameter.")
    @ApiResponse(responseCode = "404", description = "There is not any of Vision.training!")
    public TrainingClassDto createTrainingClass(@Valid @RequestBody CreateTrainingClassCommand createCommand){
        return trainingClassService.createTrainingClass(createCommand);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Training Classes can be upgrade.", description = "Be carefull, it can not be withdraw.")
    @ApiResponse(responseCode = "404", description = "Training Classes not found")
    public ResponseEntity updateTraining(@PathVariable("id") long id, @Valid @RequestBody UpdateTrainingClassCommand command) {
        try {
            return ResponseEntity.ok(trainingClassService.update(id, command));
        } catch (IllegalArgumentException iae) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "A Vision.training Class be deleted by ID.", description = "Be carefull, it can not be withdraw.")
    public void deleteTrainingById(@PathVariable("id") long id) {
        trainingClassService.delTrainingClass(id);
    }

    @DeleteMapping("/del")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "A Vision.training Class be deleted by name.", description = "Be carefull, it can not be withdraw.")
    public void deleteTrainingByName(@RequestParam Optional<String> prefix) {
        trainingClassService.delTrainingClassByName(prefix);
    }

    @DeleteMapping("/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "All Training Classes will be deleted.", description = "Be carefull, it can not be withdraw.")
    public void deleteAllTraining() {
        trainingClassService.delAllTrainingClass();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<Problem> handleNotFound(IllegalArgumentException iae) {
        Problem problem = Problem.builder()
                .withType(URI.create("/api/trainingclasses"))
                .withTitle("Not found!")
                .withStatus(Status.NOT_FOUND)
                .withDetail(iae.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(InvalidIntervalException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> invalidIntervalhandler(InvalidIntervalException ide) {
        Problem problem = Problem.builder()
                .withType(URI.create("/api/trainingclasses"))
                .withTitle("Bad time interval!")
                .withStatus(Status.I_AM_A_TEAPOT)
                .withDetail(ide.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<Problem> handleValidException(MethodArgumentNotValidException mnve) {
        List<Violation> violations = mnve.getBindingResult().getFieldErrors().stream()
                .map(fe -> new Violation(fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.toList());

        Problem problem = Problem.builder()
                .withType(URI.create("instruments/not-valid"))
                .withTitle("Validation error")
                .withStatus(Status.BAD_REQUEST)
                .withDetail(mnve.getMessage())
                .with("violations", violations)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .contentType(MediaType.APPLICATION_PROBLEM_JSON)
                .body(problem);
    }


//    data  http
//    Create POST többször fut, az eredmény mindig változik
//    Read GET
//    Update PUT csak egyszer fut le idempotens
//    Delete DELETE
}
