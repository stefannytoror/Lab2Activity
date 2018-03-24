package co.edu.udea.compumovil.gr02_20181.lab2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.widget.SearchView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.ImageView;

import java.io.IOException;

import co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper;

public class NDRestaurant extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                                    AdapterDrinks.OnListener ,
                                    AdapterPlates.OnListener {

    private static final int REQUEST_IMAGE_GALLERY = 31;

    //false for plates true for Drinks
    boolean plateOrDrink = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ndrestaurant);
        SearchView search = (SearchView) findViewById(R.id.search);
        search.setLayoutParams(new Toolbar.LayoutParams(Gravity.RIGHT));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.ndrestaurant, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;

        if (id == R.id.nav_plates) {
            SearchView search = (SearchView) findViewById(R.id.search);
            search.setVisibility(View.VISIBLE);

            fragment = new PlatesFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,fragment)
                    .commit();

        } else if (id == R.id.nav_drinks) {
            SearchView search = (SearchView) findViewById(R.id.search);
            search.setVisibility(View.VISIBLE);
            fragment = new DrinksFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,fragment)
                    .commit();

        } else if (id == R.id.nav_profile) {
            SearchView search = (SearchView) findViewById(R.id.search);
            search.setVisibility(View.INVISIBLE);
            fragment = new UserProfileFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.container,fragment)
                    .commit();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }



    public void photoGallery(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);

        switch (v.getId()) {

            case R.id.btnAgregarImgBebida:
                plateOrDrink= true;
                break;
            case R.id.btnAgregarImagen:
                plateOrDrink= false;
                break;

        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            photoPickerIntent.setType("image/*");
            //crop
            photoPickerIntent.putExtra("crop","true");
            photoPickerIntent.putExtra("outputX",200);
            photoPickerIntent.putExtra("outputY",200);
            photoPickerIntent.putExtra("aspectX",1);
            photoPickerIntent.putExtra("aspectY",1);
            photoPickerIntent.putExtra("scale",true);
            photoPickerIntent.putExtra("outputFormat",Bitmap.CompressFormat.JPEG.toString());

            startActivityForResult(photoPickerIntent, REQUEST_IMAGE_GALLERY);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            Bundle b = data.getExtras();
            try {
                //Fragment fragment = new AddDrinksFragment();
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(),imageUri);
                Bitmap selectedImagec = b.getParcelable("data");

                if(plateOrDrink){
                    ImageView drinkImage = (ImageView) findViewById(R.id.imgBebida) ;
                    drinkImage.setImageBitmap(selectedImage);
                    AddDrinksFragment frag = (AddDrinksFragment) getSupportFragmentManager().findFragmentByTag("drinkFragmentTag");
                    if (frag != null) {
                        String TAG = "que";
                        Log.d(TAG, "onActivityResult: guardo la imagen de bebida");
                        DbHelper db = new DbHelper(getApplicationContext());
                        frag.setPhoto(db.encodeImage(selectedImage));

                    }
                }
                if(!plateOrDrink)
                {
                    Log.d("que", "onActivityResult: entro al else");
                    ImageView plateImage = (ImageView) findViewById(R.id.imgPlato) ;
                    plateImage.setImageBitmap(selectedImage);
                    AddPlatesFragment frag = (AddPlatesFragment) getSupportFragmentManager().
                            findFragmentByTag("platesFragmentTag");
                    Log.d("que", " imagen de plato");
                    if (frag != null) {
                        String TAG = "que";
                        Log.d(TAG, "onActivityResult: guardo la imagen de plato");
                        DbHelper db = new DbHelper(getApplicationContext());
                        frag.setPhoto(db.encodeImage(selectedImage));

                    }
                }


                //getSupportFragmentManager().beginTransaction().replace(R.id.container,fragment,"drinkFragmentTag").addToBackStack(null).commit();


            } catch (IOException e) {
            }

        }
    }

    @Override
    public void getPosition(Bundle datos) {
        Fragment frag = new ShowCompleteInfoDrink();
        frag.setArguments(datos);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).addToBackStack(null).commit();
    }

    @Override
    public void getPositionPlates(Bundle datosPlatos) {
        Fragment frag = new ShowCompleteInfoPlate();
        frag.setArguments(datosPlatos);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, frag).addToBackStack(null).commit();
    }

    public void newDrink(View view){
        Fragment fragment = new AddDrinksFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment,"drinkFragmentTag")
                .addToBackStack(null).commit();

    }

    public void newPlate(View view){
        Fragment fragment = new AddPlatesFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.container,fragment,"platesFragmentTag")
                .addToBackStack(null).commit();

    }

    public void setActionBarTitle(String title){
        getSupportActionBar().setTitle(title);
    }
}
