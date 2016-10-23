package models;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by chuby on 2016/10/22.
 */

/*
  Usage:
  ContactsDatabaseHelper helper = ContactsDatabaseHelper.getInstance(this);
 */

public class ContactsDatabaseHelper extends SQLiteOpenHelper {
    private static ContactsDatabaseHelper sInstance;
    // Database Info
    private static final String DATABASE_NAME = "contactsDatabase";
    private static final int DATABASE_VERSION = 1;

    // Table Names
    private static final String TABLE_CONTACTS = "contacts";

    // Contact Table Columns
    private static final String KEY_CONTACT_ID = "id";
    private static final String KEY_CONTACT_CONTENT = "content";

    // Called when the database connection is being configured.
    // Configure database settings for things like foreign key support, write-ahead logging, etc.
    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    // Called when the database is created for the FIRST time.
    // If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACT_TABLE = "CREATE TABLE " + TABLE_CONTACTS +
                "(" +
                KEY_CONTACT_ID + " INTEGER PRIMARY KEY," + // Define a primary key
                KEY_CONTACT_CONTENT + " TEXT" +
                ")";

        db.execSQL(CREATE_CONTACT_TABLE);
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
            onCreate(db);
        }
    }

    public static synchronized ContactsDatabaseHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = new ContactsDatabaseHelper(context.getApplicationContext());
        }
        return sInstance;
    }

    //TODO Please help
    /**
     * Constructor should be private to prevent direct instantiation.
     * Make a call to the static method "getInstance()" instead.
     */
    private ContactsDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Insert or update a contact in the database
    // Since SQLite doesn't support "upsert" we need to fall back on an attempt to UPDATE (in case the
    // user already exists) optionally followed by an INSERT (in case the user does not already exist).
    // Unfortunately, there is a bug with the insertOnConflict method
    // (https://code.google.com/p/android/issues/detail?id=13045) so we need to fall back to the more
    // verbose option of querying for the user's primary key if we did an update.
    public void addContact(Contact contact) {
        // The database connection is cached so it's not expensive to call getWriteableDatabase() multiple times.
        SQLiteDatabase db = getWritableDatabase();
//        long contactDBId = -1;

        db.beginTransaction();
        try {
            // Serialize contact
            Gson gson = new Gson();
            String json = gson.toJson(contact);
            ContentValues values = new ContentValues();
            values.put(KEY_CONTACT_CONTENT, json);
            values.put(KEY_CONTACT_ID, contact.getId());

//            // Use contact id as the key in the table
//            values.put(KEY_CONTACT_ID, contact.getId());

            // First try to update the user in case the user already exists in the database
            // This assumes userNames are unique
            int rows = db.update(TABLE_CONTACTS, values, KEY_CONTACT_ID + "= ?", new String[]{contact.getId()});

            // Check if update succeeded
            if (rows == 1) {
                // Get the primary key of the user we just updated
                String contactsSelectQuery = String.format("SELECT %s FROM %s WHERE %s = ?",
                        KEY_CONTACT_ID, TABLE_CONTACTS, KEY_CONTACT_ID);
                Cursor cursor = db.rawQuery(contactsSelectQuery, new String[]{String.valueOf(contact.getId())});
                try {
                    if (cursor.moveToFirst()) {
//                        contactDBId = cursor.getInt(0);
                        db.setTransactionSuccessful();
                    }
                } finally {
                    if (cursor != null && !cursor.isClosed()) {
                        cursor.close();
                    }
                }
            } else {
                // user with this userName did not already exist, so insert new user
//                contactDBId = db.insertOrThrow(TABLE_CONTACTS, null, values);
                db.setTransactionSuccessful();
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to add or update user");
            // DEBUG
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    public List<Contact> getAllContacts() {
        List<Contact> contacts = new ArrayList<>();

        // SELECT * FROM POSTS
        // LEFT OUTER JOIN USERS
        // ON POSTS.KEY_POST_USER_ID_FK = USERS.KEY_USER_ID
        String POSTS_SELECT_QUERY =
                String.format("SELECT * FROM %s",
                        TABLE_CONTACTS);

        // "getReadableDatabase()" and "getWriteableDatabase()" return the same object (except under low
        // disk space scenarios)
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(POSTS_SELECT_QUERY, null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    JSONObject jsonObj = new JSONObject(cursor.getString(cursor.getColumnIndex(KEY_CONTACT_CONTENT)));
                    Contact newContact = models.Contact.fromJSON(jsonObj);
                    contacts.add(newContact);
                } while(cursor.moveToNext());
            }
        } catch (Exception e) {
            Log.d(TAG, "Error while trying to get posts from database");
        } finally {
            if (cursor != null && !cursor.isClosed()) {
                cursor.close();
            }
        }
        return contacts;
    }
}
