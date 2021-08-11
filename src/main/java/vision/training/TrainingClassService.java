package vision.training;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TrainingClassService {

    private TrainingClassRepo trainingClassRepo;

    private ModelMapper modelMapper;

    public TrainingClassService(TrainingClassRepo trainingClassRepo, ModelMapper modelMapper) {
        this.trainingClassRepo = trainingClassRepo;
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
}
