package com.cyhex.flashcom;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Camera;
import android.os.Handler;
import android.os.Message;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.cyhex.flashcom.lib.Transmitter;

public class MainActivity extends ActionBarActivity {

    private ProgressDialog progress;
    private SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent i = new Intent(this, SettingsActivity.class);
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

    public void sendData(View view) {
        EditText editTest = (EditText) findViewById(R.id.editText);
        final String data = editTest.getText().toString();
        sharedPref = PreferenceManager.getDefaultSharedPreferences(this);

        progress = ProgressDialog.show(MainActivity.this, "Sending", "Sending data: " + data);
        new Thread(new Runnable() {
            public void run() {

                try {
                    Camera cam = Camera.open();
                    cam.startPreview();
                    Transmitter t = new Transmitter(cam);
                    t.setTimeHigh(Integer.parseInt(sharedPref.getString("high_pulse", "60")));
                    t.setTimeLow(Integer.parseInt(sharedPref.getString("low_pulse", "40")));
                    t.setTimeLightPulse(Integer.parseInt(sharedPref.getString("light_pulse", "50")));
                    t.transmit(data);
                    cam.stopPreview();
                    cam.release();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }).start();

    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            progress.dismiss();
        }
    };

}