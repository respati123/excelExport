package com.example.acer.excelexport;

import android.Manifest;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.acer.excelexport.entities.User;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.WorkbookSettings;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class MainActivity extends AppCompatActivity {

    private List<String> data = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean writePermission = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        boolean readPermissions = ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;

        if(!writePermission){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
            }, 1);
        }

        if(!writePermission){
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            }, 2);
        }

        if(isExternalStorageReadable()){
            Log.d("TEST", "eksternal readable");
        }

        if(isExternalStorageWritable()){
            Log.d("TEST", "eksternal WRITE");
        }

        ModelView modelView = ViewModelProviders.of(this).get(ModelView.class);

        modelView.getGetAllData().observe(this, new Observer<List<User>>() {
            @Override
            public void onChanged(@Nullable List<User> users) {

                String folder = "Excel";
                File directory = new File(Environment.getExternalStorageDirectory() +"/"+folder);
                String csvFile = "myData1.xls";
                //create directory if not exist
                if (!directory.isDirectory()) {

                    Log.d("TEST", "tidak ada directory");
                    directory.mkdirs();
                }

                try {

                    //file path
                    Log.d("TEST", "ada directory");
                    File file = new File(directory, csvFile);
                    WorkbookSettings wbSettings = new WorkbookSettings();
                    wbSettings.setLocale(new Locale("en", "EN"));
                    WritableWorkbook workbook;
                    workbook = Workbook.createWorkbook(file, wbSettings);

                    //Excel sheet name. 0 represents first sheet
                    WritableSheet sheet = workbook.createSheet("userList", 0);
                    sheet.addCell(new Label(0, 0, "UserName"));
                    sheet.addCell(new Label(1, 0, "address"));
                    sheet.addCell(new Label(2, 0, "deskripsi"));

                    Log.d("TEST", ""+users.size());
                    for(int i = 0; i < users.size(); i++){
                        User temp = users.get(i);
//                        Log.d("TEST", "" +i + "." +temp.getName());
                        String name = temp.getName();
                        String address = temp.getAddress();
                        String deskripsi = temp.getDeskripsi();

                        sheet.addCell(new Label(0, i+1, "komar"));
                        sheet.addCell(new Label(1, i+1, "komar1"));
                        sheet.addCell(new Label(2, i+1, "komar2"));
                    }

                    workbook.write();
                    workbook.close();
                    Toast.makeText(getApplication(),
                            "Data Exported in a Excel Sheet", Toast.LENGTH_SHORT).show();
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

}
