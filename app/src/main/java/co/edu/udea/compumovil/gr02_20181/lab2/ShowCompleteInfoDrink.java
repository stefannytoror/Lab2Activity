package co.edu.udea.compumovil.gr02_20181.lab2;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShowCompleteInfoDrink.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShowCompleteInfoDrink#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShowCompleteInfoDrink extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    boolean favorite = false;


    private OnFragmentInteractionListener mListener;

    public ShowCompleteInfoDrink() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShowCompleteInfoDrink.
     */
    // TODO: Rename and change types and number of parameters
    public static ShowCompleteInfoDrink newInstance(String param1, String param2) {
        ShowCompleteInfoDrink fragment = new ShowCompleteInfoDrink();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_complete_info_drink,
                container, false);
        ((NDRestaurant)getActivity()).setActionBarTitle("Bebida");
        TextView t;
        ImageView i;
        final FloatingActionButton fb = (FloatingActionButton) view.findViewById(R.id.addFavortires);
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){

                if(favorite){
                    Toast.makeText(getContext(),"Eliminado de favoritos", Toast.LENGTH_SHORT).show();
                    fb.setImageResource(R.drawable.ic_star_border_black_24dp);
                    favorite = false;
                }else{
                    Toast.makeText(getContext(),"Agregado a favoritos", Toast.LENGTH_SHORT).show();
                    fb.setImageResource(R.drawable.ic_star_black_24dp);
                    favorite = true;
                }
            }
        });


        i = (ImageView)view.findViewById(R.id.imgDrinkComplete);
        byte[] photo = Base64.decode(getArguments().getString("pictureDrinks"),Base64.DEFAULT);
        i.setImageBitmap(BitmapFactory.decodeByteArray(photo,0,photo.length));

        t = (TextView)view.findViewById(R.id.txtNameCompleteDrink);
        t.setText(getArguments().getString("nameDrink"));

        t = (TextView)view.findViewById(R.id.txtPriceCompleteDrink);
        t.setText("Precio: " + getArguments().getInt("priceDrink"));

        t = (TextView)view.findViewById(R.id.txtIngredientsCompleteDrink);
        t.setText("Ingredientes: " + getArguments().getString("ingredientsDrinks"));

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

  /*  @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }*/

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


}
