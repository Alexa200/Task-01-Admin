package com.inc.niccher.task1;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;


public class Add_Car extends AppCompatActivity {

    private Spinner vmaker,vbody,vmodel,vyear,vmileage,vcondi,vng,vcolo,vtrans,vint,vfuel;
    private EditText bigdesc;
    private Button btnSubmit;
    private String Desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_car);

        getSupportActionBar().setTitle("Add A vehicle");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        vmaker = (Spinner) findViewById(R.id.cmaka);
        vmaker.setOnItemSelectedListener(new CarMakerListing());

        vbody = (Spinner) findViewById(R.id.cbodtype);
        vbody.setOnItemSelectedListener(new CarMakerListing());

        vmodel = (Spinner) findViewById(R.id.cmodel);
        vmodel.setOnItemSelectedListener(new CarMakerListing());

        vyear = (Spinner) findViewById(R.id.cyear);
        vyear.setOnItemSelectedListener(new CarMakerListing());

        vmileage = (Spinner) findViewById(R.id.cmileage);
        vmileage.setOnItemSelectedListener(new CarMakerListing());

        vcondi = (Spinner) findViewById(R.id.ccond);
        vcondi.setOnItemSelectedListener(new CarMakerListing());

        vng = (Spinner) findViewById(R.id.cengine);
        vng.setOnItemSelectedListener(new CarMakerListing());

        vcolo = (Spinner) findViewById(R.id.ccolor);
        vcolo.setOnItemSelectedListener(new CarMakerListing());

        vtrans = (Spinner) findViewById(R.id.ctrans);
        vtrans.setOnItemSelectedListener(new CarMakerListing());

        vint = (Spinner) findViewById(R.id.cinte);
        vint.setOnItemSelectedListener(new CarMakerListing());

        vfuel = (Spinner) findViewById(R.id.cfuel);
        vfuel.setOnItemSelectedListener(new CarMakerListing());

        bigdesc =  findViewById(R.id.cdesc);


        btnSubmit = (Button) findViewById(R.id.fina);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Desc="Car Maker : " +String.valueOf(vmaker.getSelectedItem())+"\n"+
                        "Car Body : " +String.valueOf(vbody.getSelectedItem())+"\n"+
                        "Car Model : " +String.valueOf(vmodel.getSelectedItem())+"\n"+
                        "Car Year : " +String.valueOf(vyear.getSelectedItem())+"\n"+
                        "Car Mileage : " +String.valueOf(vmileage.getSelectedItem())+"\n"+
                        "Car Condition : " +String.valueOf(vcondi.getSelectedItem())+"\n"+
                        "Car Engine : " +String.valueOf(vng.getSelectedItem())+"\n"+
                        "Car Color : " +String.valueOf(vcolo.getSelectedItem())+"\n"+
                        "Car Transmision : " +String.valueOf(vtrans.getSelectedItem())+"\n"+
                        "Car Interior : " +String.valueOf(vint.getSelectedItem())+"\n"+
                        "Car Fuel : " +String.valueOf(vfuel.getSelectedItem())+"\n";
                //vmaker,vbody,vmodel,vyear,vmileage,vcondi,vng,vcolo,vtrans,vint,vfuel;

                Toast.makeText(Add_Car.this, "Options\n"+Desc, Toast.LENGTH_LONG).show();
                //startActivity(new Intent(Add_Car.this,Add_CarImgs.class));

            }

        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idd=item.getItemId();
        if (idd==android.R.id.home){
            finish();
            startActivity(new Intent(Add_Car.this,Casa.class));
        }
        return super.onOptionsItemSelected(item);
    }


}
