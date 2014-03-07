package com.cyhex.flashcom.lib;

import android.hardware.Camera;

public class Transmitter {

    public static final int TIME_LOW = 60;
    public static final int TIME_HIGH = 40;
    public static final int TIME_LIGHT_PULSE = 50;

    private Camera cam;
    private Camera.Parameters params;

    public Transmitter(Camera cam) {
        this.cam = cam;
        this.params = cam.getParameters();
    }

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
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        cam.setParameters(params);
    }

    private void off() {
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        cam.setParameters(params);
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
