package com.cyhex.flashcom.lib;

import android.hardware.Camera;

public class Transmitter {

    private int timeLow = 60;
    private int timeHigh = 40;
    private int timeLightPulse = 50;

    private Camera cam;
    private Camera.Parameters params;

    public int getTimeLow() {
        return timeLow;
    }

    public void setTimeLow(int timeLow) {
        this.timeLow = timeLow;
    }

    public int getTimeHigh() {
        return timeHigh;
    }

    public void setTimeHigh(int timeHigh) {
        this.timeHigh = timeHigh;
    }

    public int getTimeLightPulse() {
        return timeLightPulse;
    }

    public void setTimeLightPulse(int timeLightPulse) {
        this.timeLightPulse = timeLightPulse;
    }

    public Transmitter(Camera cam) {
        this.cam = cam;
        this.params = cam.getParameters();
    }

    public void transmit(String str) throws InterruptedException {
        String binaryString = toBinaryString(str);

        for (char c : binaryString.toCharArray()) {
            on();
            Thread.sleep(timeLightPulse);
            off();
            if (c == '0') {
                Thread.sleep(timeLow);
            } else {
                Thread.sleep(timeHigh);
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
