package com.tpo.sparovcek.sparovcek;

import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.math.BigDecimal;
import java.util.List;

public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder>{


    List<UserHistoryActivity.Vnos> vnosi;

    RVAdapter(List<UserHistoryActivity.Vnos> persons){
        this.vnosi = persons;
    }
    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycled_cardview, viewGroup, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        UserHistoryActivity.Vnos info_vnos = vnosi.get(i);
        boolean positive = true;
        if(Double.valueOf(info_vnos.znesek_value.toString())<0){
            positive = false;
        }

        personViewHolder.personName.setText(vnosi.get(i).znesek);
        if(positive){
            personViewHolder.personName.setTextColor(Color.parseColor("#3ED9C4"));
        }else{
            personViewHolder.personName.setTextColor(Color.parseColor("#8500FA"));
        }
        String nazivkat = vnosi.get(i).kategorija;
        personViewHolder.personAge.setText(nazivkat);
        personViewHolder.personName2.setText(vnosi.get(i).naziv);
        String timedate = vnosi.get(i).timestamp+" "+vnosi.get(i).datetime;
        personViewHolder.personDatumCas.setText(timedate);
        personViewHolder.personPhoto.setImageResource(0);
    }

    @Override
    public int getItemCount() {
        return vnosi.size();
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView personName;
        TextView personName2;
        TextView personAge;
        TextView personDatumCas;
        ImageView personPhoto;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            personName2 = (TextView)itemView.findViewById(R.id.person_name2);
            personName = (TextView)itemView.findViewById(R.id.person_name);
            personAge = (TextView)itemView.findViewById(R.id.person_age);
            personDatumCas = (TextView)itemView.findViewById(R.id.person_datum_cas);
            personPhoto = (ImageView)itemView.findViewById(R.id.person_photo);
        }
    }

}
