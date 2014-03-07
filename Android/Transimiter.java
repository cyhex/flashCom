package flashcom;

public class Transimiter {

    public static final int TIME_LOW = 100;
    public static final int TIME_HIGH = 50;
    public static final int TIME_LIGHT_PULSE = 50;

    public void transmit(String str) throws InterruptedException {
        String binaryString = toBinaryString(str);

        for (char c : binaryString.toCharArray()) {
            on();
            Thread.sleep(TIME_LIGHT_PULSE);
            off();
            if (c == '0') {
                Thread.sleep(TIME_LOW);
            } else {
                Thread.sleep(TIME_HIGH);
            }
        }
    }

    private void on() {

    }

    private void off() {

    }

    private String toBinaryString(String str) {
        String r = "";
        byte[] bytes = str.getBytes();

        for (byte b : bytes) {
            r += Integer.toBinaryString(b);
        }
        return r;
    }

}
