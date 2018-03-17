package co.edu.udea.compumovil.gr02_20181.lab2;

import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;


import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

import co.edu.udea.compumovil.gr02_20181.lab2.R;



public class DbHelper  extends SQLiteOpenHelper {

    private Context cont;

    public DbHelper(Context context) {
        super(context, RestaurantDB.DB_RESTAURANT_NAME, null, RestaurantDB.DB_VERSION);
        this.cont = context.getApplicationContext();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + RestaurantDB.TABLE_USER + " ("
                + RestaurantDB.ColumnUser.USER_NAME + " TEXT NOT NULL,"
                + RestaurantDB.ColumnUser.USER_EMAIL + " TEXT NOT NULL,"
                + RestaurantDB.ColumnUser.USER_PASSWORD + " TEXT NOT NULL,"
                + RestaurantDB.ColumnUser.USER_PICTURE + " TEXT,"
                + RestaurantDB.ColumnUser.USER_STATE + " TEXT NOT NULL,"
                + "UNIQUE (" + RestaurantDB.ColumnUser.USER_NAME + "),"
                + "PRIMARY KEY (" + RestaurantDB.ColumnUser.USER_EMAIL + "))");


        /*Bitmap user_picture = BitmapFactory.decodeResource(cont.getResources(), R.drawable.cali);

        String user_pictureString = encodeImage(user_picture);*/
        UserStructure user = new UserStructure("Santi ago", "santi@gmail.com", "1", "aqui va a imagen");
        db.insert(RestaurantDB.TABLE_USER, null, user.toContentValues());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String encodeImage(Bitmap bit){
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 0, temp);
        byte[] p = temp.toByteArray();
        return (Base64.encodeToString(p, Base64.DEFAULT));
    }

    public Bitmap decodeString(String code){
        byte[] temp = Base64.decode(code,Base64.DEFAULT);
        return (BitmapFactory.decodeByteArray(temp, 0, temp.length));
    }

}
