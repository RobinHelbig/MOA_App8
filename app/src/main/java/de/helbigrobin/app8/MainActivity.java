package de.helbigrobin.app8;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    static String text_class;
    String text;
    Button button1, button2, button3;
    TextView textView1, textView2;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        button1 = (Button) findViewById(R.id.button1);
        button2 = (Button) findViewById(R.id.button2);
        button3 = (Button) findViewById(R.id.button3);
        textView1 = (TextView) findViewById(R.id.textView1);
        textView2 = (TextView) findViewById(R.id.textView2);
        editText = (EditText) findViewById(R.id.editText);

        editText.setOnEditorActionListener((v, actionId, event) -> {
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                String text = editText.getText().toString();
                textView1.setText(text);
                saveText(text);
                return true;
            }
            return false;
        });

        setContent(savedInstanceState);
        log();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle stateToBeSaved) {
        super.onSaveInstanceState(stateToBeSaved);

        stateToBeSaved.putBooleanArray("buttonStates", new boolean[]{button1.isEnabled(), button2.isEnabled(), button3.isEnabled()});
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setContent(savedInstanceState);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String newText = data.getStringExtra("newText");

        if(newText != null){
            textView2.setText(newText);
        }
    }

    void saveText(String text){
        MainActivity.text_class = text;
        this.text = text;
    }

    public void buttonGroupClick(View view){
        button1.setEnabled(true);
        button2.setEnabled(true);
        button3.setEnabled(true);
        int id = view.getId();
        if(id == R.id.button1) {
            button1.setEnabled(false);
        } else if(id ==R.id.button2) {
            button2.setEnabled(false);
        } else if(id == R.id.button3) {
            button3.setEnabled(false);
        }
    }

    public void secondActivityButtonClick(View view){
        String text = editText.getText().toString();
        textView1.setText(text);
        saveText(text);

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("text", text);
        startActivityForResult(intent, 1);
    }

    private void setContent(Bundle savedInstanceState){
        if (savedInstanceState != null && savedInstanceState.containsKey("buttonStates")){
            boolean[] buttonStates = savedInstanceState.getBooleanArray("buttonStates");
            button1.setEnabled(buttonStates[0]);
            button2.setEnabled(buttonStates[1]);
            button3.setEnabled(buttonStates[2]);
        } else {
            button1.setEnabled(false);
            button2.setEnabled(true);
            button3.setEnabled(true);
        }
    }

    protected void onStart() {
        super.onStart();
        log();
    }
    protected void onRestart() {
        super.onRestart();
        log();
    }
    protected void onResume() {
        super.onResume();
        log();
    }
    protected void onPause() {
        super.onPause();
        log();
    }
    protected void onStop() {
        super.onStop();
        log();
    }
    protected void onDestroy() {
        super.onDestroy();
        log();
    }

    void log() {
        Log.i("log", this.getClass().getSimpleName() + "." + Thread.currentThread().getStackTrace()[3].getMethodName());
    }
}