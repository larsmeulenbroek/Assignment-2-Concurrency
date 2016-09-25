import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public class Office {
    /* Constants */
    public static final int NUM_USERS = 10;
    public static final int NUM_DEVS = 6;
    /**************************************/

    /* Booleans */
    public static boolean inMeeting;
    public static boolean isUser;
    /**************************************/

    /*Counters and countdownlatchers */
    public static int usersBeforeMeeting = 0;
    public static int developersBeforeMeeting = 0;

    //begin
    public static CountDownLatch onsetUsersLock;
    public static CountDownLatch onsetUsers;
    public static CountDownLatch onsetDevsLock;
    public static CountDownLatch onsetDevs;

    //end
    public static CountDownLatch onendUsersLock;
    public static CountDownLatch onendUsers;
    public static CountDownLatch onendDevsLock;
    public static CountDownLatch onendDevs;
    /**************************************/

    /* Semaphores */
    public static Semaphore awaitingUsers;        //infinite
    public static Semaphore awaitingDevs;         //max 3
    public static Semaphore mutex = new Semaphore(1, true);
    public static Semaphore devsmutex = new Semaphore(1, true);
    /**************************************/


    /* Instantiate all semaphores and countdownlatchers that are required */
    public Office() {
        awaitingUsers = new Semaphore(0, true);
        awaitingDevs = new Semaphore(0, true);

        onsetUsersLock = new CountDownLatch(1);
        onsetDevsLock = new CountDownLatch(1);
    }

    public static void main(String[] args) {
        Office office = new Office();

        /* Create 1 productleader */
        new ProductLeader(1, office);


        /* Create 10 users */
        for (int i = 0; i < NUM_USERS; i++) {
            new User(i, office);
        }

         /* Create 6 developers*/
        for (int i = 0; i < NUM_DEVS; i++) {
            new Developer(i, office);
        }
    }

    /**
     * een gebruiker meld zich voor meeting
     */
    public static void userReportsProblem(User user) throws Exception{
        mutex.acquire();
        usersBeforeMeeting++;
        mutex.release();
        System.out.println("total users waiting: " + usersBeforeMeeting + "\n");

        awaitingUsers.acquire();
    }

    public static void developerReport() throws Exception{
        if(developersBeforeMeeting < 3) {
            mutex.acquire();
            developersBeforeMeeting++;
            System.out.println("total developers waiting: " + developersBeforeMeeting + "\n");
            mutex.release();
            awaitingDevs.acquire();
        }
    }

    public static void startMeeting() throws InterruptedException {
        if (isUser) {

            onsetUsers = new CountDownLatch(1 + usersBeforeMeeting);
            onsetUsersLock = new CountDownLatch(1);

            onendUsers = new CountDownLatch(1 + usersBeforeMeeting);
            onendUsersLock = new CountDownLatch(1);

            awaitingDevs.release(developersBeforeMeeting);

            mutex.acquire();
            developersBeforeMeeting = 0;
            mutex.release();

            awaitingUsers.release(Office.usersBeforeMeeting);

            mutex.acquire();
            usersBeforeMeeting = 0;
            mutex.release();
        } else {
            onsetDevs = new CountDownLatch(developersBeforeMeeting);
            onsetDevsLock = new CountDownLatch(1);

            onendDevs = new CountDownLatch(developersBeforeMeeting);
            onendDevsLock = new CountDownLatch(1);

            awaitingDevs.release(developersBeforeMeeting);
            mutex.acquire();
            developersBeforeMeeting = 0;
            mutex.release();
        }
    }
}
