package com.example.joel.myappportfolio;

import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showStreamerToast(View v) {
        showBuiltInToast(getResources().getString(R.string.message_streamer), Toast.LENGTH_SHORT);
    }

    public void showScoresToast(View v) {
        showBuiltInToast(getResources().getString(R.string.message_scores_app), Toast.LENGTH_SHORT);
    }

    public void showLibraryToast(View v) {
        showBuiltInToast(getResources().getString(R.string.message_library_app), Toast.LENGTH_SHORT);
    }

    public void showBuildItBiggerToast(View v) {
        showBuiltInToast(getResources().getString(R.string.message_build_app), Toast.LENGTH_SHORT);
    }

    public void showXyzReaderToast(View v) {
        showBuiltInToast(getResources().getString(R.string.message_xzy_reader_app), Toast.LENGTH_SHORT);
    }

    public void showMyOwnAppToast(View v) {
        showBuiltInToast(getResources().getString(R.string.message_capston_app), Toast.LENGTH_SHORT);
    }

    private void showBuiltInToast(CharSequence message, int duration) {
        Context context = getApplicationContext();
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }
}
