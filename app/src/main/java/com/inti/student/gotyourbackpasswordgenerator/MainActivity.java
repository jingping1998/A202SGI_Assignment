 package com.inti.student.gotyourbackpasswordgenerator;

 import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

 public class MainActivity extends AppCompatActivity {
     private static final String TAG = "ListDataActivity";

     DatabaseHelper mDatabaseHelper;
     private SimpleAdapter sa;
     private ListView mListView;
     private Button create_btn;

     @Override
     protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mListView = (ListView) findViewById(R.id.main_list);
        mDatabaseHelper = new DatabaseHelper(this);
        create_btn = (Button) findViewById(R.id.btn_create);

        populateListView();
     }

     private void populateListView() {
         Log.d(TAG, "PopulateListView: Displaying data in the ListView.");

         Cursor data = mDatabaseHelper.getData();
         HashMap<String, String> list = new HashMap<>();
         while(data.moveToNext()){
             list.put(data.getString(1), data.getString(2));
         }

         List<HashMap<String, String>> ListItems = new ArrayList<>();
         SimpleAdapter adapter = new SimpleAdapter(this, ListItems, R.layout.twolines,
                 new String[]{"First Line", "Second Line"},
                 new int[]{R.id.line_a, R.id.line_b});

         Iterator it = list.entrySet().iterator();
         while (it.hasNext()){
             HashMap<String, String> resultMap = new HashMap<>();
             Map.Entry pair = (Map.Entry)it.next();
             resultMap.put("First Line", pair.getKey().toString());
             resultMap.put("Second Line", pair.getValue().toString());
             ListItems.add(resultMap);
         }

         mListView.setAdapter(adapter);

         mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
             @Override
             public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                 String v1;
                 v1 = parent.getItemAtPosition(position).toString();
                 String[] words = v1.split(",");
                 String[] words2 = words[0].split("=");
                 String[] words3 = words[1].split("=");
                 words3[1] = words3[1].substring(0, words3[1].length() -1);

                 Cursor data = mDatabaseHelper.getData();
                 int itemID = -1;
                 while (data.moveToNext()){
                     if (data.getString(1).equals(words3[1]) && data.getString(2).equals(words2[1])){
                         itemID = data.getInt(0);
                     }else{
                         continue;
                     }
                 }

                 if(itemID > -1){
                     Log.d(TAG, "onItemClick: The ID is: " + itemID);
                     Intent editScreenIntent = new Intent(MainActivity.this, EditDataActivity.class);
                     editScreenIntent.putExtra("id", itemID);
                     startActivity(editScreenIntent);
                 }else{
                     toastMessage("No ID associated with that name");
                 }

             }
         });

         create_btn.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 startActivity(new Intent(MainActivity.this, AddDataActivity.class));
             }
         });
     }



     private void toastMessage(String message){
         Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
     }
 }
