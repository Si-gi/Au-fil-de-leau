package com.example.balzac.bbaufildeleau;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DetailPointInteret extends AppCompatActivity implements View.OnClickListener {
    TextView nomdupont;
    TextView communepont;
    TextView materiauxpont;
    TextView liaisonpont;
    TextView commentairepont;
    TextView datepont;
    ImageView favpont;
    ImageView locate;
    ImageView photopont;
    String nom_pont;
    String comunne_pont;
    String materiaux_pont;
    String liaison_pont;
    String commentaire_pont;
    String date_pont;
    String favoris_pont;
    String pont_id;
    String location_X;
    String location_Y;
    String picture_link;
    FileOutputStream outputStream;

    Integer REQUEST_CAMERA = 0, SELECT_FILE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_point_interet);
        pont_id = getIntent().getStringExtra("pont_id");

        nom_pont = getIntent().getStringExtra("nom_pont");
        nomdupont = findViewById(R.id.elem_patri_final);
        nomdupont.setText(nom_pont);

        comunne_pont = getIntent().getStringExtra("comunne_pont");
        communepont = findViewById(R.id.comunne_final);
        communepont.setText("Situation: "+comunne_pont);

        materiaux_pont = getIntent().getStringExtra("materiaux_pont");
        materiauxpont = findViewById(R.id.materiaux_final);
        materiauxpont.setText("Matériaux: "+materiaux_pont);

        liaison_pont = getIntent().getStringExtra("liaison_pont");
        liaisonpont = findViewById(R.id.liaison_final);
        liaisonpont.setText("Liaison: "+liaison_pont);

        commentaire_pont = getIntent().getStringExtra("commentaire_pont");
        commentairepont = findViewById(R.id.commentaire_final);
        commentairepont.setText("Commentaire: "+commentaire_pont);

        date_pont = getIntent().getStringExtra("date_pont");
        datepont = findViewById(R.id.date_final);
        datepont.setText("Date: "+date_pont);

        favoris_pont = getIntent().getStringExtra("favoris_pont");
        favpont = findViewById(R.id.imgfavpont);
        if (favoris_pont.equals("1")){
            favpont.setImageResource(R.drawable.ic_menu_fav);
        }
        favpont.setOnClickListener(this);

        location_X = getIntent().getStringExtra("location_X");
        location_Y = getIntent().getStringExtra("location_Y");
        locate = findViewById(R.id.imgopenmap);
        locate.setOnClickListener(this);

        picture_link = getIntent().getStringExtra("picture_link");
        photopont = findViewById(R.id.picturePont);
        if (picture_link.isEmpty()){
            photopont.setImageResource(R.drawable.ic_no_img);
        }
        else if (!picture_link.isEmpty()){
            Bitmap bmImg = BitmapFactory.decodeFile(picture_link);
            photopont.setImageBitmap(bmImg);
        }
        photopont.setOnClickListener(this);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.imgfavpont:
                if (favoris_pont.equals("0")){
                    try {
                        addfav();
                        Snackbar snackbar = Snackbar
                                .make(v, "Ajouté au favoris !", Snackbar.LENGTH_LONG);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(R.color.main_blue);
                        snackbar.setAction("annuler", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    rmfav();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        snackbar.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                else if (favoris_pont.equals("1")) {
                    try {
                        rmfav();
                        Snackbar snackbar = Snackbar
                                .make(v, "Supprimer des favoris !", Snackbar.LENGTH_LONG);
                        View snackBarView = snackbar.getView();
                        snackBarView.setBackgroundColor(R.color.main_blue);
                        snackbar.setAction("annuler", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    addfav();
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        snackbar.show();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case R.id.imgopenmap:
                Intent IntentDPI;
                IntentDPI = new Intent(this, MapsActivity.class);
                IntentDPI.putExtra("location_X", location_X);
                IntentDPI.putExtra("location_Y", location_Y);
                IntentDPI.putExtra("nom_pont", nom_pont);
                startActivity(IntentDPI);
                break;
            case R.id.picturePont:
                selectImage();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode== Activity.RESULT_OK) {
            if (requestCode== REQUEST_CAMERA){
                Bundle bundle = data.getExtras();
                final Bitmap bmp= (Bitmap) bundle.get("data");
                photopont.setImageBitmap(bmp);
                String bmpPath = saveImage(bmp);
                try {
                    addImg(bmpPath);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            else if (requestCode==SELECT_FILE){
                Uri selectImageUri = data.getData();
                photopont.setImageURI(selectImageUri);
                String uriPath = getRealPathFromURI(getApplicationContext(),selectImageUri);
                try {
                    addImg(uriPath);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void addImg(String uriPath) throws JSONException{
        String light_data = readFile();
        JSONObject js = new JSONObject(light_data);
        JSONArray jsonArray = js.getJSONArray("lists_pt_int");
        JSONObject jsonObj = jsonArray.getJSONObject(Integer.parseInt(pont_id));
        JSONObject jsonObj2 = jsonObj.getJSONObject("fields");
        jsonObj2.put("image", uriPath);
        js.put("lists_pt_int",jsonArray);
        String finale = js.toString();
        try {
            outputStream = openFileOutput("light_data.json", Context.MODE_PRIVATE);
            outputStream.write(finale.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addfav() throws JSONException {
        String light_data = readFile();
        JSONObject js = new JSONObject(light_data);
        JSONArray jsonArray = js.getJSONArray("lists_pt_int");
        JSONObject jsonObj = jsonArray.getJSONObject(Integer.parseInt(pont_id));
        JSONObject jsonObj2 = jsonObj.getJSONObject("fields");
        jsonObj2.put("favorite", 1);
        js.put("lists_pt_int",jsonArray);
        favpont.setImageResource(R.drawable.ic_menu_fav);
        favoris_pont = "1";
        String finale = js.toString();
        try {
            outputStream = openFileOutput("light_data.json", Context.MODE_PRIVATE);
            outputStream.write(finale.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void rmfav() throws JSONException {
        String light_data = readFile();
        JSONObject js = new JSONObject(light_data);
        JSONArray jsonArray = js.getJSONArray("lists_pt_int");
        JSONObject jsonObj = jsonArray.getJSONObject(Integer.parseInt(pont_id));
        JSONObject jsonObj2 = jsonObj.getJSONObject("fields");
        jsonObj2.put("favorite", 0);
        js.put("lists_pt_int",jsonArray);
        favpont.setImageResource(R.drawable.ic_menu_notfav);
        favoris_pont = "0";
        String finale = js.toString();
        try {
            outputStream = openFileOutput("light_data.json", Context.MODE_PRIVATE);
            outputStream.write(finale.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String readFile(){
        String text = "";
        try {
            FileInputStream fis = openFileInput("light_data.json");
            int size = fis.available();
            byte[] buffer = new byte[size];
            fis.read(buffer);
            fis.close();
            text = new String(buffer);
        } catch (Exception e){
            e.printStackTrace();
        }
        Bundle bundle = new Bundle();
        bundle.putString("light_data", text);
        return text;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void selectImage() {
        final CharSequence[] items={"Camera", "Galerie", "Annuler"};

        AlertDialog.Builder builder = new AlertDialog.Builder(DetailPointInteret.this);
        builder.setTitle("Ajouter/modifier l'image ?");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (items[which].equals("Camera")){
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                else if (items[which].equals("Galerie")){
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selectionner un fichier"), SELECT_FILE);
                }
                else if (items[which].equals("Annuler")){
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    private String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private String saveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        File myDir = new File(root + "/saved_images");
        myDir.mkdirs();

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fname = "Shutta_"+ timeStamp +".jpg";

        File file = new File(myDir, fname);
        if (file.exists()) file.delete ();
        try {
            FileOutputStream out = new FileOutputStream(file);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
            String path = file.getAbsolutePath();
            return path;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return root;
    }
}
