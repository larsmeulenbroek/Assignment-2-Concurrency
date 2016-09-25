import static java.lang.Thread.sleep;

public class ProductLeader extends People {

    /* Initializes all of the required variables for the ProductLeader2 instance */
    public ProductLeader(int id, Office office) {
        super(id, office);
        Thread productleader = new Thread(this);
        productleader.start();
    }

    /* Runs the thread for the given instance of userMeetingDev */
    @Override
    public void run() {
        while (true) {
            try {
                goingToDoNothing();
                if (Office.usersBeforeMeeting > 0 && Office.developersBeforeMeeting > 0) {

                    System.err.println("Everybody arrived and meeting is ready to start");
                    System.err.println("there are " + Office.developersBeforeMeeting + " developers waiting");

                    Office.inMeeting = true;
                    Office.isUser = true;

                    Office.startMeeting();

                    Office.onsetUsersLock.countDown();
                    Office.onsetUsers.await();

                    Meeting.meeting();

                    Office.onendUsers.await();
                    Office.onendUsersLock.countDown();


                    Office.inMeeting = false;
                    Office.isUser = false;

                    System.err.println("User meeting is over" + "\n");

                } else if (Office.developersBeforeMeeting == 3) {
                    System.err.println("Developer Meeting starts");
                    System.err.println("there are " + Office.usersBeforeMeeting + " users waiting");

                    Office.inMeeting = true;
                    Office.isUser = false;

                    Office.startMeeting();

                    Office.onsetDevsLock.countDown();
                    Office.onsetDevs.await();

                    Meeting.meeting();

                    Office.onendDevs.await();
                    Office.onendDevsLock.countDown();

                    Office.inMeeting = false;
                    System.err.println("Developer Meeting is over" + "\n");

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void goingToDoNothing() {
        try {
            sleep((long) (Math.random() * 10000));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
