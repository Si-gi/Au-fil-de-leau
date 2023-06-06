package com.example.balzac.bbaufildeleau;

import android.app.Activity;
import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class FavorisAdapter extends ArrayAdapter<PointInteret> {
    private Activity activity;
    private List<PointInteret> items;
    private PointInteret objBean;
    private int row;
    public FavorisAdapter(Activity act, int resource, List<PointInteret> arrayList) {
        super(act, resource, arrayList);
        this.activity = act;
        this.row = resource;
        this.items = arrayList;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view = convertView;
        FavorisAdapter.ViewHolder holder;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater)
                    activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(row, null);
            holder = new ViewHolder();
            view.setTag(holder);
        } else {
            holder = (FavorisAdapter.ViewHolder) view.getTag();
        }
        if ((items == null) || ((position + 1) > items.size()))
            return view;
        objBean = items.get(position);
        holder.elem_patri = (TextView) view.findViewById(R.id.elem_patri_fav);
        holder.comunne = (TextView) view.findViewById(R.id.comunne_fav);
        holder.ensem2 = (TextView) view.findViewById(R.id.ensem2_fav);
        holder.date_construc = (TextView) view.findViewById(R.id.date_construc_fav);
        holder.commentaire = (TextView) view.findViewById(R.id.commentaire_fav);
        holder.elem_princip = (TextView) view.findViewById(R.id.elem_princip_fav);
        holder.favorite = (TextView) view.findViewById(R.id.favorite_fav);
        holder.pontid = (TextView) view.findViewById(R.id.pont_id_fav);
        holder.locationX = (TextView) view.findViewById(R.id.location_X);
        holder.locationY = (TextView) view.findViewById(R.id.location_Y);
        holder.pictureLink = (TextView) view.findViewById(R.id.pictureLink);
        if (holder.elem_patri != null && null != objBean.getElemPatri()
                && objBean.getElemPatri().trim().length() > 0) {
            holder.elem_patri.setText(Html.fromHtml(objBean.getElemPatri()));
        }
        if (holder.comunne != null && null != objBean.getComunne()
                && objBean.getComunne().trim().length() > 0) {
            holder.comunne.setText(Html.fromHtml(objBean.getComunne()));
        }
        if (holder.ensem2 != null && null != objBean.getEnsem2()
                && objBean.getEnsem2().trim().length() > 0) {
            holder.ensem2.setText(Html.fromHtml(objBean.getEnsem2()));
        }
        if (holder.date_construc != null && null != objBean.getDateConstruc()
                && objBean.getDateConstruc().trim().length() > 0) {
            holder.date_construc.setText(Html.fromHtml(objBean.getDateConstruc()));
        }
        if (holder.commentaire != null && null != objBean.getCommentaire()
                && objBean.getCommentaire().trim().length() > 0) {
            holder.commentaire.setText(Html.fromHtml(objBean.getCommentaire()));
        }
        if (holder.elem_princip != null && null != objBean.getElemPrincip()
                && objBean.getElemPrincip().trim().length() > 0) {
            holder.elem_princip.setText(Html.fromHtml(objBean.getElemPrincip()));
        }
        if (holder.favorite != null && objBean.getElemPrincip().trim().length() > 0) {
            holder.favorite.setText(Html.fromHtml(String.valueOf(objBean.getFavorite())));
        }
        if (holder.pontid != null && objBean.getElemPrincip().trim().length() > 0) {
            holder.pontid.setText(Html.fromHtml(String.valueOf(objBean.getObjectid())));
        }
        if (holder.locationX != null && null != objBean.getLocationX()
                && objBean.getElemPrincip().trim().length() > 0) {
            holder.locationX.setText(Html.fromHtml(objBean.getLocationX()));
        }
        if (holder.locationY != null && null != objBean.getLocationY()
                && objBean.getElemPrincip().trim().length() > 0) {
            holder.locationY.setText(Html.fromHtml(objBean.getLocationY()));
        }
        if (holder.pictureLink != null && null != objBean.getPictureLink()
                && objBean.getElemPrincip().trim().length() > 0) {
            holder.pictureLink.setText(Html.fromHtml(objBean.getPictureLink()));
        }
        return view;
    }
    public class ViewHolder {
        public TextView elem_patri, comunne, ensem2, date_construc, commentaire, elem_princip, favorite, pontid, locationX, locationY, pictureLink;
    }
}
