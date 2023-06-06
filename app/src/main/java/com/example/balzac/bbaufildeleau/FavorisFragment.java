package com.example.balzac.bbaufildeleau;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

public class FavorisFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView lv;
    ArrayList items;
    private ListPointInteretFragment.OnFragmentInteractionListener mListener;
    JSONObject jsonResponse;
    ArrayAdapter<PointInteret> objAdapter;
    Intent IntentDPI;
    String light_data;
    TextView nofavText;

    public FavorisFragment() {
    }

    public static FavorisFragment newInstance(String param1, String param2) {
        FavorisFragment fragment = new FavorisFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_favoris, container, false);
        light_data = this.getArguments().getString("light_data");
        lv = v.findViewById(R.id.list_fav);
        items = new ArrayList<PointInteret>();
        parseJSON(String.valueOf(light_data));
        lv.setOnItemClickListener(this);
        if (lv.getCount() == 0) {
            nofavText = v.findViewById(R.id.textView2);
            nofavText.setText("aucun point d'intérêt favoris :(");
        }
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void parseJSON(String leJson){
        try {
            jsonResponse = new JSONObject(leJson);
            JSONArray jsonArray = jsonResponse.getJSONArray("lists_pt_int");
            PointInteret currentFlux = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                currentFlux = new PointInteret();
                JSONObject jsonObj = jsonArray.getJSONObject(i);

                String recordid = jsonObj.getString("recordid");

                JSONObject jsonObj2 = jsonObj.getJSONObject("fields");

                String commune = jsonObj2.getString("commune");
                String ensem2 = jsonObj2.getString("ensem2");
                Integer objectId = jsonObj2.getInt("objectid");
                String commentaire = jsonObj2.getString("commentair");
                String date_construc = jsonObj2.getString("histo1");
                String elem_princ = jsonObj2.getString("elem_princ");
                String elem_patri = jsonObj2.getString("elem_patri");
                Integer favorite = jsonObj2.getInt("favorite");
                String locationX = jsonObj2.getString("location_X");
                String locationY = jsonObj2.getString("location_Y");
                String pictureLink = jsonObj2.getString("image");

                currentFlux.setRecordid(recordid);
                currentFlux.setComunne(commune);
                currentFlux.setEnsem2(ensem2);
                currentFlux.setObjectid(i);
                currentFlux.setCommentaire(commentaire);
                currentFlux.setDateConstruc(date_construc);
                currentFlux.setElemPrincip(elem_princ);
                currentFlux.setElemPatri(elem_patri);
                currentFlux.setFavorite(favorite);
                currentFlux.setLocationX(locationX);
                currentFlux.setLocationY(locationY);
                currentFlux.setPictureLink(pictureLink);
                if (favorite.equals(1)) {
                    items.add(currentFlux);
                }
            }
            ArrayAdapter<PointInteret> objAdapter = new PointInteretAdapter(getActivity(),R.layout.row_point_interet, items);
            lv.setAdapter(objAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView nom_pont = (TextView) view.findViewById(R.id.elem_patri);
        TextView comunne_pont = (TextView) view.findViewById(R.id.comunne);
        TextView liaison_pont = (TextView) view.findViewById(R.id.ensem2);
        TextView commentaire_pont = (TextView) view.findViewById(R.id.commentaire);
        TextView date_pont = (TextView) view.findViewById(R.id.date_construc);
        TextView materiaux_pont = (TextView) view.findViewById(R.id.elem_princip);
        TextView favoris_pont = (TextView) view.findViewById(R.id.favorite);
        TextView pont_id = (TextView) view.findViewById(R.id.pont_id);
        TextView locationXv = (TextView) view.findViewById(R.id.location_X);
        TextView locationYv = (TextView) view.findViewById(R.id.location_Y);
        TextView picture_link = (TextView) view.findViewById(R.id.pictureLink);
        IntentDPI = new Intent(getActivity(), DetailPointInteret.class);
        IntentDPI.putExtra("nom_pont", nom_pont.getText().toString());
        IntentDPI.putExtra("comunne_pont", comunne_pont.getText().toString());
        IntentDPI.putExtra("liaison_pont", liaison_pont.getText().toString());
        IntentDPI.putExtra("commentaire_pont", commentaire_pont.getText().toString());
        IntentDPI.putExtra("date_pont", date_pont.getText().toString());
        IntentDPI.putExtra("materiaux_pont", materiaux_pont.getText().toString());
        IntentDPI.putExtra("favoris_pont", favoris_pont.getText().toString());
        IntentDPI.putExtra("pont_id", pont_id.getText().toString());
        IntentDPI.putExtra("location_X", locationXv.getText().toString());
        IntentDPI.putExtra("location_Y", locationYv.getText().toString());
        IntentDPI.putExtra("picture_link", picture_link.getText().toString());
        startActivity(IntentDPI);
    }
}
