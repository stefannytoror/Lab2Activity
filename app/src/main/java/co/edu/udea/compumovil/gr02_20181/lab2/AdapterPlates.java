package co.edu.udea.compumovil.gr02_20181.lab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr02_20181.lab2.DB.PlatesStructure;


public class AdapterPlates  extends RecyclerView.Adapter<AdapterPlates.PlatesViewH> /*implements Filterable*/{

    co.edu.udea.compumovil.gr02_20181.lab2.AdapterDrinks.OnListener listen;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        try{
            listen = (co.edu.udea.compumovil.gr02_20181.lab2.AdapterDrinks.OnListener)recyclerView.getContext();
        }catch (Exception e){

        }
    }

    List<PlatesStructure> plates;
    List<PlatesStructure> platesArray = new ArrayList<PlatesStructure>();

    public AdapterPlates(List<PlatesStructure> plates){
        this.plates = plates;
        this.platesArray.addAll(plates);
    }

    @Override
    public PlatesViewH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_plate, parent,false);
        PlatesViewH vh = new PlatesViewH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterPlates.PlatesViewH holder, final int position) {

        holder.namePlate.setText(platesArray.get(position).getPlate_name());

        int price = platesArray.get(position).getPlate_price();
        holder.pricePlate.setText(String.valueOf(price));

        holder.typePlate.setText(platesArray.get(position).getPlate_type());
        holder.timePlate.setText(platesArray.get(position).getPlate_time());



        byte[] picture_drink = Base64.decode(platesArray.get(position).getPlate_picture(),Base64.DEFAULT);
        Bitmap bit = BitmapFactory.decodeByteArray(picture_drink, 0, picture_drink.length);
        holder.picturePlate.setImageBitmap(bit);


        holder.cardViewPlate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("namePlate",platesArray.get(position).getPlate_name());
                b.putString("schedulePlate",platesArray.get(position).getPlate_schedule());
                b.putString("typePlate",platesArray.get(position).getPlate_type());
                b.putInt("pricePlate",platesArray.get(position).getPlate_price());
                b.putString("timePlate",platesArray.get(position).getPlate_time());
                b.putString("ingredientsPlate",platesArray.get(position).getPlate_ingredients());
                b.putString("picturePlate",platesArray.get(position).getPlate_picture());
                listen.getPosition(b);
            }
        });
    }
    @Override
    public int getItemCount() {


        Log.d("hola","numero de items" + platesArray.size());
        return platesArray.size();
    }

    public class PlatesViewH extends RecyclerView.ViewHolder {
        CardView cardViewPlate;
        TextView namePlate, pricePlate, typePlate, timePlate;
        ImageView picturePlate;


        PlatesViewH(View itemView) {
            super(itemView);
            cardViewPlate = (CardView)itemView.findViewById(R.id.cardViewPlate);
            namePlate = (TextView)itemView.findViewById(R.id.txtCV_namePlate);
            pricePlate = (TextView)itemView.findViewById(R.id.txtCV_pricePlate);
            typePlate = (TextView)itemView.findViewById(R.id.txtCV_typePlate);
            timePlate = (TextView)itemView.findViewById(R.id.txtCV_timePlate);
            picturePlate = (ImageView)itemView.findViewById(R.id.imgCV_plate);
        }
    }
    public interface OnListener{
        public void getPosition(Bundle datos);
    }

}