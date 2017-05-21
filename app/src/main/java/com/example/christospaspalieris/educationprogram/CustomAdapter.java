package com.example.christospaspalieris.educationprogram;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by peira on 21-May-17.
 */

public class CustomAdapter extends ArrayAdapter<String> {

    CustomAdapter(Context context, String[] resources){
        super(context,R.layout.custom_row,resources);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());
        View customView = inflater.inflate(R.layout.custom_row,parent,false);

        String singleItem = getItem(position);
        TextView buckyText = (TextView)customView.findViewById(R.id.buckysText);
        ImageView buckysImage = (ImageView) customView.findViewById(R.id.buckysImage);

        buckyText.setText(singleItem);
        buckysImage.setImageResource(R.drawable.calculator);
        return customView;





    }
}
