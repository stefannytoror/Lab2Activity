package co.edu.udea.compumovil.gr02_20181.lab2.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
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

        //user table
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


        //drinks table

        db.execSQL("CREATE TABLE " + RestaurantDB.TABLE_DRINKS + " ("
                + RestaurantDB.ColumnDrinks.DRINK_NAME + " TEXT NOT NULL,"
                + RestaurantDB.ColumnDrinks.DRINK_PRICE + " INT NOT NULL,"
                + RestaurantDB.ColumnDrinks.DRINK_INGREDIENTS + " TEXT NOT NULL,"
                + RestaurantDB.ColumnDrinks.DRINK_PICTURE + " TEXT,"
                + "UNIQUE (" + RestaurantDB.ColumnDrinks.DRINK_NAME + "),"
                + "PRIMARY KEY (" + RestaurantDB.ColumnDrinks.DRINK_NAME + "))");
        Bitmap imageBitmap;
        String picture;

        imageBitmap = BitmapFactory.decodeResource(cont.getResources(),R.drawable.future);
        picture = encodeImage(imageBitmap);
        DrinksStructure drink2 = new DrinksStructure("Jugo muy maluco",2500,
                "Agua y veneno",picture);
        DrinksStructure drink1 = new DrinksStructure("Jugo muy deli",2000,
                "Agua y frutilla picadilla",picture);

        db.insert(RestaurantDB.TABLE_DRINKS, null, drink2.toContentValues());
        db.insert(RestaurantDB.TABLE_DRINKS, null, drink1.toContentValues());


        //Plates table
        db.execSQL("CREATE TABLE " + RestaurantDB.TABLE_PLATES + " ("
                + RestaurantDB.ColumnPlates.PLATE_NAME + " TEXT NOT NULL,"
                + RestaurantDB.ColumnPlates.PLATE_SCHEDULE + " TEXT NOT NULL,"
                + RestaurantDB.ColumnPlates.PLATE_TYPE + " TEXT NOT NULL,"
                + RestaurantDB.ColumnPlates.PLATE_PRICE + " INT NOT NULL,"
                + RestaurantDB.ColumnPlates.PLATE_TIME + " TEXT NOT NULL,"
                + RestaurantDB.ColumnPlates.PLATE_INGREDIENTS + " TEXT NOT NULL,"
                + RestaurantDB.ColumnPlates.PLATE_PICTURE + " TEXT,"
                + "UNIQUE (" + RestaurantDB.ColumnPlates.PLATE_NAME + "),"
                + "PRIMARY KEY (" + RestaurantDB.ColumnPlates.PLATE_NAME + "))");

        imageBitmap = BitmapFactory.decodeResource(cont.getResources(),R.drawable.future);
        picture = encodeImage(imageBitmap);
        PlatesStructure plate2 = new PlatesStructure("Arroz","Tarde",
                "Entrada",20000,"00:45",
                "Agua,sal al gusto, arroz",picture);
        PlatesStructure plate1 = new PlatesStructure("Huevo","Mañana",
                "Fuerte", 3000,"00:15","Huevo y agua caliente", picture);

        db.insert(RestaurantDB.TABLE_PLATES, null, plate2.toContentValues());
        db.insert(RestaurantDB.TABLE_PLATES, null, plate1.toContentValues());


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public String encodeImage(Bitmap bit) {
        ByteArrayOutputStream temp = new ByteArrayOutputStream();
        bit.compress(Bitmap.CompressFormat.PNG, 100, temp);
        byte[] p = temp.toByteArray();
        return (Base64.encodeToString(p, Base64.DEFAULT));
    }

    public Bitmap decodeString(String code) {
        byte[] temp = Base64.decode(code, Base64.DEFAULT);
        return (BitmapFactory.decodeByteArray(temp, 0, temp.length));
    }

    public List<DrinksStructure> drinksList() {
        SQLiteDatabase db = getReadableDatabase();
        List<DrinksStructure> listDrinks = new ArrayList<DrinksStructure>();
        String[] columns = {
                RestaurantDB.ColumnDrinks.DRINK_NAME,
                RestaurantDB.ColumnDrinks.DRINK_PRICE,
                RestaurantDB.ColumnDrinks.DRINK_INGREDIENTS,
                RestaurantDB.ColumnDrinks.DRINK_PICTURE};
        Cursor cursor = db.query(RestaurantDB.TABLE_DRINKS, columns, null, null,
                null, null, null, null);
        cursor.moveToFirst();
        do {
            DrinksStructure drinkObtain = new DrinksStructure(
                    cursor.getString(0), cursor.getInt(1), cursor.getString(2),
                    cursor.getString(3));
            listDrinks.add(drinkObtain);
        } while (cursor.moveToNext());
        db.close();
        cursor.close();
        return listDrinks;
    }

    public List<PlatesStructure> platesList() {
        SQLiteDatabase db = getReadableDatabase();
        List<PlatesStructure> listPlates = new ArrayList<PlatesStructure>();
        String[] columns = {
                RestaurantDB.ColumnPlates.PLATE_NAME,
                RestaurantDB.ColumnPlates.PLATE_SCHEDULE,
                RestaurantDB.ColumnPlates.PLATE_TYPE,
                RestaurantDB.ColumnPlates.PLATE_PRICE,
                RestaurantDB.ColumnPlates.PLATE_TIME,
                RestaurantDB.ColumnPlates.PLATE_INGREDIENTS,
                RestaurantDB.ColumnPlates.PLATE_PICTURE};
        Cursor cursor = db.query(RestaurantDB.TABLE_PLATES, columns, null, null,
                null, null, null, null);
        cursor.moveToFirst();
        do {
            PlatesStructure drinkObtain = new PlatesStructure(
                    cursor.getString(0),cursor.getString(1),cursor.getString(2),
                    cursor.getInt(3),cursor.getString(4), cursor.getString(5),
                    cursor.getString(6));
            listPlates.add(drinkObtain);
        } while (cursor.moveToNext());
        db.close();
        cursor.close();
        return listPlates;
    }



}
