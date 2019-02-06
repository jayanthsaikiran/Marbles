package com.example.sudha.marbles;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    private static final int STORAGE_PERMISSION_CODE = 1;
    String dirpath = "";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TableLayout tableLayout;
    int count = 0;
    int tc = 0;
    ArrayList<ArrayList<EditText>> list;
    JSONObject parent;
    JSONArray main;
    ArrayList<EditText> editTexts = new ArrayList<>();
    Data d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = findViewById(R.id.tablelayout);

        parent = new JSONObject();
        readFromFile();

        addtable();
        list = new ArrayList<ArrayList<EditText>>();
        Button b2 = findViewById(R.id.button2);
        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addtable();
            }
        });

        final Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ScrollView s = findViewById(R.id.scroll);
                s.fullScroll(ScrollView.FOCUS_DOWN);
                count++;
                d = new Data("sno","s1","s2","l11","l12","l21","l22","l31","l32","area");

                addrow(d);

            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d("app", "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                Button b1 = findViewById(R.id.date);
                b1.setText(date);
            }
        };


        Button save = findViewById(R.id.save);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermission();
                main = new JSONArray();
                ArrayList<String> temp = new ArrayList<String>();
                for (EditText editText : editTexts) {
                    //Log.i("xyaa",editText.getText().toString());
                    temp.add(editText.getText().toString());
                }
                Log.i("xyaa", "length of temp" + temp.size());

                Log.i("xyab", temp.toString());

                int temp_table_count = 1;

                for (int i = 0; i < temp.size() && i + 10 <= temp.size(); ) {
                    if (temp.get(i).contains("Total")) {
                        temp_table_count += 1;
                        Log.i("xyaa", "Reached " + Integer.toString(i) + " " + temp.get(i));
                        i+=1;
                        main = new JSONArray();

                        JSONObject item = new JSONObject();
                        try {
                            item.put("SNO", temp.get(i));
                            item.put("size1", temp.get(i + 1));
                            item.put("size2", temp.get(i + 2));
                            item.put("less1_1", temp.get(i + 3));
                            item.put("less1_2", temp.get(i + 4));
                            item.put("less2_1", temp.get(i + 5));
                            item.put("less2_2", temp.get(i + 6));
                            item.put("less3_1", temp.get(i + 7));
                            item.put("less3_2", temp.get(i + 8));
                            item.put("area", temp.get(i + 9));
                            i += 10;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        main.put(item);

                        try {
                            //Log.i("xyaa",main.toString(4));
                            parent.put("table" + Integer.toString(temp_table_count), main);
                            Log.i("xyaa","Reached here"+Integer.toString(i)+" "+temp.get(i));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
//                        Log.i("xyaa", "inside" + Integer.toString(i) + " " + temp.get(i));
                        i = check(i, temp);
                        try {
                            parent.put("table" + Integer.toString(temp_table_count), main);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                try {
                    Log.i("xyaa", parent.toString(4));
                    writeToFile(parent.toString());
                    readFromFile();

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });

        Button calculate = findViewById(R.id.calculate);
        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<String> temp = new ArrayList<String>();
                for (EditText editText : editTexts) {
                    //Log.i("xyaa",editText.getText().toString());
                    temp.add(editText.getText().toString());
                }
                Log.i("xyaa", "length of temp" + temp.size());
                int temp_table_count = 1;
                main = new JSONArray();

                for (int i = 0; i < temp.size() && i + 10 <= temp.size(); ) {
                    if (temp.get(i).contains("Total")) {
                        temp_table_count += 1;
                        Log.i("xyaa", "Reached " + Integer.toString(i) + " " + temp.get(i));
                        i+=1;
                        JSONObject item = new JSONObject();
                        try {
                            item.put("SNO", temp.get(i));
                            item.put("size1", temp.get(i + 1));
                            item.put("size2", temp.get(i + 2));
                            item.put("less1_1", temp.get(i + 3));
                            item.put("less1_2", temp.get(i + 4));
                            item.put("less2_1", temp.get(i + 5));
                            item.put("less2_2", temp.get(i + 6));
                            item.put("less3_1", temp.get(i + 7));
                            item.put("less3_2", temp.get(i + 8));
                            item.put("area", temp.get(i + 9));
                            i += 10;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        main.put(item);

                        try {
                            //Log.i("xyaa",main.toString(4));
                            parent.put("table" + Integer.toString(temp_table_count), main);
                            Log.i("xyaa","Reached here"+Integer.toString(i)+" "+temp.get(i));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    } else {
//                        Log.i("xyaa", "inside" + Integer.toString(i) + " " + temp.get(i));
                        i = check(i, temp);
                        try {
                            parent.put("table" + Integer.toString(temp_table_count), main);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

            }
        });
    }

    private void requestPermission() {

        if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                new AlertDialog.Builder(this)
                        .setTitle("Permission needed")
                        .setMessage("This permission is needed to save data")
                        .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                ActivityCompat.requestPermissions(MainActivity.this,
                                        new String[] {Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
                            }
                        })
                        .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        })
                        .create().show();

            } else {
                ActivityCompat.requestPermissions(this,
                        new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
            }
        }
        else {
            Log.i("xyaa","Permission granted");
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == STORAGE_PERMISSION_CODE)  {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "TABLE NOT SAVED", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private int check(int i, ArrayList<String> temp) {
        if (i == 0|| i<temp.size() ) {
            Log.i("xyaa", "Running inside" + i + " " + temp.get(i));
            JSONObject item = new JSONObject();
            try {
                item.put("SNO", temp.get(i));
                item.put("size1", temp.get(i + 1));
                item.put("size2", temp.get(i + 2));
                item.put("less1_1", temp.get(i + 3));
                item.put("less1_2", temp.get(i + 4));
                item.put("less2_1", temp.get(i + 5));
                item.put("less2_2", temp.get(i + 6));
                item.put("less3_1", temp.get(i + 7));
                item.put("less3_2", temp.get(i + 8));
                item.put("area", temp.get(i + 9));
                i += 10;
            } catch (JSONException e) {
                e.printStackTrace();
            }
            main.put(item);
        }
        return i;
    }

    private void writeToFile(String data) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(openFileOutput("config.txt", Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("xyae", "File write failed: " + e.toString());
        }
    }

    private String readFromFile() {

        String ret = "";
        requestPermission();
        try {
            InputStream inputStream = getApplicationContext().openFileInput("config.txt");

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
                Log.i("xyaa",ret);
                JSONObject jarray = new JSONObject(ret);
                Log.i("xyaa","The json format is "+jarray.toString(3));
                updatetable(jarray);
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return ret;
    }

    private void updatetable(JSONObject jsonObject) throws JSONException {

        JSONArray keys = jsonObject.names ();

        for (int i = 0; i < keys.length (); ++i) {

            String key = keys.getString (i); // Here's your key

            Log.i("xyj",jsonObject.getString(key));
            JSONArray temp_array = new JSONArray(jsonObject.getString(key));

            addtable();
            for(int j=0;j<temp_array.length();j++) {
                JSONObject o = (JSONObject) temp_array.get(j);

                d = new Data((String) o.get("SNO"),(String) o.get("size1"),(String) o.get("size2"),(String) o.get("less1_1"),(String) o.get("less1_2"),(String) o.get("less2_1"),(String) o.get("less2_2"),(String) o.get("less3_1"),(String) o.get("less3_2"),(String) o.get("area"));

                addrow(d);
            }
            Log.i("xyja","Reached");
        }
    }

    private void addtable() {
        TableRow row = new TableRow(getApplicationContext());

        tc++;
        count = 0;

        TextView sno = new TextView(getApplicationContext());
        sno.setText("S.NO");
        sno.setGravity(Gravity.CENTER);

        sno.setBackground(getResources().getDrawable(R.drawable.border));

        TextView size = new TextView(getApplicationContext());
        size.setText("Size");
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 2;
        size.setLayoutParams(params);
        size.setGravity(Gravity.CENTER);
        size.setBackground(getResources().getDrawable(R.drawable.border));

        TextView less1 = new TextView(getApplicationContext());
        less1.setText("Less1");
        less1.setLayoutParams(params);
        less1.setGravity(Gravity.CENTER);
        less1.setBackground(getResources().getDrawable(R.drawable.border));

        TextView less2 = new TextView(getApplicationContext());
        less2.setText("Less2");
        less2.setLayoutParams(params);
        less2.setGravity(Gravity.CENTER);
        less2.setBackground(getResources().getDrawable(R.drawable.border));

        TextView less3 = new TextView(getApplicationContext());
        less3.setText("Less3");
        less3.setLayoutParams(params);
        less3.setGravity(Gravity.CENTER);
        less3.setBackground(getResources().getDrawable(R.drawable.border));

        TextView area = new TextView(getApplicationContext());
        area.setText("Area");
        area.setGravity(Gravity.CENTER);
        area.setBackground(getResources().getDrawable(R.drawable.border));

        row.addView(sno);
        row.addView(size);
        row.addView(less1);
        row.addView(less2);
        row.addView(less3);
        row.addView(area);

        if (tc > 1) {
            TableRow row2 = new TableRow(getApplicationContext());

            EditText textView = new EditText(getApplicationContext());
            textView.setEnabled(false);
            textView.setText("Total");

            row2.addView(textView);
            editTexts.add(textView);
            tableLayout.addView(row2);

        }

        tableLayout.addView(row);


    }

    private void addrow(Data d) {
        TableRow row = new TableRow(getApplicationContext());

        EditText sno = new EditText(getApplicationContext());
        sno.setText(d.getSno());
        sno.setEnabled(false);
        sno.setGravity(Gravity.CENTER);
        sno.setBackground(getResources().getDrawable(R.drawable.border));

        EditText size1 = new EditText(getApplicationContext());
        size1.setText(d.getS1());
        size1.setGravity(Gravity.CENTER);
        size1.setBackground(getResources().getDrawable(R.drawable.border));

        EditText size2 = new EditText(getApplicationContext());
        size2.setText(d.getS2());
        size2.setGravity(Gravity.CENTER);
        size2.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less1_1 = new EditText(getApplicationContext());
        less1_1.setText(d.getL1_1());
        less1_1.setGravity(Gravity.CENTER);
        less1_1.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less1_2 = new EditText(getApplicationContext());
        less1_2.setText(d.getL1_2());
        less1_2.setGravity(Gravity.CENTER);
        less1_2.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less2 = new EditText(getApplicationContext());
        less2.setText(d.getL2_1());
        less2.setGravity(Gravity.CENTER);
        less2.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less2_2 = new EditText(getApplicationContext());
        less2_2.setText(d.getL2_2());
        less2_2.setGravity(Gravity.CENTER);
        less2_2.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less3 = new EditText(getApplicationContext());
        less3.setText(d.getL3_1());
        less3.setGravity(Gravity.CENTER);
        less3.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less3_2 = new EditText(getApplicationContext());
        less3_2.setText(d.getL3_2());
        less3_2.setGravity(Gravity.CENTER);
        less3_2.setBackground(getResources().getDrawable(R.drawable.border));

        EditText area = new EditText(getApplicationContext());
        area.setText(d.getArea());
        area.setGravity(Gravity.CENTER);
        area.setEnabled(false);
        area.setBackground(getResources().getDrawable(R.drawable.border));

        editTexts.add(sno);
        editTexts.add(size1);
        editTexts.add(size2);
        editTexts.add(less1_1);
        editTexts.add(less1_2);
        editTexts.add(less2);
        editTexts.add(less2_2);
        editTexts.add(less3);
        editTexts.add(less3_2);
        editTexts.add(area);

        Log.i("xyaa", editTexts.get(0).toString());

        row.addView(sno);
        row.addView(size1);
        row.addView(size2);
        row.addView(less1_1);
        row.addView(less1_2);
        row.addView(less2_2);
        row.addView(less2);
        row.addView(less3);
        row.addView(less3_2);
        row.addView(area);

        tableLayout.addView(row);


    }

    public void showDatePicker(View v) {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                MainActivity.this,
                android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                mDateSetListener,
                year, month, day);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();

    }
}
