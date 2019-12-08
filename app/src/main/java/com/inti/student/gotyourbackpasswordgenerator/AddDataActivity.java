package com.inti.student.gotyourbackpasswordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import java.security.SecureRandom;
import java.util.Random;

public class AddDataActivity extends AppCompatActivity {

    private EditText applicationName_pt, userName_pt, PassWord_pt;
    private Button back_btn, save_btn, generate_btn;
    private String copy, application, userName, Password;
    private ImageButton copy_btn;
    private Spinner mSpinner;
    private static final Random RANDOM = new SecureRandom();
    private static final String ALPHABET = "0123456789QWERTYUIOPASDFGHJKLZXCVBNMqwertyuiopasdfghjklzxcvbnm";
    DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_data);

        applicationName_pt = (EditText) findViewById(R.id.applicationName_pt);
        userName_pt = (EditText) findViewById(R.id.userName_pt);
        PassWord_pt = (EditText) findViewById(R.id.password_pt);
        copy_btn = (ImageButton) findViewById(R.id.copy_btn);
        back_btn = (Button) findViewById(R.id.back_btn);
        save_btn = (Button) findViewById(R.id.Save_btn);
        generate_btn = (Button) findViewById(R.id.generate_btn);
        mSpinner = (Spinner) findViewById(R.id.spinner);
        mDatabaseHelper = new DatabaseHelper(this);

        copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                copy = PassWord_pt.getText().toString();
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("password", copy);
                clipboard.setPrimaryClip(clip);
                toastMessage("Copied");
            }
        });

        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddDataActivity.this, MainActivity.class));
            }
        });

        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                application = applicationName_pt.getText().toString();
                userName = userName_pt.getText().toString();
                Password = PassWord_pt.getText().toString();

                if(application.matches("")){
                    toastMessage("You did not enter a application name.");
                    return;
                }
                if(userName.matches("")){
                    toastMessage("You did not enter a username.");
                    return;
                }
                if(Password.matches("")){
                    toastMessage("You did not enter a password.");
                    return;
                }

                boolean insertData = mDatabaseHelper.addData(application, userName, Password);

                if (insertData){
                    toastMessage("Added");
                }else{
                    toastMessage("Something went wrong");
                }
                startActivity(new Intent(AddDataActivity.this, MainActivity.class));
            }
        });

        generate_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Password = generatePassword(Integer.parseInt(mSpinner.getSelectedItem().toString()));
                PassWord_pt.setText(Password);
            }
        });

    }
    private String generatePassword(int length){
        StringBuilder returnValue = new StringBuilder(length);

        for (int i = 0; i < length; i++){
            returnValue.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
        }
        return new String(returnValue);
    }

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
