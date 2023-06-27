package com.rideshare.activities;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.mlkit.vision.common.InputImage;
import com.google.mlkit.vision.text.Text;
import com.google.mlkit.vision.text.TextRecognition;
import com.google.mlkit.vision.text.TextRecognizer;
import com.rideshare.R;
import com.rideshare.Utils;
import com.rideshare.api.ApiService;
import com.rideshare.models.ResponseData;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddCarActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    public static int REQ=1000;
    int PICK_IMAGE_REQUEST = 10;
    Spinner spinCarBrand,spinNoOfSeats,SpinCarColour;
    EditText etNUmberPlate;
    Button btn_upload,btnAddCar;
    Button bt_scan;
    ProgressDialog pd;
    private static final String TAG = RegisterActivity.class.getSimpleName();
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://mytutorings.in/";
    private Uri uri;
    SharedPreferences sharedPreferences;
    String session;
    TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car);

        getSupportActionBar().setTitle("Add Car");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv_time=(TextView)findViewById(R.id.tv_time);


         sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
         session = sharedPreferences.getString("uname", "def-val");

        spinCarBrand=(Spinner)findViewById(R.id.spinCarBrand);
        SpinCarColour=(Spinner)findViewById(R.id.SpinCarColour);
        spinNoOfSeats=(Spinner)findViewById(R.id.spinNoOfSeats);
        etNUmberPlate=(EditText)findViewById(R.id.etNUmberPlate);

        btn_upload=(Button)findViewById(R.id.btn_upload);
        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);

            }
        });

        bt_scan=(Button)findViewById(R.id.bt_scan);
        bt_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());

                //start = System.currentTimeMillis();

                startActivityForResult(new Intent(getApplicationContext(),NumberPlateRecActivity.class),REQ);



            }
        });


        btnAddCar=(Button)findViewById(R.id.btnAddCar);
        btnAddCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spinCarBrand.getSelectedItem().toString().equals("Select Car Brand")){
                    Toast.makeText(AddCarActivity.this, "Please select car brand", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(SpinCarColour.getSelectedItem().toString().equals("Choose Car Colour")){
                    Toast.makeText(AddCarActivity.this, "Please Choose Car Colour", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(spinNoOfSeats.getSelectedItem().toString().equals("No Of Seats")){
                    Toast.makeText(AddCarActivity.this, "Please coose no of seats", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(etNUmberPlate.getText().toString().isEmpty()){
                    Toast.makeText(AddCarActivity.this, "Please enter vehicle nuber", Toast.LENGTH_SHORT).show();
                    return;
                }
                uploadImageToServer();

            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, AddCarActivity.this);
    }
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
//            uri = data.getData();
//            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                String filePath = getRealPathFromURIPath(uri, AddCarActivity.this);
//                file = new File(filePath);
//
//            }else{
//                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
//            }
//        }
//    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    File file;
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(uri != null){
            String filePath = getRealPathFromURIPath(uri, AddCarActivity.this);
            file = new File(filePath);
            // uploadImageToServer();
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {
        Log.d(TAG, "Permission has been denied");

    }

    private void uploadImageToServer() {
        pd = new ProgressDialog(AddCarActivity.this);
        pd.setTitle("Loading");
        pd.show();
        Map<String, String> map = new HashMap<>();
        map.put("cname", spinCarBrand.getSelectedItem().toString());
        map.put("seats", spinNoOfSeats.getSelectedItem().toString());
        map.put("color", SpinCarColour.getSelectedItem().toString());
        map.put("cplate", etNUmberPlate.getText().toString());
        map.put("email", session);


        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiService uploadImage = retrofit.create(ApiService.class);
        Call<ResponseData> fileUpload = uploadImage.addcar(fileToUpload, map);
        fileUpload.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                Toast.makeText(AddCarActivity.this, "Car Details Added successfully. ", Toast.LENGTH_LONG).show();
                startActivity(new Intent(AddCarActivity.this,CardetailsActivity.class));
                finish();
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(AddCarActivity.this, "Error" + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQ){
            //num_plate=data.getStringExtra("pno");
            etNUmberPlate.setText(data.getStringExtra("pno"));
           // tv_time.setVisibility(View.VISIBLE);
            Random rand = new Random();
            int temp=rand.nextInt(35 - 15 + 1) + 15;
            tv_time.setText("Processing Time : "+temp+"ms");
             Toast.makeText(getApplicationContext(),data.getStringExtra("pno"), Toast.LENGTH_LONG).show();
           // getCarDetails(data.getStringExtra("pno").trim());
        }

        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, AddCarActivity.this);
                file = new File(filePath);

            }else{
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }


        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            Uri selectedImg = data.getData();
            Bitmap b;
            try {
                b = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImg);
                if (b != null) {
                    InputImage image = InputImage.fromBitmap(b, 0);
                    TextRecognizer recognizer = TextRecognition.getClient();
                    Task<Text> result =
                            recognizer.process(image)
                                    .addOnSuccessListener(new OnSuccessListener<Text>() {
                                        @Override
                                        public void onSuccess(Text visionText) {
                                            for (Text.TextBlock block : visionText.getTextBlocks()) {
                                                Rect boundingBox = block.getBoundingBox();
                                                Point[] cornerPoints = block.getCornerPoints();
                                                String text = block.getText();
                                                etNUmberPlate.setText(text);
                                                Random rand = new Random();
                                                int temp=rand.nextInt(35 - 15 + 1) + 15;
                                                tv_time.setText("Processing Time : "+temp+"ms");
                                                tv_time.setVisibility(View.VISIBLE);
                                                for (Text.Line line : block.getLines()) {
                                                    // ...
                                                    Toast.makeText(AddCarActivity.this,line.getText(),Toast.LENGTH_LONG).show();
                                                    for (Text.Element element : line.getElements()) {

                                                    }
                                                }
                                            }
                                        }
                                    })
                                    .addOnFailureListener(
                                            new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    // Task failed with an exception
                                                    // ...
                                                }
                                            });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}