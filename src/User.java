public class User extends Thread {

    private static Office company = new Office();

    public void run() {

        while (true) {

            if(foundAProblem()) {
//                company.reportProblem



            } else {
                testing();
            }
//foundAproblemAndReportIt

//waitsForInvitation

//getsInvitation

//goesToCompany

//reportsHeIsThere

//WaitsTillProductLeaderInvitesHim

//MeetingStarts
        }
    }

    private boolean foundAProblem() {
        if (((Math.random()*10) + 1) < 5) {
            return true;
        } else {
            return false;
        }
    }

    private void testing() {
        try {
            sleep((long) (Math.random()*1000) + 300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void goesToCompany() {
        try {
            sleep((long) (Math.random()*1000) + 300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
