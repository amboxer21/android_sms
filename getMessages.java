import java.io.*;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;

public class getMessages extends SQLiteOpenHelper{

private boolean checkDataBase(){

    SQLiteDatabase checkDB = null;
    final String DB_PATH = "/home/anthony/Documents/Ruby/androidSMS/";
    final String DB_NAME = "mmssms.db";

    try{
        String myPath = DB_PATH + DB_NAME;
        checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }catch(SQLiteException e){

    }

    if(checkDB != null){
        checkDB.close();
    }

    return checkDB != null ? true : false;
}

}
