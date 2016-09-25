import static java.lang.Thread.sleep;

public class User extends People {

    /* Initializes all of the required variables for the Guest instance */
    public User(int id, Office office) {
        super(id, office);
        Thread user = new Thread(this);
        user.start();
    }

    /* Runs the thread for the given instance of user */
    @Override
    public void run() {
        while (true) {
            try {
                liveAndTest();
                if (foundAProblem()) {
//                    System.out.println("User " + this.userid + " found a problem");

                    Office.userReportsProblem(this);

                    Office.onsetUsersLock.await();
                    Office.onsetUsers.countDown();

                    Meeting.meeting();

                    Office.onendUsers.countDown();
                    Office.onendUsersLock.await();

                } else {
                    liveAndTest();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private boolean foundAProblem() {
        if (((Math.random() * 10) + 1) < 5) {
            return true;
        } else {
            return false;
        }
    }

    private void liveAndTest() {
        try {
            sleep((long) (Math.random() * 100000) + 10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
