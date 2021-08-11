package vision.training;

public class InvalidIntervalException extends RuntimeException {

    public InvalidIntervalException() {
    }

    public InvalidIntervalException(String massage) {
        super(massage);
    }

    public InvalidIntervalException(String massage, Exception e) {
        super(massage, e);
    }
}
