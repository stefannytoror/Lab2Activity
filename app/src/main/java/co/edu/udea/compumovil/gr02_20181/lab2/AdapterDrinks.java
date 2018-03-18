package co.edu.udea.compumovil.gr02_20181.lab2;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;

import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr02_20181.lab2.DB.DrinksStructure;

/**
 * Created by personal on 18/03/18.
 */

public class AdapterDrinks  extends RecyclerView.Adapter<AdapterDrinks.DrinksViewH> /*implements Filterable*/{

    OnListener listen;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        try{
            listen = (OnListener)recyclerView.getContext();
        }catch (Exception e){

        }
    }

    List<DrinksStructure> drinks;
    List<DrinksStructure> drinksArray = new ArrayList<DrinksStructure>();

    public AdapterDrinks(List<DrinksStructure> drinks){
        this.drinks = drinks;
        this.drinksArray.addAll(drinks);
    }

    @Override
    public DrinksViewH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view_drink, parent,false);
        DrinksViewH vh = new DrinksViewH(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AdapterDrinks.DrinksViewH holder, final int position) {

        holder.nameDrink.setText(drinksArray.get(position).getDrink_name());
        int price = drinksArray.get(position).getDrink_price();

        holder.priceDrink.setText(String.valueOf(price));


        /*byte[] picture_drink = Base64.decode(drinksArray.get(position).getDrink_picture(),Base64.DEFAULT);
        Bitmap bit = BitmapFactory.decodeByteArray(picture_drink, 0, picture_drink.length);
        holder.pictureDrink.setImageBitmap(bit);*/

        holder.cardViewDrink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("nameDrink",drinksArray.get(position).getDrink_name());
                b.putInt("priceDrink",drinksArray.get(position).getDrink_price());
                b.putString("ingredients",drinksArray.get(position).getDrink_ingredients());
                b.putString("pictureDrinks",drinksArray.get(position).getDrink_picture());
                listen.getPosition(b);
            }
        });
    }
    @Override
    public int getItemCount() {


        Log.d("hola","numero de items" + drinksArray.size());
        return drinksArray.size();
    }

    public class DrinksViewH extends RecyclerView.ViewHolder {
        CardView cardViewDrink;
        TextView nameDrink, priceDrink;
        ImageView pictureDrink;


        DrinksViewH(View itemView) {
            super(itemView);
            cardViewDrink = (CardView)itemView.findViewById(R.id.cardViewDrink);
            nameDrink = (TextView)itemView.findViewById(R.id.txtCV_nameDrink);
            priceDrink = (TextView)itemView.findViewById(R.id.txtCV_priceDrink);
            pictureDrink = (ImageView)itemView.findViewById(R.id.imgCV_drink);
        }
    }
    public interface OnListener{
        public void getPosition(Bundle datos);
    }

}
