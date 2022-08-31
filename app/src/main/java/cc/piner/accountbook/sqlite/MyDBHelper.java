package cc.piner.accountbook.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * <p>createDate 22-3-10</p>
 * <p>fileName   MyDBHelper</p>
 *
 * @author KeQing
 * @version 1.0
 */
public class MyDBHelper extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "myDB.db";
    public static final String TABLE_NAME_COST = "costTable";
    public static final String COST_ID_COLUMN = "_ID";
    public static final String COST_COST_COLUMN = "_COST";
    public static final String COST_TIME_COLUMN = "_TIME";
    public static final String COST_DESC_COLUMN = "_DESC";
    public static final String[] COST_ALL_COLUMN = {COST_ID_COLUMN, COST_COST_COLUMN, COST_TIME_COLUMN, COST_DESC_COLUMN};

    public static final String TABLE_NAME_TAG = "tagTable";
    public static final String TAG_ID_COLUMN = "_ID";
    public static final String TAG_COSTID_COLUMN = "_COSTID";
    public static final String TAG_TAG_COLUMN = "_TAG";
    public static final String[] TAG_ALL_COLUMN = {TAG_ID_COLUMN, TAG_COSTID_COLUMN, TAG_TAG_COLUMN};

    private static final String SQL_CREATE_COST_TABLE =
            "CREATE TABLE " + TABLE_NAME_COST + " (" +
                    COST_ID_COLUMN + " INTEGER PRIMARY KEY," +
                    COST_COST_COLUMN + " INTEGER," +
                    COST_TIME_COLUMN + " INTEGER," +
                    COST_DESC_COLUMN + " TEXT)";
    public static final String SQL_CREATE_TAG_TABLE =
            "CREATE TABLE " + TABLE_NAME_TAG + " (" +
                    TAG_ID_COLUMN + " INTEGER PRIMARY KEY," +
                    TAG_COSTID_COLUMN + " INTEGER," +
                    TAG_TAG_COLUMN + " TEXT)";


    private static final String SQL_DELETE_COST_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME_COST;
    private static final String SQL_DELETE_TAG_TABLE =
            "DROP TABLE IF EXISTS " + TABLE_NAME_TAG;

    public MyDBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_COST_TABLE);
        db.execSQL(SQL_CREATE_TAG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_COST_TABLE);
        db.execSQL(SQL_DELETE_TAG_TABLE);
        onCreate(db);
    }
}
