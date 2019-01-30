package com.example.sudha.marbles;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
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

public class MainActivity extends AppCompatActivity {

    String dirpath = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            addtable();
        } catch (IOException e) {
            Log.i("appi", "error occured");
            e.printStackTrace();
        }
        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TableLayout tableLayout = findViewById(R.id.tablelayout);
                TableRow row = new TableRow(getApplicationContext());

                TextView t = new TextView(getApplicationContext());
                t.setText("klsdfalsk");
                t.setPadding(10, 10, 10, 10);
                t.setBackground(getResources().getDrawable(R.drawable.border));
                row.addView(t);

                TextView t2 = new TextView(getApplicationContext());
                t2.setText("lksjlk");
                t2.setPadding(10, 10, 10, 10);
                row.addView(t2);

                tableLayout.addView(row);
            }
        });
    }

    public void addtable() throws IOException {
        TableLayout tableLayout = findViewById(R.id.tablelayout);
        TableRow row = new TableRow(getApplicationContext());

        TextView sno = new TextView(getApplicationContext());
        sno.setText("S.NO");
        sno.setBackground(getResources().getDrawable(R.drawable.border));

        TextView size = new TextView(getApplicationContext());
        size.setText("Size");
        size.setBackground(getResources().getDrawable(R.drawable.border));

        TextView less1 = new TextView(getApplicationContext());
        less1.setText("Less 1");
        less1.setBackground(getResources().getDrawable(R.drawable.border));

        TextView less2 = new TextView(getApplicationContext());
        less2.setText("Less 2");
        less2.setBackground(getResources().getDrawable(R.drawable.border));

        TextView less3 = new TextView(getApplicationContext());
        less3.setText("Less 3");
        less3.setBackground(getResources().getDrawable(R.drawable.border));

        TextView area = new TextView(getApplicationContext());
        area.setText("Area");
        area.setBackground(getResources().getDrawable(R.drawable.border));

        row.addView(sno);
        row.addView(size);
        row.addView(less1);
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
        DialogFragment newFragment = new MyDatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "date picker");
        Button b = v.findViewById(R.id.date);
        b.setText(((MyDatePickerFragment) newFragment).s);
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
