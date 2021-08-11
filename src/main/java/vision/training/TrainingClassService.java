package vision.training;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import vision.registration.Registration;
import vision.registration.RegistrationRepo;
import vision.registration.RegistrationStudentCommand;
import vision.students.Student;
import vision.students.StudentRepo;
import vision.students.StudentStatus;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingClassService {

    private TrainingClassRepo trainingClassRepo;

    private RegistrationRepo registrationRepo;

    private StudentRepo studentRepo;

    private ModelMapper modelMapper;

    public TrainingClassService(TrainingClassRepo trainingClassRepo, RegistrationRepo registrationRepo, StudentRepo studentRepo, ModelMapper modelMapper) {
        this.trainingClassRepo = trainingClassRepo;
        this.registrationRepo = registrationRepo;
        this.studentRepo = studentRepo;
        this.modelMapper = modelMapper;
    }

    public List<TrainingClassDto> getAllTraning() {
        return trainingClassRepo.findAll()
                .stream()
                .map(a -> modelMapper.map(a, TrainingClassDto.class))
                .collect(Collectors.toList());
    }

    @Transactional
    public TrainingClassDto update(long id, UpdateTrainingClassCommand command) {
        TrainingClass trainingClass = trainingClassRepo.findById(id).orElseThrow(() -> new IllegalArgumentException());
        if (command.getName() != null) {trainingClass.setName(command.getName());}
        if (command.getInterval().getStartDate() != null) {trainingClass.setStartDate(command.getInterval().getStartDate());}
        if (command.getInterval().getEndDate() != null  && trainingClass.getStartDate().isBefore(command.getInterval().getEndDate())) {
            trainingClass.setEndDate(command.getInterval().getEndDate());
        } else {throw new InvalidIntervalException("Start time can be later than end time!");}

        return modelMapper.map(trainingClass, TrainingClassDto.class);
    }

    public TrainingClassDto createTrainingClass(CreateTrainingClassCommand createCommand) {
        TrainingClass trainingClass = new TrainingClass(createCommand.getName(),
                createCommand.getInterval().getStartDate(), createCommand.getInterval().getEndDate());
        trainingClassRepo.save(trainingClass);
        return modelMapper.map(trainingClass, TrainingClassDto.class);
    }

    public void delTrainingClass(Long id) {
        trainingClassRepo.deleteById(id);
    }

    public void delAllTrainingClass() {
        trainingClassRepo.deleteAll();
    }

    public void delTrainingClassByName(Optional<String> prefix) {
        prefix.ifPresent(s -> trainingClassRepo.deleteByName(s.trim()));
    }

    public TrainingClassDto getTrainingById(long id) {
        return modelMapper.map(trainingClassRepo.getById(id), TrainingClassDto.class);
    }

    public TrainingClassDto registration(long id, RegistrationStudentCommand command) {
        TrainingClass trainingClass = trainingClassRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Training Class not found"));
        Student student = studentRepo.findById(command.getId())
                .orElseThrow(() -> new IllegalArgumentException("Student Class not found"));
        registrationRepo.save(new Registration(student, StudentStatus.ACTIVE, trainingClass));
        return modelMapper.map(trainingClass, TrainingClassDto.class);
    }
}
