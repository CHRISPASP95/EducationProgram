package com.example.christospaspalieris.educationprogram;

import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.igalata.bubblepicker.BubblePickerListener;
//import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.adapter.BubblePickerAdapter;
import com.igalata.bubblepicker.model.BubbleGradient;
import com.igalata.bubblepicker.model.PickerItem;
import com.igalata.bubblepicker.rendering.BubblePicker;



import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;




public class EducationActivity extends AppCompatActivity {

    private final static String TAG = "EducationActivity";

    String[] name={
            "Δεκαδικοί",
            "Ρίζες",
            "Εξισώσεις",
            "Κλάσματα",
            "Γκάρυ"
    };


    int[] images = {
            R.drawable.calculator,
            R.drawable.edu_icon,
            R.drawable.ruler_pencil,
            R.drawable.portion,
            R.drawable.sample_1
    };

    int[] colors = {
            Color.parseColor("#1A237E"),
            Color.parseColor("#990099"),
            Color.parseColor("#ff9900"),
            Color.parseColor("#00ccff"),
            Color.parseColor("#990000")
    };


    BubblePicker picker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_education);

        //bubblePicker = (BubblePicker)findViewById(picker);
        //ArrayList<PickerItem> listItems = new ArrayList<>();

        final String[] titles = getResources().getStringArray(R.array.subjects);
        final TypedArray colors = getResources().obtainTypedArray(R.array.colors);
        final TypedArray images = getResources().obtainTypedArray(R.array.images);

        picker = (BubblePicker)findViewById(R.id.picker);
        //picker.getBackground().setColorFilter(Color.parseColor("#00ff00"), PorterDuff.Mode.DARKEN);
        /*ArrayList<PickerItem> listItems = new ArrayList<>();

        for(int i = 0;i<name.length;i++)
        {
            PickerItem item = new PickerItem(name[i], colors[i],Color.WHITE,getDrawable(images[i]));
            listItems.add(item);
        }

        picker.setItems(listItems);*/
        picker.setCenterImmediately(true);
        picker.setBubbleSize(20);

        picker.setAdapter(new BubblePickerAdapter() {
            @Override
            public int getTotalCount() {
                return titles.length;
            }

            @NotNull
            @Override
            public PickerItem getItem(int position) {
                PickerItem item = new PickerItem();
                item.setTitle(titles[position]);
//                item.setGradient(new BubbleGradient(colors.getColor((position * 2) % 8, 0),
//                        colors.getColor((position * 2) % 8 + 1, 0), BubbleGradient.VERTICAL));
                item.setColor(colors.getResourceId(position,0));
                //item.setIcon(ContextCompat.getDrawable(EducationActivity.this, images.getResourceId(position, 0)));
                //item.setIconOnTop(true);

                item.setColor(colors.getInt(position,0));

                item.setTypeface(Typeface.SERIF);
                item.setTextColor(ContextCompat.getColor(EducationActivity.this, android.R.color.white));
                item.setBackgroundImage(ContextCompat.getDrawable(EducationActivity.this, images.getResourceId(position, 0)));
                return item;
            }
        });


        picker.setListener(new BubblePickerListener() {
            @Override
            public void onBubbleSelected(@NotNull PickerItem item) {

            }

            @Override
            public void onBubbleDeselected(@NotNull PickerItem item) {



                //if(item.isSelected())
                //{
                    if(item.getTitle() == "Δεκαδικοί")
                        //Toast.makeText(EducationActivity.this, "Mpika sto if ", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(getApplicationContext(),TheoryActivity.class));
                    openOptionsMenu();

                //}
            }
        });


    }

    @Override
    public void openOptionsMenu() {
        super.openOptionsMenu();
        Toast.makeText(EducationActivity.this, "Mpika sto if ", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Toast.makeText(EducationActivity.this, "Mpika sto if ", Toast.LENGTH_SHORT).show();
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);



    }

    @Override
    protected void onPause() {
        super.onPause();
        picker.onPause();
    }
}
