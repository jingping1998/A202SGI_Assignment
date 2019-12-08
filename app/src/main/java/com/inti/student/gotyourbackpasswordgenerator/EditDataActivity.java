package com.inti.student.gotyourbackpasswordgenerator;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class EditDataActivity extends AppCompatActivity {

    private Button Back_btn, Del_btn;
    private ImageButton Copy_btn;
    private TextView username_tv, pass_tv, Application_tv;

    DatabaseHelper mDatabaseHelper;

    private int selectedID;
    private String Application, Username, Password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);
        Del_btn = (Button) findViewById(R.id.del_btn);
        Back_btn = (Button) findViewById(R.id.back_btn);
        Copy_btn = (ImageButton) findViewById(R.id.copy_btn);
        Application_tv = (TextView) findViewById(R.id.ApplicationName2_tv);
        username_tv = (TextView) findViewById(R.id.UserName_tv);
        pass_tv = (TextView) findViewById(R.id.pass_tv);
        mDatabaseHelper = new DatabaseHelper(this);

        Intent receivedIntent = getIntent();

        selectedID = receivedIntent.getIntExtra("id", -1);

        Cursor data = mDatabaseHelper.getData();
        while (data.moveToNext()){
            if (data.getInt(0) == selectedID){
                Application = data.getString(1);
                Username = data.getString(2);
                Password = data.getString(3);
            }else{
                continue;
            }
        }
        Application_tv.setText(Application);
        username_tv.setText(Username);
        pass_tv.setText(Password);

        Del_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabaseHelper.deleteID(selectedID);
                startActivity(new Intent(EditDataActivity.this, MainActivity.class));
            }
        });
        Back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditDataActivity.this, MainActivity.class));
            }
        });
        Copy_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("password", Password);
                clipboard.setPrimaryClip(clip);
                toastMessage("Copied");
            }
        });

    }
    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
