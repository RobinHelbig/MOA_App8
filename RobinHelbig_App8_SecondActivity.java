package de.helbigrobin.app8;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;


/*
Beim "Kill-the-app Experiment" bleiben alle Texte bestehen
 */

public class SecondActivity extends AppCompatActivity {
    static String text_class;
    String text;
    TextViewModel textViewModel;
    GlobalState globalState;

    EditText editText;
    TextView textView1, textView2, textView3, textView4, textView5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        textViewModel = ViewModelProviders.of(this).get(TextViewModel.class);
        globalState = ((GlobalState)getApplication());

        editText = findViewById(R.id.secondView_editText);
        textView1 = findViewById(R.id.secondView_textView1);
        textView2 = findViewById(R.id.secondView_textView2);
        textView3 = findViewById(R.id.secondView_textView3);
        textView4 = findViewById(R.id.secondView_textView4);
        textView5 = findViewById(R.id.secondView_textView5);

        Intent intent = getIntent();
        String text = intent.getStringExtra("text");
        if(text != null){
            editText.setText(text);
            saveText(text); //Das ergibt zwar nicht viel Sinn das zu speichern und direkt wieder zu laden, aber in der MainActivity geht das ja nicht wirklich
            loadText();
        }

        log();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle stateToBeSaved) {
        super.onSaveInstanceState(stateToBeSaved);

        stateToBeSaved.putString("text", this.text);
    }

    @Override
    public void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState.containsKey("text")){
            textView5.setText(savedInstanceState.getString("text"));
        } else {
            textView5.setText("Leer");
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        intent.putExtra("newText", editText.getText().toString());
        setResult(RESULT_OK, intent);
        finish();
    }

    private void saveText(String text){
        /*
        Ich speichere den Text mit den fünf Möglichkeiten in der SecondActivity, da man die Klassenvariable, das Viewmodel und das Bundle aus "onRestoreInstanceState" nicht aus der MainActivity auslesen kann.
        Application GlobalState und Klassenvariable könnte man in der MainActivity setzen, aber das wollte ich nicht, damit das alles einheitlich hier gemacht wird.
         */

        //Klassenvariable
        SecondActivity.text_class = text;

        //Membervariable
        this.text = text;

        //Application Objekt
        globalState.text = text;

        //Viemodel
        textViewModel.text = text;
    }

    private void loadText(){
        textView1.setText(SecondActivity.text_class != null ?  SecondActivity.text_class : "Leer");
        textView2.setText(this.text != null ? this.text : "Leer");
        textView3.setText(globalState.text != null ? globalState.text : "Leer");
        textView4.setText(textViewModel.text != null ? textViewModel.text : "Leer");
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
