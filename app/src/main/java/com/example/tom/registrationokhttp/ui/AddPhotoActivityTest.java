package com.example.tom.registrationokhttp.ui;

import android.Manifest;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.location.Location;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.tom.registrationokhttp.R;
import com.example.tom.registrationokhttp.app.App;
import com.example.tom.registrationokhttp.app.DBConstants;
import com.example.tom.registrationokhttp.app.Logger;
import com.example.tom.registrationokhttp.db.AppDBHelper;
import com.example.tom.registrationokhttp.locationmanager.MyLocationManager;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class AddPhotoActivityTest extends AppCompatActivity implements MyLocationManager.Callback {

    private static final int PICK_IMAGE = 12;
    private Button saveButton;
    private ImageView imageView;
    private EditText etHeadline;
    private EditText etDescription;
    private long userId = -1;
    private double dLatitude;
    private double dLongitude;
    public static final int REQUEST_IMAGE = 100;
    public static final int REQUEST_PERMISSION = 200;
    private String imageFilePath = "";
    AlertDialog.Builder alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_photo);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        saveButton = findViewById(R.id.saveButtonAddPhotoActivity);
        imageView = findViewById(R.id.photoImageViewAddPhotoActivity);
        etHeadline = findViewById(R.id.headlineEditTextAddPhotoActivity);
        etDescription = findViewById(R.id.descriptionEditTextAddPhotoActivity);


        //ALERT DIALOG///
        String title = "Add an image";
        String message = "Choose the source";
        String makePhoto = "Make a photo";
        String gallery = "Get from gallery";

        alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle(title);
        alertDialog.setMessage(message);
        alertDialog.setNegativeButton(makePhoto, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                checkPermission();
                openCameraIntent();
            }
        });
        alertDialog.setPositiveButton(gallery, new DialogInterface.OnClickListener() {
            public File photoFile;

            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                photoPickerIntent.setType("image/*");

//                Uri photoUri = FileProvider.getUriForFile(AddPhotoActivityTest.this, getPackageName() + ".provider", photoFile);


//                photoPickerIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(photoPickerIntent, PICK_IMAGE);

            }
        });
        alertDialog.setCancelable(true);
        alertDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                Toast.makeText(AddPhotoActivityTest.this, "You're choose nothing", Toast.LENGTH_SHORT).show();
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.show();
            }
        });

        //ALERT DIALOG///


        //  DATA BASE ////





        saveButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick (View v){
        AppDBHelper dbHelper = new AppDBHelper(AddPhotoActivityTest.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        String strSelection = DBConstants.DB_FIELD_ID + "=?";
        String[] argsSelection = new String[]{Long.toString(userId)};
        String headline = etHeadline.getText().toString();
        String description = etDescription.getText().toString();
        String locationLatitude = String.valueOf(dLatitude);
        String locationLongitude = String.valueOf(dLatitude);
        String path = imageFilePath;

        ContentValues contentValues = new ContentValues();
        contentValues.put(DBConstants.DB_FIELD_HEADLINE, headline);
        contentValues.put(DBConstants.DB_FIELD_DESCRIPTION, description);
        contentValues.put(DBConstants.DB_FIELD_LATITUDE, locationLatitude);
        contentValues.put(DBConstants.DB_FIELD_LONGITUDE, locationLongitude);
        contentValues.put(DBConstants.DB_FIELD_PATH, path);

        if (userId != -1) {
            db.update(DBConstants.DB_TABLE_NAME, contentValues, strSelection, argsSelection);
            finish();
        }

        db.insert(DBConstants.DB_TABLE_NAME, null, contentValues);
//                Logger.v("headline ---> " + headline);
//                Logger.v("description ---> " + description);
//                Logger.v("locationLatitude ---> " + locationLatitude);
//                Logger.v("locationLongitude ---> " + locationLongitude);
//                Logger.v("path ---> " + path);


        String[] result;
        ArrayList<Cursor> resultMy = new ArrayList<>();
        if (db != null) {
            Cursor cursor = db.query(DBConstants.DB_TABLE_NAME,
                    result = new String[]{"_id, _headline", "_path", "_longitude"},
                    null, null, null, null, null);
            Logger.v("res.toString(); " + Arrays.toString(result));

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
//                                resultMy.add(cursor);
                        Logger.v("DB_FIELD_ID " + cursor.getString(cursor.getColumnIndex(DBConstants.DB_FIELD_ID)));
                        Logger.v("DB_FIELD_HEADLINE " + cursor.getString(cursor.getColumnIndex(DBConstants.DB_FIELD_HEADLINE)));
                        Logger.v("DB_FIELD_LONGITUDE " + cursor.getString(cursor.getColumnIndex(DBConstants.DB_FIELD_LONGITUDE)));
                        Logger.v("DB_FIELD_PATH " + cursor.getString(cursor.getColumnIndex(DBConstants.DB_FIELD_PATH)));
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
            db.close();
        }


//
//                  this.mUserId = cursor.getLong(nPositionId);
//        this.mName = cursor.getString(nPositionName);
//        this.mSName = cursor.getString(nPositionSName);

//                if (userId != -1){
//                    db.update(DBConstants.DB_TABLE_NAME, contentValues, strSelection, argsSelection);
//                    finish();
//                } else {
//                    db.insert(DBConstants.DB_TABLE_NAME, null, contentValues);
//                    etHeadline.setText("");
//                    etDescription.setText("");
//                }

        //  DATA BASE ////

    }

    });

}

    @Override
    protected void onResume() {
        App.getLocationManager().startLocationUpdate(this);
        super.onResume();
    }

    void checkPermission() {
        if ((ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) ||
                (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                        PackageManager.PERMISSION_GRANTED) ||
        (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED)){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, REQUEST_PERMISSION);
        }
    }

    private void openCameraIntent() {
        Intent pictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            // FileProvider
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {

                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    return;
                }
                Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", photoFile);
                Logger.v("photoUri >>> " + photoUri);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(pictureIntent, REQUEST_IMAGE);
            }
        } else {
            //CRASH HERE on 4.4!!!!
            checkPermission();
            if (pictureIntent.resolveActivity(getPackageManager()) != null) {
                File photoFile = null;
                try {
                    photoFile = createImageFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Uri photoUri = FileProvider.getUriForFile(this, getPackageName() + ".provider", photoFile);
                Logger.v("photoUri >>> " + photoUri);
                pictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                startActivityForResult(pictureIntent, REQUEST_IMAGE);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == REQUEST_PERMISSION && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Thanks for granting Permission", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        checkPermission();
        switch (requestCode) {
            case REQUEST_IMAGE:
                if (resultCode == RESULT_OK) {
                                          imageView.setImageURI(Uri.parse(imageFilePath));
                        Logger.v("imageView.setImageURI(Uri.parse(imageFilePath) " + imageFilePath);
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "You cancelled the operation", Toast.LENGTH_SHORT).show();
                }
                break;
            case PICK_IMAGE:
                if (resultCode == RESULT_OK) {
                    final Uri imageUri = data.getData();
                    final InputStream imageStream;
                    try {
                        if (imageUri != null) {
                            imageStream = getContentResolver().openInputStream(imageUri);
                            final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                            imageView.setImageBitmap(selectedImage);
                            imageStream.close();
                            Logger.v(" imageView.setImageBitmap(selectedImage) " + selectedImage);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    //TODO Parse PICK_IMAGE URI
                }
        }

    }

    private File createImageFile() throws IOException {
        checkPermission();
        String timeStamp = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        } else {
            timeStamp = "88888";
        }
        String imageFileName = "IMG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(imageFileName, ".jpg", storageDir);
        imageFilePath = image.getAbsolutePath();
        Logger.v("imageFilePath >>> " + imageFilePath);
        return image;
    }

    //  DATA BASE ////
    @Override
    public void locationUpdate(Location location) {
        this.dLatitude = location.getLatitude();
        this.dLongitude= location.getLongitude();
    }
    //  DATA BASE ////



}
