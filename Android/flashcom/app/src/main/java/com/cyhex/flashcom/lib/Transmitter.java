package com.cyhex.flashcom.lib;

import android.hardware.Camera;
import android.os.SystemClock;
import android.util.Log;

public class Transmitter {

    private int timeLow = 40;
    private int timeHigh = 80;
    private int timeLightPulse = 40;

    private Camera cam;
    private Camera.Parameters paramOn, paramOff;

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
        this.paramOn = cam.getParameters();
        this.paramOff = cam.getParameters();
        this.paramOn.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        this.paramOff.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
    }

    public void transmit(String str) throws InterruptedException {
        String binaryString = toBinaryString(str);
        Log.i("Transmitter", binaryString);
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
        on();
        Thread.sleep(timeLightPulse);
        off();
    }

    private void on() {
        cam.setParameters(paramOn);
    }

    private void off() {
        cam.setParameters(paramOff);
    }

    private String toBinaryString(String str) {
        String r = "";
        byte[] bytes = str.getBytes();

        for (byte b : bytes) {
            r += String.format("%8s", Integer.toBinaryString(b)).replace(' ', '0');
        }
        return r;

    }

}
