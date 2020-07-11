package com.josamar.marvelcharacters.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.josamar.marvelcharacters.R;
import com.josamar.marvelcharacters.model.Information;

import java.util.List;

public class Adaptador extends ArrayAdapter<Information> {
    private List<Information> miList;
    private Context mContext;
    private int resourceLayout;

    public Adaptador(Context context, int resource, List<Information> objects) {
        super(context, resource, objects);
        this.miList = objects;
        this.mContext=context;
        this.resourceLayout = resource;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;

        view = LayoutInflater.from(mContext).inflate(resourceLayout,null);

        Information model = miList.get(position);

        ImageView img = view.findViewById(R.id.imageItem);
        img.setImageBitmap(model.getImage());

        TextView tx = view.findViewById(R.id.txNameItem);
        tx.setText(model.getName());

        return view;
    }
}
