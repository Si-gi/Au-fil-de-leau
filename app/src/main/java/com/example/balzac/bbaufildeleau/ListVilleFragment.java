package com.example.balzac.bbaufildeleau;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.renderscript.Sampler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.ArrayList;

public class ListVilleFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView lv;
    ArrayList items;
    private OnFragmentInteractionListener mListener;
    JSONObject jsonResponse;
    ArrayAdapter<Ville> objAdapter;
    String ville;

    public ListVilleFragment() {
    }

    public static ListVilleFragment newInstance(String param1, String param2) {
        ListVilleFragment fragment = new ListVilleFragment();
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
        View v = inflater.inflate(R.layout.fragment_list_ville, container, false);
        InputStream is = getResources().openRawResource(R.raw.ville_data);
        Writer writer = new StringWriter();
        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        String villeData = writer.toString();
        lv = v.findViewById(R.id.list_ville);
        items = new ArrayList<Ville>();
        parseJSON(villeData);
        lv.setOnItemClickListener(this);

        return v;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void parseJSON(String leJson){
        try {
            jsonResponse = new JSONObject(leJson);
            JSONArray jsonArray = jsonResponse.getJSONArray("listes_villes");
            Ville currentFlux = null;
            for (int i = 0; i < jsonArray.length(); i++) {
                currentFlux = new Ville();
                JSONObject jsonObj = jsonArray.getJSONObject(i);
                String nom_ville = jsonObj.getString("ville");
                Integer nbPtInt = jsonObj.getInt("nb_point_interet");
                currentFlux.setNom(nom_ville);
                currentFlux.setNbPointInteret(nbPtInt);
                items.add(currentFlux);
            }
            ArrayAdapter<Ville> objAdapter = new VilleAdapter(getActivity(),R.layout.row_ville, items);
            lv.setAdapter(objAdapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView c = (TextView) view.findViewById(R.id.nom_ville);
        ville = c.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("la_ville", ville);
        bundle.putString("light_data", this.getArguments().getString("light_data"));
        AppCompatActivity activity = (AppCompatActivity) view.getContext();
        Fragment myFragment = new ListPointInteretFragment();
        myFragment.setArguments(bundle);
        activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_list_ville, myFragment).addToBackStack(null).commit();
    }
}
