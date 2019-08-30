package com.inc.niccher.task1;

/**
 * Created by niccher on 29/05/19.
 */

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;


public class Adpter_Car extends RecyclerView.Adapter<Adpter_Car.Shika>{

    Context cnt;
    List<Mod_V> poslist;
    Dialog conf;
    String Postid=null;

    Button edpos,delpos,viupos;

    FirebaseUser fusa;

    FirebaseAuth mAuth;
    FirebaseUser userf;
    DatabaseReference dref1;

    public Adpter_Car(Context cnt, List<Mod_V> poslist) {
        this.cnt = cnt;
        this.poslist = poslist;
    }

    @Override
    public Shika onCreateViewHolder(ViewGroup parent, int viewType) {
        View infla= LayoutInflater.from(cnt).inflate(R.layout.part_postv,parent,false);

        fusa= FirebaseAuth.getInstance().getCurrentUser();

        final Shika shikapoint=new Shika(infla);

        conf=new Dialog(cnt);
        conf.setContentView(R.layout.part_cont);

        viupos=conf.findViewById(R.id.cnt_view);
        edpos=conf.findViewById(R.id.cnt_edit);
        delpos=conf.findViewById(R.id.cnt_delete);

        mAuth= FirebaseAuth.getInstance();
        userf=mAuth.getCurrentUser();

        /*Button edpos=conf.findViewById(R.id.cnt_edit);
        Button delpos=conf.findViewById(R.id.cnt_delete);

        /*edpos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(cnt, "Clicked Edit "+shikapoint.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });

        delpos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(cnt, "Clicked Delete "+shikapoint.getAdapterPosition(), Toast.LENGTH_SHORT).show();
            }
        });

        shikapoint.viu_PosDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(cnt, "Post ID "+shikapoint.getAdapterPosition()+"\nPost ID"+Postid, Toast.LENGTH_SHORT).show();
                conf.show();
            }
        });*/

        //return new Shika(infla);
        return shikapoint;
    }

    @Override
    public void onBindViewHolder(Shika holder, final int position) {

        final Mod_V kar=poslist.get(position);

        holder.viu_Bod.setText(kar.getcBody());
        holder.viu_Mak.setText(kar.getcMaker());
        holder.viu_Conditio.setText(kar.getcCondition());
        holder.viu_Pric.setText(kar.getcEngine());
        holder.viu_Mile.setText(kar.getcMileage());

        Postid=kar.getcKey();

        Picasso.get().load(kar.cImg0).into(holder.viu_carimg);

        holder.viu_PosDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conf.show();
            }
        });

        edpos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conf.dismiss();
                Intent posd=new Intent(cnt, Car_Edit.class);
                posd.putExtra("PostEditCode",kar.getcKey());
                cnt.startActivity(new Intent(posd));
            }
        });

        viupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conf.dismiss();
                Intent posd=new Intent(cnt, PostCarD.class);
                posd.putExtra("PostUUIDCode",kar.getcKey());
                cnt.startActivity(new Intent(posd));
            }
        });

        delpos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                conf.dismiss();
                DelPost();
            }
        });
        /*holder.viu_PosDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent posd=new Intent(cnt, PostCarD.class);
                posd.putExtra("PostUUIDCode",kar.getcKey());
                cnt.startActivity(new Intent(posd));
            }
        });*/
    }

    private void DelPost() {
        final ProgressDialog pgd=new ProgressDialog(cnt);
        pgd.setMessage("Deleting Post");
        pgd.show();
        try {
            //dref1= FirebaseDatabase.getInstance().getReference("Posteds/"+userf.getUid()+"/Vehicles/"+Postid);
            dref1= FirebaseDatabase.getInstance().getReference("Posteds/Vehicles/"+Postid);

            dref1.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    //Log.e("Adpter Car ", "DelPost: \n" +dref1.toString());
                    dref1.removeValue();
                    pgd.dismiss();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    //Log.e("Adpter Car ", "DelPost: onCancelled\n" +databaseError.getMessage());
                    pgd.dismiss();
                }
            });
        }catch (Exception ex){
            pgd.dismiss();
            Log.e("Adpter Car ", "DelPost: \n" +ex.getMessage());
        }

    }

    @Override
    public int getItemCount() {
        return poslist.size();
    }

    class Shika extends RecyclerView.ViewHolder {

        public TextView viu_Mak,viu_Bod,viu_Conditio,viu_Pric,viu_Mile;
        public ImageView viu_carimg;
        RelativeLayout viu_PosDetail;

        public Shika(View itemView) {
            super(itemView);

            viu_Mak = itemView.findViewById(R.id.disp_maker);
            viu_Bod = itemView.findViewById(R.id.disp_body);
            viu_Conditio = itemView.findViewById(R.id.disp_condition);
            viu_Pric = itemView.findViewById(R.id.disp_price);
            viu_Mile = itemView.findViewById(R.id.disp_mileage);

            viu_carimg = itemView.findViewById(R.id.disp_imgs);
            viu_PosDetail = itemView.findViewById(R.id.disp_post);
        }
    }
}