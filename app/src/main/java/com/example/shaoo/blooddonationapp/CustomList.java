package com.example.shaoo.blooddonationapp;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by ehsan on 15-10-2017.
 */

public class CustomList extends SimpleAdapter {

    public LayoutInflater inflater = null;
    int res;
    private Context mContext;

    public CustomList(Context context, List<? extends Map<String, ?>> data, int resource, String[] from, int[] to) {
        super(context, data, resource, from, to);
        mContext = context;
        res = resource;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View vi = convertView;
        if (convertView == null)
            vi = inflater.inflate(res, null);

        HashMap<String, Object> data = (HashMap<String, Object>) getItem(position);
        TextView text = (TextView) vi.findViewById(R.id.name);
        String name = (String) data.get("name");
        text.setText(name);
        TextView text1 = (TextView) vi.findViewById(R.id.address);
        String city = (String) data.get("city");
        text1.setText(city);
        TextView text2 = (TextView) vi.findViewById(R.id.contact);
        String cntct = (String) data.get("contact");
        text2.setText(cntct);
        ImageView imageView = (ImageView) vi.findViewById(R.id.imageView);
        Bitmap image = (Bitmap) data.get("image");
        imageView.setImageBitmap(image);
        return vi;
    }
}