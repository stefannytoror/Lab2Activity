package co.edu.udea.compumovil.gr02_20181.lab2;

import android.provider.BaseColumns;


public class RestaurantDB {
    public static final String DB_RESTAURANT_NAME = "lab2activity.db"; //DB name
    public static final int DB_VERSION = 1; //DB version
    public static final String TABLE_USER = "users";//Name of users table
    /*public static final String TABLE_PLATES = "plates"; //Name of plates table
    public static final String TABLE_DRINKS = "drinks";//Name of drinks table*/


    //Columns of the table
    public class ColumnUser {
        //public static final String ID = BaseColumns._ID; El ID se suele definir así por convención
        public static final String USER_NAME = "user_name";
        public static final String USER_EMAIL = "user_email";
        public static final String USER_PASSWORD = "user_password";
        public static final String USER_PICTURE = "user_picture";
        public static final String USER_STATE = "user_state"; //podria ser mejor un numero
    }

    //the rest of the tables here
}
