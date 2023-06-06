package com.example.balzac.bbaufildeleau;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class VilleAdapter extends ArrayAdapter<Ville> {
    private Activity activity;
    private List<Ville> items;
    private Ville objBean;
    private int row;
    public VilleAdapter(Activity act, int resource, List<Ville> arrayList) {
        super(act, resource, arrayList);
        this.activity = act;
        this.row = resource;
        this.items = arrayList;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);
            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if ((items == null) || ((position + 1) > items.size()))
            return view;
        objBean = items.get(position);
        holder.nom_ville = (TextView) view.findViewById(R.id.nom_ville);
        holder.nb_point_interet = (TextView) view.findViewById(R.id.nb_point_interet);
        if (holder.nom_ville != null && null != objBean.getNom()
                && objBean.getNom().trim().length() > 0) {
            holder.nom_ville.setText(objBean.getNom()); //idem
        }
        if (holder.nb_point_interet != null && 0 != objBean.getNbPointInteret()
                && objBean.getNbPointInteret() > 0) { //idem
            holder.nb_point_interet.setText(String.valueOf(objBean.getNbPointInteret())); //idem
        }
        return view;
    }
    public class ViewHolder {
        public TextView nom_ville, nb_point_interet;
    }
}
