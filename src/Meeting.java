import static java.lang.Thread.sleep;

public abstract class Meeting {

    public static void meeting() {
        try {
            sleep((long) (Math.random() * 100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
