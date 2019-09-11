package com.inc.niccher.task1;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Add_Estate extends AppCompatActivity {

    ListView ltype;
    String []est_type={"Commercial Property for Sale","Commercial Property for Rent","Event Center and Venues",
                            "Houses & Apartment for Sale","Houses & Apartment for Rent","Land and Plots for Sale",
                                "Land and Plots for Rent","Short Let and Hotels"};
    Intent proc;
    String uploadId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_estate);

        getSupportActionBar().setTitle("Select Category");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        proc=getIntent();

        Setta();

        ltype=findViewById(R.id.typo);

        ArrayAdapter<String> adpa=new ArrayAdapter<String>(this,R.layout.support_simple_spinner_dropdown_item,est_type);

        ltype.setAdapter(adpa);

        ltype.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //Toast.makeText(Add_Estate.this, "Yolo -> "+est_type[i], Toast.LENGTH_SHORT).show();
                Intent etyp=new Intent(Add_Estate.this,Estate_Edit.class);
                etyp.putExtra("Kiy",proc.getStringExtra("Kiy"));
                etyp.putExtra("Typee",est_type[i]);
                startActivity(etyp);
            }
        });

    }

    private void Setta(){
        uploadId=proc.getStringExtra("Kiy");
        try {
            if (uploadId.length() < 1){
                finish();
                startActivity(new Intent(Add_Estate.this, Casa.class));
                //Toast.makeText(this, "No key Passed", Toast.LENGTH_LONG).show();
            }else {
                //Toast.makeText(this, "Setta Estate Caught -> "+uploadId, Toast.LENGTH_LONG).show();
            }
        }catch (Exception ex){
            Toast.makeText(this, "Setta Error -> "+ex, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int idd=item.getItemId();
        if (idd==android.R.id.home){
            finish();
            startActivity(new Intent(Add_Estate.this,Casa.class));
        }
        return super.onOptionsItemSelected(item);
    }


}
