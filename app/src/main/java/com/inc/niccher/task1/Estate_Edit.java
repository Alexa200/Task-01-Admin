package com.inc.niccher.task1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.ColorSpace;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.VideoView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class Estate_Edit extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner County,SubCounty;
    ArrayAdapter<String> countyArray,subcountyArray;
    Button btnSubmit,btnupload;
    EditText desc,price,area;

    private int coun=0,reqcod=4;
    private ProgressDialog pds22;

    private ImageView imgsel;
    private VideoView vidsel;

    Intent getta;
    String Ki,typ;

    Uri uri_image;

    FirebaseAuth mAuth;
    FirebaseUser userf;
    DatabaseReference dref,drefup;
    FirebaseDatabase fdbas;

    private StorageReference mStorageRef;
    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estate_edit);

        getSupportActionBar().setTitle("Populate Fields");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        pds22=new ProgressDialog(this);

        getta=getIntent();

        Init();

        mAuth = FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();
        fdbas= FirebaseDatabase.getInstance();

        mStorageRef = FirebaseStorage.getInstance().getReference("EstateImgs");

        imgsel = findViewById(R.id.com_imagesel);
        imgsel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CropImage.activity()
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setCropShape(CropImageView.CropShape.RECTANGLE)
                    .setBorderLineColor(Color.RED)
                    .setBorderCornerColor(Color.BLUE)
                    .setGuidelinesColor(Color.GREEN)
                    .setBorderLineThickness(2)
                    .start(Estate_Edit.this);
            }
        });

        btnupload = findViewById(R.id.upload);
        btnupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Estate_Edit.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                    //Toast.makeText(MainActivity.this, mPostBody.getText().toString().trim(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        btnSubmit = (Button) findViewById(R.id.fina);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendComit();
            }
        });

        desc= findViewById(R.id.cdesc);
        price= findViewById(R.id.cprice);
        area=findViewById(R.id.carea);

        desc.clearFocus();
        price.clearFocus();

        County = (Spinner) findViewById(R.id.sCountry);
        County.setOnItemSelectedListener(this);

        countyArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        countyArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        County.setAdapter(countyArray);

        countyArray.add("Baringo");
        countyArray.add("Bomet");
        countyArray.add("Bungoma");
        countyArray.add("Busia");
        countyArray.add("Elgeyo Marakwet ");
        countyArray.add("Embu");
        countyArray.add("Garissa");
        countyArray.add("Homabay");
        countyArray.add("Isiolo");
        countyArray.add("Kajiado");
        countyArray.add("Kakamega");
        countyArray.add("Kericho");
        countyArray.add("Kiambu");
        countyArray.add("Kilifi");
        countyArray.add("Kirinyaga");
        countyArray.add("Kisii");
        countyArray.add("Kisumu");
        countyArray.add("Kitui");
        countyArray.add("Kwale");
        countyArray.add("Laikipia");
        countyArray.add("Lamu");
        countyArray.add("Machakos");
        countyArray.add("Makueni");
        countyArray.add("Mandera");
        countyArray.add("Marsabit");
        countyArray.add("Meru");
        countyArray.add("Migori");
        countyArray.add("Mombasa");
        countyArray.add("Murang'a");
        countyArray.add("Nairobi");
        countyArray.add("Nakuru");
        countyArray.add("Nandi");
        countyArray.add("Narok");
        countyArray.add("Nyamira");
        countyArray.add("Nyandarua");
        countyArray.add("Nyeri");
        countyArray.add("Samburu");
        countyArray.add("Siaya");
        countyArray.add("Taita Taveta");
        countyArray.add("Tana River");
        countyArray.add("Trans Nzoia");
        countyArray.add("Tharaka Nithi ");
        countyArray.add("Turkana ");
        countyArray.add("Uasin Gishu");
        countyArray.add("Vihiga");
        countyArray.add("Wajir");
        countyArray.add("West Pokot");

        countyArray.setNotifyOnChange(true);

        County.setSelection(0);

        SubCounty = (Spinner) findViewById(R.id.subCounty);
        SubCounty.setOnItemSelectedListener(this);

        subcountyArray = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        subcountyArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        SubCounty.setAdapter(subcountyArray);


    }

    @Override
    protected void onStart() {
        desc.clearFocus();
        price.clearFocus();
        super.onStart();
    }

    @Override
    protected void onResume() {
        desc.clearFocus();
        price.clearFocus();
        super.onResume();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        
        int countrySpinnerPosition = County.getSelectedItemPosition();
        switch (countrySpinnerPosition){
            case 0:
                fillBaringo();
                break;
            case 1:
                fillBomet();
                break;
            case 2:
                fillBungoma();
                break;
            case 3:
                fillBusia();
                break;
            case 4:
                fillElgeyo();
                break;
            case 5:
                fillEmbu();
                break;
            case 6:
                fillGarissa();
                break;
            case 7:
                fillHomabay();
                break;
            case 8:
                fillIsiolo();
                break;
            case 9:
                fillKajiado();
                break;
            case 10:
                fillKakamega();
                break;
            case 11:
                fillKericho();
                break;
            case 12:
                fillKiambu();
                break;
            case 13:
                fillKilifi();
                break;
            case 14:
                fillKirinyaga();
                break;
            case 15:
                fillKisii();
                break;
            case 16:
                fillKisumu();
                break;
            case 17:
                fillKitui();
                break;
            case 18:
                fillKwale();
                break;
            case 19:
                fillLaikipia();
                break;
            case 20:
                fillLamu();
                break;
            case 21:
                fillMachakos();
                break;
            case 22:
                fillMakueni();
                break;
            case 23:
                fillMandera();
                break;
            case 24:
                fillMarsabit();
                break;
            case 25:
                fillMeru();
                break;
            case 26:
                fillMigori();
                break;
            case 27:
                fillMombasa();
                break;
            case 28:
                fillMurang();
                break;
            case 29:
                fillNairobi();
                break;
            case 30:
                fillNakuru();
                break;
            case 31:
                fillNandi();
                break;
            case 32:
                fillNarok();
                break;
            case 33:
                fillNyamira();
                break;
            case 34:
                fillNyandarua();
                break;
            case 35:
                fillNyeri();
                break;
            case 36:
                fillSamburu();
                break;
            case 37:
                fillSiaya();
                break;
            case 38:
                fillTaita();
                break;
            case 39:
                fillTana();
                break;
            case 40:
                fillTrans();
                break;
            case 41:
                fillTharaka();
                break;
            case 42:
                fillTurkana();
                break;
            case 43:
                fillUasin();
                break;
            case 44:
                fillVihiga();
                break;
            case 45:
                fillWajir();
                break;
            case 46:
                fillWest();
                break;
        }
    }

    private void fillBaringo(){
        subcountyArray.clear();
        subcountyArray.add("Baringo East");
        subcountyArray.add("Baringo West");
        subcountyArray.add("Baringo Central");
        subcountyArray.add("Mochongoi");
        subcountyArray.add("Mogotio");
        subcountyArray.add("Eldama Ravine");
        subcountyArray.notifyDataSetChanged();
    }


    private void fillBomet(){
        subcountyArray.clear();
        subcountyArray.add("Sotik");
        subcountyArray.add("Chepalungu");
        subcountyArray.add("Bomet East");
        subcountyArray.add("Bomet Central");
        subcountyArray.add("Konoin");
        subcountyArray.notifyDataSetChanged();
    }


    private void fillBungoma(){
        subcountyArray.clear();
        subcountyArray.add("Mt. Elgon");
        subcountyArray.add("Sirisia");
        subcountyArray.add("Kabuchia");
        subcountyArray.add("Bumula");
        subcountyArray.add("Kandunyi");
        subcountyArray.add("Webuye");
        subcountyArray.add("Bokoli");
        subcountyArray.add("Kimilili");
        subcountyArray.add("Tongaren");
        subcountyArray.notifyDataSetChanged();
    }


    private void fillBusia(){
        subcountyArray.clear();
        subcountyArray.add("Teso North");
        subcountyArray.add("Teso South");
        subcountyArray.add("Nambale");
        subcountyArray.add("Matayos");
        subcountyArray.add("Butula");
        subcountyArray.add("Funyula");
        subcountyArray.add("Budalangi");
        subcountyArray.notifyDataSetChanged();
    }


    private void fillElgeyo(){
        subcountyArray.clear();
        subcountyArray.add("Marakwet East");
        subcountyArray.add("Marakwet West");
        subcountyArray.add("Keiyo East");
        subcountyArray.add("Keiyo South");
        subcountyArray.notifyDataSetChanged();
    }


    private void fillEmbu(){
        subcountyArray.clear();
        subcountyArray.add("Manyatta");
        subcountyArray.add("Runyenjes");
        subcountyArray.add("Gachoka");
        subcountyArray.add("Siakago");
        subcountyArray.notifyDataSetChanged();
    }


    private void fillGarissa(){
        subcountyArray.clear();
        subcountyArray.add("TaveDujis");
        subcountyArray.add("Balambala");
        subcountyArray.add("Lagdera");
        subcountyArray.add("Dadaab");
        subcountyArray.add("Fafi");
        subcountyArray.add("Ijara");
        subcountyArray.notifyDataSetChanged();
    }


    private void fillHomabay(){
        subcountyArray.clear();
        subcountyArray.add("Kasipul");
        subcountyArray.add("Kabondo");
        subcountyArray.add("Karachuonyo");
        subcountyArray.add("Rangwe");
        subcountyArray.add("Homabay Town");
        subcountyArray.add("Ndhiwa");
        subcountyArray.add("Mbita");
        subcountyArray.add("Gwassi");
        subcountyArray.notifyDataSetChanged();
    }


    private void fillIsiolo(){
        subcountyArray.clear();
        subcountyArray.add("Isiolo North");
        subcountyArray.add("Isiolo South");
        subcountyArray.notifyDataSetChanged();
    }


    private void fillKajiado(){
        subcountyArray.clear();
        subcountyArray.add("Kajiado Central");
        subcountyArray.add("Kajiado North");
        subcountyArray.add("Kajiado South");
        subcountyArray.notifyDataSetChanged();
    }


    private void fillKakamega(){
        subcountyArray.clear();
        subcountyArray.add("Lugari");
        subcountyArray.add("Likuyani");
        subcountyArray.add("Malava");
        subcountyArray.add("Lurambi");
        subcountyArray.add("Makholo");
        subcountyArray.add("Mumias");
        subcountyArray.add("Mumias East");
        subcountyArray.add("Matungu");
        subcountyArray.add("Butere");
        subcountyArray.add("Khwisero");
        subcountyArray.add("Shinyalu");
        subcountyArray.add("ikolomani");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillKericho(){
        subcountyArray.clear();
        subcountyArray.add("Ainamoi");
        subcountyArray.add("Belgut");
        subcountyArray.add("Kipkelion");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillKiambu(){
        subcountyArray.clear();
        subcountyArray.add("Gatundu South");
        subcountyArray.add("Gatundu North");
        subcountyArray.add("Juja");
        subcountyArray.add("Thika Town");
        subcountyArray.add("Ruiru");
        subcountyArray.add("Githunguri");
        subcountyArray.add("Kiambu");
        subcountyArray.add("Kiambaa");
        subcountyArray.add("Kabete");
        subcountyArray.add("Kikuyu");
        subcountyArray.add("Limuru");
        subcountyArray.add("Lari");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillKilifi(){
        subcountyArray.clear();
        subcountyArray.add("Kilifi North");
        subcountyArray.add("Kilifi South");
        subcountyArray.add("Kaloleni");
        subcountyArray.add("Rabai");
        subcountyArray.add("Ganze");
        subcountyArray.add("Malindi");
        subcountyArray.add("Magarini");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillKirinyaga(){
        subcountyArray.clear();
        subcountyArray.add("Mwea");
        subcountyArray.add("Gichugu");
        subcountyArray.add("Ndia");
        subcountyArray.add("Kirinyaga");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillKisii(){
        subcountyArray.clear();
        subcountyArray.add("Bonchari");
        subcountyArray.add("South Mugirango");
        subcountyArray.add("Bomachoge");
        subcountyArray.add("Bobasi");
        subcountyArray.add("Gucha");
        subcountyArray.add("Nyaribari");
        subcountyArray.add("Masaba");
        subcountyArray.add("Nyaribari");
        subcountyArray.add("Chache");
        subcountyArray.add("Matrani");
        subcountyArray.add("Mosocho");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillKisumu(){
        subcountyArray.clear();
        subcountyArray.add("Kisumu East");
        subcountyArray.add("Kisumu West");
        subcountyArray.add("Kisumu Central");
        subcountyArray.add("Seme");
        subcountyArray.add("Nyando");
        subcountyArray.add("Muhoroni");
        subcountyArray.add("Nyakach");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillKitui(){
        subcountyArray.clear();
        subcountyArray.add("Mwingi North");
        subcountyArray.add("Mwingi Central");
        subcountyArray.add("Mwingi South");
        subcountyArray.add("Kitui West");
        subcountyArray.add("Kitui Rural");
        subcountyArray.add("Kitui Town");
        subcountyArray.add("Mutitu");
        subcountyArray.add("Kitui South");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillKwale(){

        subcountyArray.clear();
        subcountyArray.add("Msambweni");
        subcountyArray.add("Lunga Lunga");
        subcountyArray.add("Kinango");
        subcountyArray.add("Matuga");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillLaikipia(){
        subcountyArray.clear();
        subcountyArray.add("Laikipia West");
        subcountyArray.add("Laikipia Wast");
        subcountyArray.add("Laikipia North");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillLamu(){
        subcountyArray.clear();
        subcountyArray.add("Lamu East");
        subcountyArray.add("Lamu West");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillMachakos(){
        subcountyArray.clear();
        subcountyArray.add("Masinga");
        subcountyArray.add("Yatta");
        subcountyArray.add("Kangundo");
        subcountyArray.add("Matungulu");
        subcountyArray.add("Kathiani");
        subcountyArray.add("Mavoko");
        subcountyArray.add("Machakos Town");
        subcountyArray.add("Mwala");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillMakueni(){
        subcountyArray.clear();
        subcountyArray.add("Mbooni");
        subcountyArray.add("Kilome");
        subcountyArray.add("Kaiti");
        subcountyArray.add("Makueni");
        subcountyArray.add("Kibwezi West");
        subcountyArray.add("Kibwezi East");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillMandera(){
        subcountyArray.clear();
        subcountyArray.add("Mandera West");
        subcountyArray.add("Banisa");
        subcountyArray.add("Mandera North");
        subcountyArray.add("Mandera East");
        subcountyArray.add("Lafey");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillMarsabit(){
        subcountyArray.clear();
        subcountyArray.add("Moyale");
        subcountyArray.add("North Horr");
        subcountyArray.add("Saku");
        subcountyArray.add("Laisamis");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillMeru(){
        subcountyArray.clear();
        subcountyArray.add("Igembe South");
        subcountyArray.add("Igembe Central");
        subcountyArray.add("Igembe North");
        subcountyArray.add("Tigania West");
        subcountyArray.add("Tigania East");
        subcountyArray.add("North Imenti");
        subcountyArray.add("Buuri");
        subcountyArray.add("Central Imenti");
        subcountyArray.add("South Imenti");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillMigori(){
        subcountyArray.clear();
        subcountyArray.add("Rongo");
        subcountyArray.add("Awendo");
        subcountyArray.add("Migori East");
        subcountyArray.add("Migori West");
        subcountyArray.add("Uriri");
        subcountyArray.add("Nyatike");
        subcountyArray.add("Kuria East");
        subcountyArray.add("Kuria West");


        subcountyArray.notifyDataSetChanged();
    }


    private void fillMombasa(){
        subcountyArray.clear();
        subcountyArray.add("Changamwe");
        subcountyArray.add("Jomvu");
        subcountyArray.add("Kisauni");
        subcountyArray.add("Nyali");
        subcountyArray.add("likoni");
        subcountyArray.add("Mvita");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillMurang(){
        subcountyArray.clear();
        subcountyArray.add("Kangema");
        subcountyArray.add("Mathioya");
        subcountyArray.add("Kiharu");
        subcountyArray.add("Kigumo");
        subcountyArray.add("Maragwa");
        subcountyArray.add("Kandara");
        subcountyArray.add("Gatanga");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillNairobi(){
        subcountyArray.clear();
        subcountyArray.add("Westlands");
        subcountyArray.add("Parklands");
        subcountyArray.add("Dagoretti");
        subcountyArray.add("Karen / Langata");
        subcountyArray.add("Kibira");
        subcountyArray.add("Roysambu");
        subcountyArray.add("Kasarani");
        subcountyArray.add("Ruaraka");
        subcountyArray.add("Kariobangi");
        subcountyArray.add("kayole");
        subcountyArray.add("Embakasi");
        subcountyArray.add("Mihang’o");
        subcountyArray.add("Nairobi West");
        subcountyArray.add("Makadara");
        subcountyArray.add("kamukunii");
        subcountyArray.add("Starehe");
        subcountyArray.add("Mathare");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillNakuru(){
        subcountyArray.clear();
        subcountyArray.add("Molo");
        subcountyArray.add("Njoro");
        subcountyArray.add("Naivasha");
        subcountyArray.add("Gilgil");
        subcountyArray.add("Kuresoi South");
        subcountyArray.add("Kuresoi North");
        subcountyArray.add("Subukia");
        subcountyArray.add("Rongai");
        subcountyArray.add("Bahati");
        subcountyArray.add("Nakuru Town West");
        subcountyArray.add("Nakuru Town East");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillNandi() {
        subcountyArray.clear();
        subcountyArray.add("Tinderet");
        subcountyArray.add("Aldai");
        subcountyArray.add("Nandi Hills");
        subcountyArray.add("Emgwen North");
        subcountyArray.add("Emgwen South");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillNarok(){
        subcountyArray.clear();
        subcountyArray.add("Mosop");
        subcountyArray.add("Kilgoris");
        subcountyArray.add("Emurua Dikirr");
        subcountyArray.add("Narok North");
        subcountyArray.add("Kajiado East");
        subcountyArray.add("Kajiado West");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillNyamira() {
        subcountyArray.clear();
        subcountyArray.add("Kitutu Masaba");
        subcountyArray.add("North Mugirango");
        subcountyArray.add("West Mugirango");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillNyandarua(){
        subcountyArray.clear();
        subcountyArray.add("Kinangop");
        subcountyArray.add("Kipipiri");
        subcountyArray.add("Ol-Kalou");
        subcountyArray.add("Ol-Jorok");
        subcountyArray.add("Ndaragwa");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillNyeri(){
        subcountyArray.clear();
        subcountyArray.add("Tetu");
        subcountyArray.add("Kieni");
        subcountyArray.add("Mathira");
        subcountyArray.add("Othaya");
        subcountyArray.add("Mukurwe");
        subcountyArray.add("Nyeri Town");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillSamburu(){
        subcountyArray.clear();
        subcountyArray.add("Samburu West");
        subcountyArray.add("Samburu North");
        subcountyArray.add("Samburu East");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillSiaya(){
        subcountyArray.clear();
        subcountyArray.add("Ugenya");
        subcountyArray.add("Ugunja");
        subcountyArray.add("Alego Usonga");
        subcountyArray.add("Gem");
        subcountyArray.add("Bondo");
        subcountyArray.add("Rarieda");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillTaita(){
        subcountyArray.clear();
        subcountyArray.add("Taveta");
        subcountyArray.add("Wundanyi");
        subcountyArray.add("Mwatate");
        subcountyArray.add("Voi");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillTana(){
        subcountyArray.clear();
        subcountyArray.add("Garsen");
        subcountyArray.add("Galole");
        subcountyArray.add("Bura");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillTharaka(){
        subcountyArray.clear();
        subcountyArray.add("Nithi");
        subcountyArray.add("Maara");
        subcountyArray.add("Tharaka");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillTrans() {
        subcountyArray.clear();
        subcountyArray.add("Kwanza");
        subcountyArray.add("Endebess");
        subcountyArray.add("Saboti");
        subcountyArray.add("Kiminini");
        subcountyArray.add("Cherenganyi");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillTurkana(){
        subcountyArray.clear();
        subcountyArray.add("Turkana North");
        subcountyArray.add("Turkana West");
        subcountyArray.add("Turkana Central");
        subcountyArray.add("Loima");
        subcountyArray.add("Turkana South");
        subcountyArray.add("Turkana East");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillUasin(){
        subcountyArray.clear();
        subcountyArray.add("Eldoret East");
        subcountyArray.add("Eldoret North");
        subcountyArray.add("Eldoret South");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillVihiga(){
        subcountyArray.clear();
        subcountyArray.add("Vihiga");
        subcountyArray.add("Sabatia");
        subcountyArray.add("Hamisi");
        subcountyArray.add("Emuhaya");
        subcountyArray.add("Luanda");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillWajir() {
        subcountyArray.clear();
        subcountyArray.add("Wajir North");
        subcountyArray.add("Wajir East");
        subcountyArray.add("Tarbaj");
        subcountyArray.add("Wajir West");
        subcountyArray.add("Eldas");
        subcountyArray.add("Wajir South");

        subcountyArray.notifyDataSetChanged();
    }


    private void fillWest() {
        subcountyArray.clear();
        subcountyArray.add("Kapenguri");
        subcountyArray.add("Sigor");
        subcountyArray.add("Kacheliba");
        subcountyArray.add("Pokot South");

        subcountyArray.notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idd=item.getItemId();
        if (idd==android.R.id.home){
            finish();
            startActivity(new Intent(Estate_Edit.this,Add_Estate.class));
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                uri_image = result.getUri();
                Picasso.get().load(uri_image).into(imgsel);//imgshow
                //Verrif();
            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
                String er=error.getMessage().toString();
                Toast.makeText(this, "Error-> \n"+er, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void Init(){
        Ki=getta.getStringExtra("Kiy");
        typ=getta.getStringExtra("Typee");

        try {
            if (Ki.length() < 1 || typ.length()< 1){
                finish();
                startActivity(new Intent(Estate_Edit.this, Add_Estate.class));
                Toast.makeText(this, "No key Passed", Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Toast.makeText(this, "Setta Error -> "+ex, Toast.LENGTH_LONG).show();
        }
    }


    private void SendComit() {
        pds22.setMessage("Uploading Info");
        Toast.makeText(this, "Selected County :"+County.getSelectedItem().toString()+
                "\n Selected SubCounty :"+SubCounty.getSelectedItem().toString()+
                "\n Description :"+desc.getText().toString()+
                "\n Price :"+price.getText().toString(),Toast.LENGTH_LONG);
        pds22.show();

        Calendar cal= Calendar.getInstance();
        SimpleDateFormat ctime=new SimpleDateFormat("HH:mm");
        SimpleDateFormat cdate=new SimpleDateFormat("dd-MMMM-yyyy");

        final String ctim=ctime.format(cal.getTime());
        final String cdat=cdate.format(cal.getTime());

        DatabaseReference mDatabaseRef= FirebaseDatabase.getInstance().getReference("Posteds/Estates");

        HashMap<String , Object> hasm2=new HashMap<String, Object>();

        hasm2.put("eType" ,typ);
        hasm2.put("eCounty" ,SubCounty.getSelectedItem().toString());
        hasm2.put("eCountySub" ,SubCounty.getSelectedItem().toString());
        hasm2.put("eArea" ,String.valueOf(area.getText ().toString()));
        hasm2.put("ePrice" ,(price.getText().toString()));
        hasm2.put("eDesc",(desc.getText().toString()));
        hasm2.put("eImg" ,"Hey");
        hasm2.put("cKey",Ki);
        hasm2.put("cOwner",userf.getUid());
        hasm2.put("cTime","On "+cdat+" At "+ctim);

        mDatabaseRef.child(Ki).updateChildren(hasm2).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {

                pds22.dismiss();
                finish();
                Intent carimg=new Intent(Estate_Edit.this, Casa.class);
                carimg.putExtra("PostUUIDCode","Posts");
                startActivity(carimg);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pds22.dismiss();
                Toast.makeText(Estate_Edit.this, "Add car addOnFailureListener\n"+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void uploadFile() {
        pds22.setTitle("Uploading Image Resource");
        pds22.setMessage("Please Wait");
        pds22.show();

        if (uri_image != null) {
            final StorageReference fileReference = mStorageRef.child("Img"+System.currentTimeMillis()+ ".jpeg");

            mUploadTask = fileReference.putFile(uri_image)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            Task<Uri> result = taskSnapshot.getStorage().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    //drefup=FirebaseDatabase.getInstance().getReference("Posteds/"+userf.getUid()+"/Vehicles/"+uploadId);
                                    drefup=FirebaseDatabase.getInstance().getReference("Posteds/Estates/"+Ki);

                                    HashMap<String , Object> hasm3=new HashMap<String, Object>();

                                    hasm3.put("eImg"+coun/*System.currentTimeMillis()*/ ,uri.toString());
                                    coun=coun+1;
                                    //Log.e("mUploadTask ", "onSuccess: "+coun );
                                    uri_image=null;

                                    drefup.updateChildren(hasm3).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pds22.dismiss();
                                            Toast.makeText(Estate_Edit.this, "Add Image URL addOnFailureListener\n"+e.getMessage(), Toast.LENGTH_LONG).show();
                                        }
                                    });


                                    Picasso.get().load(R.drawable.ic_addcimg).placeholder(R.drawable.ic_addcimg).into(imgsel);
                                    pds22.dismiss();

                                    Toast.makeText(Estate_Edit.this, "Upload Succesfull", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            pds22.dismiss();
                            Toast.makeText(Estate_Edit.this, "Upload task addOnFailureListener\n"+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0 * taskSnapshot.getBytesTransferred() / taskSnapshot.getTotalByteCount());
                            //pds22.setProgress((int) progress);
                        }
                    });
        } else {
            pds22.dismiss();
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }

}
