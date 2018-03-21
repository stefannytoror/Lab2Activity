package co.edu.udea.compumovil.gr02_20181.lab2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import java.io.IOException;

import co.edu.udea.compumovil.gr02_20181.lab2.DB.DbHelper;

public class LoginActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_GALLERY = 98;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.frame_loginContainer, new StartLoginFragment());
        ft.commit();
    }

    public void photoGallery(View v) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
        } else {
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, REQUEST_IMAGE_GALLERY);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            try {
                Bitmap selectedImage = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                DbHelper db = new DbHelper(getApplicationContext());
                RegisterUserFragment frag = (RegisterUserFragment) getSupportFragmentManager().findFragmentByTag("registerUserFragment");

                if (frag != null) {
                    String TAG = "que";
                    Log.d(TAG, "onActivityResult: guardo la imagen");
                    frag.setPhoto(db.encodeImage(selectedImage));
                }
            } catch (IOException e) {
            }

        }
    }
}
