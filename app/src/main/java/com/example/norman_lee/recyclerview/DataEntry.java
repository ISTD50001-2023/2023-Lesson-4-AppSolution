package com.example.norman_lee.recyclerview;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;

public class DataEntry extends AppCompatActivity {

    EditText editTextNameEntry;
    Button buttonSelectImage;
    Button buttonOK;
    ImageView imageViewSelected;
    Bitmap bitmap;
    final static int REQUEST_IMAGE_GET = 2000;
    final static String KEY_PATH = "Image";
    final static String KEY_NAME = "Name";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_entry);

        editTextNameEntry = findViewById(R.id.editTextNameEntry);
        buttonSelectImage = findViewById(R.id.buttonSelectImage);
        imageViewSelected = findViewById(R.id.imageViewSelected);
        buttonOK = findViewById(R.id.buttonOK);

        //TODO 12.2 Set up an implicit intent to the image gallery (standard code)
        // registerForActivityResult takes in 2 objects
        // new ActivityResultContracts.StartActivityForResult() <-- can always put this
        // new ActivityResultCallback<ActivityResult>(){ ...} <--- it is always this
        final ActivityResultLauncher<Intent> launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        // YOU SAY WHAT HAPPENS WHEN THE IMAGE IS SELECTED
                        // --> A URI to the image is returned
                        if( result.getResultCode() == Activity.RESULT_OK
                        && result.getData() != null ){
                            Intent intent = result.getData();
                            Uri photoUri = intent.getData();
                            imageViewSelected.setImageURI(photoUri);

                            try {
                                bitmap = MediaStore.Images.Media.getBitmap(
                                        DataEntry.this.getContentResolver(),
                                        photoUri
                                );
                            } catch (IOException e) {
                                e.printStackTrace(); // Write a toast if you want
                            } // now go VVVVVVVVV
                        }
                    }
                }

        );


        //TODO 12.3a Set up a launcher to process the result of the selection
        buttonSelectImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /* MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                <-- location of the image gallery
                 */
                Intent intent = new Intent( Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                launcher.launch(intent);

            }
        });

        //TODO 12.4 When the OK button is clicked, set up an intent to go back to MainActivity
        buttonOK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                String name = editTextNameEntry.getText().toString();
                // Hack solution - save the bitmap to internal storage, and
                //   get the path of its location  ^^^^^ to launcher
                String path = Utils.saveToInternalStorage( bitmap, name, DataEntry.this);
                resultIntent.putExtra(KEY_NAME, name);
                resultIntent.putExtra(KEY_PATH, path);

                // MainActivity --Intent that expects a result--> DataEntry
                // DataEntry --setResult() to send data back --> MainActivity
                setResult( Activity.RESULT_OK, resultIntent);
                finish();
            }
        });

        //TODO 12.5 --> Go back to MainActivity


    }

    //TODO 12.3b Write onActivityResult to get the image selected
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,resultCode,data);
        if (requestCode == REQUEST_IMAGE_GET && resultCode == RESULT_OK) {

        }
    }
}
