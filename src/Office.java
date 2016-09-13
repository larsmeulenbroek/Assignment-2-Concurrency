import java.util.concurrent.Semaphore;

public class Office {
    private static final int NR_OF_ENDUSERS = 10;
    private static final int NR_OF_DEVELOPERS = 6;
    private Thread[] developers;
    private Thread[] endusers;

    private Semaphore meetingroom;


    public Office() {
        developers = new Thread[NR_OF_DEVELOPERS];
        endusers = new Thread[NR_OF_ENDUSERS];

    }

}
