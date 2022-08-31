package cc.piner.accountbook.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import cc.piner.accountbook.sqlite.pojo.Cost;
import cc.piner.accountbook.sqlite.pojo.Tag;

/**
 * <p>createDate 22-3-10</p>
 * <p>fileName   MyDBDao</p>
 *
 * @author KeQing
 * @version 1.0
 */
public class MyDBDao {
    MyDBHelper dbHelper;

    public MyDBDao(MyDBHelper dbHelper) {
        // this.context = context;
        // this.dbHelper = new MyDBHelper(context);
        this.dbHelper = dbHelper;
    }

    public long insertCost(long cost, long time, String desc) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(MyDBHelper.COST_COST_COLUMN, cost);
        values.put(MyDBHelper.COST_TIME_COLUMN, time);
        values.put(MyDBHelper.COST_DESC_COLUMN, desc);
        return db.insert(MyDBHelper.TABLE_NAME_COST, null, values);
    }

    public long insertTag(long costID, String tag) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues val = new ContentValues();
        val.put(MyDBHelper.TAG_COSTID_COLUMN, costID);
        val.put(MyDBHelper.TAG_TAG_COLUMN, tag);
        return db.insert(MyDBHelper.TABLE_NAME_TAG, null, val);
    }

    public List<Cost> queryCost(String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = MyDBHelper.COST_ALL_COLUMN;

        // String selection = OrderDBHelper.NAME_COLUMN + " = ?";
        // String[] selectionArgs = {"xiaoming"};

        // String sortOrder = FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                MyDBHelper.TABLE_NAME_COST,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        List<Cost> costs = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MyDBHelper.COST_ID_COLUMN));
            long cost = cursor.getLong(cursor.getColumnIndexOrThrow(MyDBHelper.COST_COST_COLUMN));
            long time = cursor.getLong(cursor.getColumnIndexOrThrow(MyDBHelper.COST_TIME_COLUMN));
            String desc = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.COST_DESC_COLUMN));

            costs.add(new Cost(id, cost, time, desc));
        }

        cursor.close();
        return costs;
    }
    public List<Tag> queryTag(String selection, String[] selectionArgs, String sortOrder) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        String[] projection = MyDBHelper.TAG_ALL_COLUMN;

        // String selection = OrderDBHelper.NAME_COLUMN + " = ?";
        // String[] selectionArgs = {"xiaoming"};

        // String sortOrder = FeedEntry.COLUMN_NAME_SUBTITLE + " DESC";

        Cursor cursor = db.query(
                MyDBHelper.TABLE_NAME_TAG,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                selection,              // The columns for the WHERE clause
                selectionArgs,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                sortOrder               // The sort order
        );
        List<Tag> tags = new ArrayList<>();
        while (cursor.moveToNext()) {
            long id = cursor.getLong(cursor.getColumnIndexOrThrow(MyDBHelper.TAG_ID_COLUMN));
            long costId = cursor.getLong(cursor.getColumnIndexOrThrow(MyDBHelper.TAG_COSTID_COLUMN));
            String tag = cursor.getString(cursor.getColumnIndexOrThrow(MyDBHelper.TAG_TAG_COLUMN));

            tags.add(new Tag(id, costId, tag));
        }

        cursor.close();
        return tags;
    }

}
