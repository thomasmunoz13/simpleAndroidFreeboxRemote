package fr.thomasmunoz.myapplication;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    final Context context = this;
    private String remoteCode;
    private FreeboxTvController freeboxTvController;
    public static final String PREFS_NAME = "FreeboxRemotePrefs";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        freeboxTvController = new FreeboxTvController();

        // SharePreference Loading ...
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String remoteCode = settings.getString("remoteCode", "");

        if(remoteCode == ""){
            displayPrompt();
        } else {
            this.remoteCode = remoteCode;
            freeboxTvController.setControllerId(this.remoteCode);
        }
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

    @Override
    protected void onStop(){
        super.onStop();

        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("remoteCode", remoteCode);

        editor.commit();
    }

    public void changeChannel(View v){
        Button button = (Button) v;
        freeboxTvController.changeChannel(button.getText().toString());
    }

    public void nextChannel(View v){
        freeboxTvController.next();
    }


    public void previousChannel(View v){
        freeboxTvController.previous();
    }

    private void displayPrompt(){
        // We get prompt.xml view
        LayoutInflater li = LayoutInflater.from(context);
        View promptsView = li.inflate(R.layout.prompt, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                remoteCode = userInput.getText().toString();
                                freeboxTvController.setControllerId(remoteCode);
                            }
                        });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();
    }
}
