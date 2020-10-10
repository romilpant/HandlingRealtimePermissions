package com.romilpant.runtime_permissions;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button cameraPermissionButton, storagePermissionButton, allPermissionsButton;
    private TextView cameraPermissionText, storagePermissionText, allPermissionsText;

    public static final int CAMERA_PERMISSION_CODE = 100;
    public static final int STORAGE_PERMISSION_CODE = 101;
    public static final int MULTIPLE_PERMISSIONS_CODE = 102;

    String[] permissions = new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cameraPermissionButton = findViewById(R.id.camera_permission_button);
        storagePermissionButton = findViewById(R.id.storage_permission_button);
        allPermissionsButton = findViewById(R.id.all_permissions_button);

        cameraPermissionText = findViewById(R.id.camera_permission_text);
        storagePermissionText = findViewById(R.id.storage_permission_text);
        allPermissionsText = findViewById(R.id.all_permissions_text);

        cameraPermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleCameraPermission();
            }
        });

        storagePermissionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleStoragePermission();
            }
        });

        allPermissionsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleAllPermissions();
            }
        });


    }

    private void handleCameraPermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            cameraPermissionText.setText("Permission Already Granted!");
            cameraPermissionText.setTextColor(Color.parseColor("#13B700"));
        }
    }

    private void handleStoragePermission() {
        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        } else {
            storagePermissionText.setText("Permission Already Granted!");
            storagePermissionText.setTextColor(Color.parseColor("#13B700"));
        }
    }

    private boolean handleAllPermissions() {
        int result;
        List<String> listPermissionsRequired = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(MainActivity.this, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsRequired.add(p);
            }
        }
        if (!listPermissionsRequired.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsRequired.toArray(new String[listPermissionsRequired.size()]), MULTIPLE_PERMISSIONS_CODE);
            return false;
        } else {
            allPermissionsText.setText("Permissions Already Granted!");
            allPermissionsText.setTextColor(Color.parseColor("#13B700"));
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                cameraPermissionText.setText("Permission Granted");
                cameraPermissionText.setTextColor(Color.parseColor("#13B700"));
            } else {
                cameraPermissionText.setText("Permission Denied!");
                cameraPermissionText.setTextColor(Color.parseColor("#FF0000"));
            }
        } else if (requestCode == STORAGE_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                storagePermissionText.setText("Permission Granted");
                storagePermissionText.setTextColor(Color.parseColor("#13B700"));
            } else {
                storagePermissionText.setText("Permission Denied!");
                storagePermissionText.setTextColor(Color.parseColor("#FF0000"));
            }
        } else if (requestCode == MULTIPLE_PERMISSIONS_CODE) {
            if (grantResults.length > 0) {
                String permissionsDenied = "";
                for (String per : permissions) {
                    if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                        permissionsDenied += "\n" + per;
                        allPermissionsText.setText("Permission Denied!");
                        allPermissionsText.setTextColor(Color.parseColor("#FF0000"));
                    } else {
                        allPermissionsText.setText("Permission Granted!");
                        allPermissionsText.setTextColor(Color.parseColor("#13B700"));
                    }

                }
                // Show permissionsDenied

            }
            return;

        }
    }
}