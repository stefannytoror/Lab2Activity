package co.edu.udea.compumovil.gr02_20181.lab2;

import android.app.TimePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

import java.util.Calendar;


import javax.microedition.khronos.opengles.GL;






/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link AddPlatesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link AddPlatesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AddPlatesFragment extends Fragment implements TimePickerDialog.OnTimeSetListener, View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    // photo
    private String photo;

    //informacion del picker
    private TextView mTimeDisplay;
    private TextView mDateDisplay;
    private int mYear;
    private int mMonth;
    private int mDay;
    private int mHour;
    private int mMinute;

    public AddPlatesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AddPlatesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AddPlatesFragment newInstance(String param1, String param2) {
        AddPlatesFragment fragment = new AddPlatesFragment();
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
        //Organizar cuando se haga el layout

        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_plates, container, false);

        Button timePlate = (Button) view.findViewById(R.id.btnTiempoPlato);
        timePlate.setOnClickListener(this);
        Button addDrinkButton = (Button) view.findViewById(R.id.btnAgregarPlato);
        addDrinkButton.setOnClickListener(this);
        mTimeDisplay = (TextView)view.findViewById(R.id.txtTiempoPlato);

        updateDisplay();
        return view;

    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

   /* @Override
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

    private void updateDisplay() {
        mTimeDisplay.setText(new StringBuilder()
                .append(String.format("%02d",mHour)).append(":").append(String.format("%02d", mMinute)));


    }


    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        mHour = hourOfDay;
        mMinute = minute;
        updateDisplay();
    }

    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.btnTiempoPlato:
                DialogFragment timePickerFragment = new TimePickerFragment();
                timePickerFragment.show(getFragmentManager(), "timePicker");
                break;

            case R.id.btnAgregarPlato:
                break;

        }
    }


    public void addPlate(){
        EditText addDrink;
        String namePlateDB, schedulePlateDB,tipePlateDB, pricePlateDB, ingredientsDrinkDB;

        /*addDrink = (EditText) getView().findViewById(R.id.txtNombreBebida);
        nameDrinkDB = addDrink.getText().toString();


        addDrink = (EditText) getView().findViewById(R.id.txtPrecioBebida);
        priceDrinkDB = addDrink.getText().toString();

        addDrink = (EditText) getView().findViewById(R.id.txtIngredientesBebida);
        ingredientsDrinkDB = addDrink.getText().toString()*/;
    }

}
