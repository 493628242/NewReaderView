package com.gray.newreaderview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gray.newreaderview.reader.draw.HorizontalMoveDraw;
import com.gray.newreaderview.reader.util.PageProperty;
import com.gray.newreaderview.reader.view.ReaderView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ReaderView reader = findViewById(R.id.readview);
        reader.setDraw(new HorizontalMoveDraw(PageProperty.getInstance(this)));
        reader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onClick", "onClick: ");
            }
        });
    }
}
