package co.edu.udea.compumovil.gr02_20181.lab2;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper;
import co.edu.udea.compumovil.gr02_20181.lab2.DB.DrinksStructure;
import co.edu.udea.compumovil.gr02_20181.lab2.DB.RestaurantDB;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddDrinksFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddDrinksFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddDrinksFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String photo;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public AddDrinksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddDrinksFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddDrinksFragment newInstance(String param1, String param2) {
        AddDrinksFragment fragment = new AddDrinksFragment();
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

        View viewDrinks = inflater.inflate(R.layout.fragment_add_drinks,
                container, false);
        ((NDRestaurant)getActivity()).setActionBarTitle("Agregar Bebida");
        Button addDrinkButton = (Button) viewDrinks.findViewById(R.id.btnAgregarBebida);
        addDrinkButton.setOnClickListener(this);


        // Inflate the layout for this fragment
        return viewDrinks;
    }

    // TODO: Rename method, update argument and hook method into UI event
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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void setPhoto(String photo){this.photo = photo;}

    @Override
    public void onClick(View v){

        if(v.getId()== R.id.btnAgregarBebida) {
        /*Button imageButton = (Button) view.findViewById(R.id.btnAgregarImgBebida);*/

            EditText addDrink;
            String nameDrinkDB, priceDrinkDB, ingredientsDrinkDB;

            addDrink = (EditText) getView().findViewById(R.id.txtNombreBebida);
            nameDrinkDB = addDrink.getText().toString();


            addDrink = (EditText) getView().findViewById(R.id.txtPrecioBebida);
            priceDrinkDB = addDrink.getText().toString();

            addDrink = (EditText) getView().findViewById(R.id.txtIngredientesBebida);
            ingredientsDrinkDB = addDrink.getText().toString();


            if (photo == null) {
                Toast.makeText(getContext(), "No se pudo asignar foto", Toast.LENGTH_SHORT).show();
            }

            if (nameDrinkDB.equals("") || priceDrinkDB.equals("") || ingredientsDrinkDB.equals("") || photo == null) {
                Toast.makeText(getContext(), "Información incompleta", Toast.LENGTH_SHORT).show();
            } else {

                int priceDrinkDb = Integer.parseInt(priceDrinkDB);

                DbHelper dbHelper = new DbHelper(getContext());
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                DrinksStructure drink_structure = new DrinksStructure(nameDrinkDB, priceDrinkDb, ingredientsDrinkDB, photo);

                //insert in  DB
                db.insert(RestaurantDB.TABLE_DRINKS, null, drink_structure.toContentValues());

                Fragment drinksf = new DrinksFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction().replace(R.id.container, drinksf).commit();
            }

        }
    }
}
