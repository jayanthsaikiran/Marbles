package com.example.sudha.marbles;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;

public class Main2Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        TableLayout newTable = new TableLayout(getApplicationContext());
        int rowIndex = 0;

// First Row (3 buttons)
        TableRow newRow1 = new TableRow(getApplicationContext());
        newRow1.addView(new Button(getApplicationContext()), 0);
        newRow1.addView(new Button(getApplicationContext()), 1);
        newRow1.addView(new Button(getApplicationContext()), 2);
        newTable.addView(newRow1, rowIndex++);

// Second Row (2 buttons, last one spans 2 columns)
        TableRow newRow2 = new TableRow(getApplicationContext());
        newRow2.addView(new Button(getApplicationContext()), 0);
        Button newButton = new Button(getApplicationContext());

// Create the layout to span two columns
        TableRow.LayoutParams params = new TableRow.LayoutParams();
        params.span = 2;
        newRow2.addView(newButton, 1, params);

        newTable.addView(newRow2, rowIndex++);
        setContentView(newTable);
    }

}
