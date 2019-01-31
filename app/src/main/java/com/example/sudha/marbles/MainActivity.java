package com.example.sudha.marbles;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TabLayout;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    String dirpath = "";
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    TableLayout tableLayout;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tableLayout = findViewById(R.id.tablelayout);

        addtable();

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
                try {
                    ScrollView s = findViewById(R.id.scroll);
                    s.fullScroll(ScrollView.FOCUS_DOWN);
                    count++;
                    addrow();

                } catch (IOException e) {
                    e.printStackTrace();
                }
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
    }

    private void addtable() {
        TableRow row = new TableRow(getApplicationContext());


        TableRow.LayoutParams param = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, (float) 1.0);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, (float) 0.5);

        TextView sno = new TextView(getApplicationContext());
        sno.setText("S.NO");
        sno.setGravity(Gravity.CENTER);
        sno.setLayoutParams(params);
        sno.setBackground(getResources().getDrawable(R.drawable.border));

        TextView size = new TextView(getApplicationContext());
        size.setText("Size");
        size.setGravity(Gravity.CENTER);
        TableRow.LayoutParams params2 = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, (float) 2.0);

        size.setLayoutParams(params2);
        size.setBackground(getResources().getDrawable(R.drawable.border));

        TextView less1 = new TextView(getApplicationContext());
        less1.setText("Less1");
        less1.setGravity(Gravity.CENTER);
        less1.setLayoutParams(param);
        less1.setBackground(getResources().getDrawable(R.drawable.border));

        TextView less2 = new TextView(getApplicationContext());
        less2.setText("Less2");
        less2.setGravity(Gravity.CENTER);
        less2.setLayoutParams(param);
        less2.setBackground(getResources().getDrawable(R.drawable.border));

        TextView less3 = new TextView(getApplicationContext());
        less3.setText("Less3");
        less3.setGravity(Gravity.CENTER);
        less3.setLayoutParams(param);
        less3.setBackground(getResources().getDrawable(R.drawable.border));

        TextView area = new TextView(getApplicationContext());
        area.setText("Area");
        area.setGravity(Gravity.CENTER);
        area.setLayoutParams(param);
        area.setBackground(getResources().getDrawable(R.drawable.border));

        row.addView(sno);
        row.addView(size);
        row.addView(less1);
        row.addView(less2);
        row.addView(less3);
        row.addView(area);

        tableLayout.addView(row);
    }

    private void addrow() throws IOException {
        TableRow row = new TableRow(getApplicationContext());


        TableRow.LayoutParams param = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, (float) 1.0);
        TableRow.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.MATCH_PARENT, (float) 0.5);

        TextView sno = new TextView(getApplicationContext());
        sno.setText("S.NO");
        sno.measure(0, 0);
        sno.getMeasuredHeight();
        sno.getMeasuredWidth();
        sno.setGravity(Gravity.CENTER);
        sno.setLayoutParams(params);
        sno.setBackground(getResources().getDrawable(R.drawable.border));

        EditText size1 = new EditText(getApplicationContext());
        size1.setText("Size1");
        size1.setGravity(Gravity.CENTER);
        size1.setLayoutParams(param);
        size1.setHeight(sno.getMeasuredHeight());
        size1.setWidth(sno.getMeasuredWidth());
        size1.setBackground(getResources().getDrawable(R.drawable.border));

        EditText size2 = new EditText(getApplicationContext());
        size2.setText("Size2");
        size2.setGravity(Gravity.CENTER);
        size2.setLayoutParams(param);
        size2.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less1_1 = new EditText(getApplicationContext());
        less1_1.setText("Less1_1");
        less1_1.setGravity(Gravity.CENTER);
        less1_1.setLayoutParams(param);
        less1_1.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less1_2 = new EditText(getApplicationContext());
        less1_2.setText("less1_2");
        less1_2.setGravity(Gravity.CENTER);

        less1_2.setLayoutParams(param);
        less1_2.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less2 = new EditText(getApplicationContext());
        less2.setText("Less2");
        less2.setGravity(Gravity.CENTER);
        less2.setLayoutParams(param);
        less2.setBackground(getResources().getDrawable(R.drawable.border));

        EditText less3 = new EditText(getApplicationContext());
        less3.setText("Less3");
        less3.setGravity(Gravity.CENTER);
        less3.setLayoutParams(param);
        less3.setBackground(getResources().getDrawable(R.drawable.border));

        TextView area = new TextView(getApplicationContext());
        area.setText("Area");
        area.setGravity(Gravity.CENTER);
        area.setLayoutParams(param);
        area.setBackground(getResources().getDrawable(R.drawable.border));

        row.addView(sno);
        row.addView(size1);
        row.addView(size2);
        row.addView(less1_1);
        row.addView(less1_2);
        row.addView(less2);
        row.addView(less3);
        row.addView(area);

        tableLayout.addView(row);

        ScrollView body = findViewById(R.id.scroll);
        body.setDrawingCacheEnabled(true);
        body.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        body.layout(0, 0, body.getMeasuredWidth(), body.getMeasuredHeight());
        body.buildDrawingCache();
        Bitmap bm = Bitmap.createBitmap(body.getDrawingCache());
        body.setDrawingCacheEnabled(false); // clear drawing cache
        Intent share = new Intent(Intent.ACTION_SEND);
        share.setType("image/jpg");

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File f = new File(getExternalFilesDir(null).getAbsolutePath() + File.separator + "Certificate" + File.separator + "myCertificate.jpg");
        try {


            f.createNewFile();
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());

        } catch (Exception e) {
            e.printStackTrace();
            Log.i("appi", "Error occured inside");
        }
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
