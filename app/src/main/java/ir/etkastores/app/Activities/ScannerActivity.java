package ir.etkastores.app.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.google.zxing.Result;

import java.util.List;

import ir.etkastores.app.BuildConfig;
import ir.etkastores.app.R;
import me.dm7.barcodescanner.zxing.ZXingScannerView;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;
import pub.devrel.easypermissions.PermissionRequest;

public class ScannerActivity extends BaseActivity implements ZXingScannerView.ResultHandler, EasyPermissions.PermissionCallbacks {

    public static final String FORMAT = "FORMAT";
    public static final String DATA = "DATA";

    public final static int SCAN_REQUEST_CODE = 1005;
    private final static int PERMISSION_REQ_CODE = 1006;

    public static void show(Fragment fragment){
        Intent intent = new Intent(fragment.getActivity(),ScannerActivity.class);
        fragment.startActivityForResult(intent,SCAN_REQUEST_CODE);
    }

    public static void show(Activity activity){
        Intent intent = new Intent(activity,ScannerActivity.class);
        activity.startActivityForResult(intent,SCAN_REQUEST_CODE);
    }

    private ZXingScannerView mScannerView;

    String[] permissions = {Manifest.permission.CAMERA};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mScannerView = new ZXingScannerView(this);
        setContentView(mScannerView);
    }

    @Override
    public void handleResult(Result result) {
        if(BuildConfig.DEBUG){
            Log.e("Scanner", "result value: "+result.getText());
            Log.e("Scanner", "result format: "+result.getBarcodeFormat().toString());
        }
        mScannerView.resumeCameraPreview(this);
        Intent intent = new Intent();
        intent.putExtra(FORMAT,result.getBarcodeFormat().toString());
        intent.putExtra(DATA,result.getText());
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (EasyPermissions.hasPermissions(this, permissions)){
            mScannerView.setResultHandler(this);
            mScannerView.startCamera();
        }else{
            EasyPermissions.requestPermissions(
                    new PermissionRequest.Builder(this, PERMISSION_REQ_CODE, permissions)
                            .setRationale(R.string.cameraPermissionRationalMessage)
                            .setPositiveButtonText(R.string.continue_)
                            .setNegativeButtonText(R.string.cancel)
                            .build());
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }


    @Override
    public void onPermissionsGranted(int i, @NonNull List<String> list) {
        mScannerView.setResultHandler(this);
        mScannerView.startCamera();
    }

    @Override
    public void onPermissionsDenied(int i, @NonNull List<String> list) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, list)) {
            new AppSettingsDialog.Builder(this).build().show();
        }
        finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
            if (EasyPermissions.hasPermissions(this, permissions)) {
                mScannerView.setResultHandler(this);
                mScannerView.startCamera();
            }
        }
    }

}
