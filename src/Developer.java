import static java.lang.Thread.sleep;

public class Developer extends People {

    public Developer(int id, Office office) {
        super(id, office);
        Thread developer = new Thread(this);
        developer.start();
    }

    @Override
    public void run() {
        while (true) {
            try {
                doWork();
                if (!Office.inMeeting) {

                    Office.developerReport();

                    if (Office.isUser) {

                        if(Office.devsmutex.tryAcquire()) {

                            Office.onsetUsersLock.await();
                            Office.onsetUsers.countDown();

                            //user meeting
                            Meeting.meeting();

                            Office.onendUsers.countDown();
                            Office.onendUsersLock.countDown();

                            Office.devsmutex.release();
                        }

                    } else if (!Office.isUser) {
                        Office.onsetDevsLock.await();
                        Office.onsetDevs.countDown();

                        //developer meeting
                        Meeting.meeting();

                        Office.onendDevs.countDown();
                        Office.onendDevsLock.await();
                    }
                } else {
                    doWork();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void doWork() {
        try {
            sleep((long) (Math.random() * 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
