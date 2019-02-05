package com.example.sudha.marbles;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
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

import com.itextpdf.text.Document;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity {
    String dirpath = "";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TableLayout tableLayout;
    int count = 0;
    int tc = 0;
    ArrayList<ArrayList<EditText>> list;
    JSONObject parent;
    JSONArray main;
    ArrayList<EditText> editTexts = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = findViewById(R.id.tablelayout);

        parent = new JSONObject();

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
                addrow();

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
                main = new JSONArray();
                ArrayList<String> temp = new ArrayList<String>();
                for (EditText editText : editTexts) {
                    //Log.i("xyaa",editText.getText().toString());
                    temp.add(editText.getText().toString());
                }
                Log.i("xyaa", "length of temp" + temp.size());

                Log.i("xyab", temp.toString());

                int temp_table_count = 1;
                JSONArray newJArray = new JSONArray();

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
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        });
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

    private void addrow() {
        TableRow row = new TableRow(getApplicationContext());

        EditText sno = new EditText(getApplicationContext());
        sno.setText(Integer.toString(count));
        sno.setEnabled(false);
        sno.setGravity(Gravity.CENTER);
        sno.setBackground(getResources().getDrawable(R.drawable.border));

        EditText size1 = new EditText(getApplicationContext());
        size1.setText("S1");
        size1.setGravity(Gravity.CENTER);
        size1.setBackground(getResources().getDrawable(R.drawable.border));

        EditText size2 = new EditText(getApplicationContext());
        size2.setText("S2");
        size2.setGravity(Gravity.CENTER);
        size2.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less1_1 = new EditText(getApplicationContext());
        less1_1.setText("L11");
        less1_1.setGravity(Gravity.CENTER);
        less1_1.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less1_2 = new EditText(getApplicationContext());
        less1_2.setText("l12");
        less1_2.setGravity(Gravity.CENTER);
        less1_2.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less2 = new EditText(getApplicationContext());
        less2.setText("L2");
        less2.setGravity(Gravity.CENTER);
        less2.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less2_2 = new EditText(getApplicationContext());
        less2_2.setText("L2_2");
        less2_2.setGravity(Gravity.CENTER);
        less2_2.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less3 = new EditText(getApplicationContext());
        less3.setText("L3");
        less3.setGravity(Gravity.CENTER);
        less3.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less3_2 = new EditText(getApplicationContext());
        less3_2.setText("L3_2");
        less3_2.setGravity(Gravity.CENTER);
        less3_2.setBackground(getResources().getDrawable(R.drawable.border));

        EditText area = new EditText(getApplicationContext());
        area.setText("Area");
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

        Log.i("list", list.toString());

//        ScrollView body = findViewById(R.id.scroll);
//        body.setDrawingCacheEnabled(true);
//        body.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
//                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
//        body.layout(0, 0, body.getMeasuredWidth(), body.getMeasuredHeight());
//        body.buildDrawingCache();
//        Bitmap bm = Bitmap.createBitmap(body.getDrawingCache());
//        body.setDrawingCacheEnabled(false); // clear drawing cache
//        Intent share = new Intent(Intent.ACTION_SEND);
//        share.setType("image/jpg");
//
//        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
//        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
//        File f = new File(getExternalFilesDir(null).getAbsolutePath() + File.separator + "Certificate" + File.separator + "myCertificate.jpg");
//        try {
//
//
//            f.createNewFile();
//            FileOutputStream fo = new FileOutputStream(f);
//            fo.write(bytes.toByteArray());
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            Log.i("appi", "Error occured inside");
//        }
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


    public void imageToPDF() throws FileNotFoundException {
        try {
            Document document = new Document();
            dirpath = android.os.Environment.getExternalStorageDirectory().toString();
            PdfWriter.getInstance(document, new FileOutputStream(dirpath + "/NewPDF.pdf")); //  Change pdf's name.
            document.open();
            Image img = Image.getInstance(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            float scaler = ((document.getPageSize().getWidth() - document.leftMargin()
                    - document.rightMargin() - 0) / img.getWidth()) * 100;
            img.scalePercent(scaler);
            img.setAlignment(Image.ALIGN_CENTER | Image.ALIGN_TOP);
            document.add(img);
            document.close();
            Toast.makeText(this, "PDF Generated successfully!..", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {

        }
    }
}
