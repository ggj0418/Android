package com.example.android.Activites.Barcode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Toast;

import com.example.android.DTOS.CartItemDTO;
import com.example.android.R;
import com.example.android.Retrofit.RetrofitClient;
import com.example.android.Services.Services;
import com.example.android.Utils.Barcode.BarcodeGraphic;
import com.example.android.Utils.Barcode.BarcodeGraphicTracker;
import com.example.android.Utils.Barcode.BarcodeTrackerFactory;
import com.example.android.Utils.Camera.CameraSource;
import com.example.android.Utils.Camera.CameraSourcePreview;
import com.example.android.Utils.Camera.GraphicOverlay;
import com.example.android.Utils.Preference.PreferenceManager;
import com.example.android.Utils.RecyclerView.CartClickListener;
import com.example.android.Utils.RecyclerView.MyCartAdapter;
import com.example.android.Utils.RecyclerView.RecyclerViewMethod;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.MultiProcessor;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;
import com.google.android.material.snackbar.Snackbar;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class QRCodeAcitivty extends AppCompatActivity implements BarcodeGraphicTracker.BarcodeUpdateListener {
    private List<CartItemDTO> cartItemList = new ArrayList<>();

    private static final String TAG = "Barcode-reader";

    private long backKeyPressedTime = 0;

    // intent request code to handle updating play services if needed.
    private static final int RC_HANDLE_GMS = 9001;

    // permission request codes need to be < 256
    private static final int RC_HANDLE_CAMERA_PERM = 2;

    // constants used to pass extra data in the intent
    public static final String AutoFocus = "AutoFocus";
    public static final String UseFlash = "UseFlash";
    public static final String BarcodeObject = "Barcode";

    private CameraSource mCameraSource;
    private CameraSourcePreview mPreview;
    private GraphicOverlay<BarcodeGraphic> mGraphicOverlay;

    // helper objects for detecting taps and pinches.
    private ScaleGestureDetector scaleGestureDetector;
    private GestureDetector gestureDetector;

    // get vibrator object
    private Vibrator vibrator;

    // android component
    private SlidingUpPanelLayout slidingUpPanelLayout;
    private RecyclerView recyclerView;
    private MyCartAdapter myCartAdapter;

    private Services retrofitAPI2;
    private RecyclerViewMethod recyclerViewMethod;
    /**
     * Initializes the UI and creates the detector pipeline.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);     // 타이틀바 제거
        setContentView(R.layout.activity_qr_activity);
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);     // 상태바 제거

        retrofitAPI2 = RetrofitClient.getRetrofit(PreferenceManager.getString(getApplicationContext(), "accessToken")).create(Services.class);

        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        mPreview = (CameraSourcePreview) findViewById(R.id.preview);
        mGraphicOverlay = findViewById(R.id.graphicOverlay);

        recyclerView = findViewById(R.id.rv_map);
        slidingUpPanelLayout = findViewById(R.id.slide_up);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        myCartAdapter = new MyCartAdapter(getApplicationContext(), cartItemList, new CartClickListener() {
            @Override
            public void onPositionClicked(int position, String value) {
                Log.d("MyBasketAdapter", value);
            }
        });
        recyclerView.setAdapter(myCartAdapter);

        // Check for the camera permission before accessing the camera.  If the
        // permission is not granted yet, request permission.
        int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA);
        if (rc == PackageManager.PERMISSION_GRANTED) {
            createCameraSource(true, false);
        } else {
            requestCameraPermission();
        }

        gestureDetector = new GestureDetector(this, new CaptureGestureListener());
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());

        recyclerViewMethod = new RecyclerViewMethod(getApplicationContext(), cartItemList, myCartAdapter);
//        showCartList();
        recyclerViewMethod.showCartList();
    }

//    private void setCartItem(int productCode) {
//        HashMap<String, Integer> map = new HashMap<>();
//        map.put("productCode", productCode);
//
//        Call<ResponseBody> setCartItemCall = retrofitAPI2.setCartItem(map);
//        setCartItemCall.enqueue(new Callback<ResponseBody>() {
//            @EverythingIsNonNull
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
//                switch (response.code()) {
//                    case 200:
//                        Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 201:
//                        Toast.makeText(getApplicationContext(), "정상적으로 장바구니에 상품이 추가되었습니다.", Toast.LENGTH_SHORT).show();
//                        showCartList();
//                        myCartAdapter.notifyDataSetChanged();
//                        break;
//                    case 400:
//                        Toast.makeText(getApplicationContext(), "유효한 입력이 아닙니다. 혹은 재고 부족으로 인해 상품을 담을 수 없습니다.", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 401:
//                        Toast.makeText(getApplicationContext(), "토큰 만료", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 403:
//                        Toast.makeText(getApplicationContext(), "유저만 접근 가능", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 404:
//                        Toast.makeText(getApplicationContext(), "해당 제품이 존재하지 않습니다.", Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        Toast.makeText(getApplicationContext(), "서버 내부 에러입니다", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//
//            @EverythingIsNonNull
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "통신 에러입니다", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

//    public void showCartList() {
//        Call<List<CartItemDTO>> getCartListCall = retrofitAPI2.getCartList();
//        getCartListCall.enqueue(new Callback<List<CartItemDTO>>() {
//            @EverythingIsNonNull
//            @Override
//            public void onResponse(Call<List<CartItemDTO>> call, Response<List<CartItemDTO>> response) {
//                switch (response.code()) {
//                    case 200:
//                        Toast.makeText(getApplicationContext(), "장바구니 리스트업 성공", Toast.LENGTH_SHORT).show();
//                        cartItemList.clear();
//                        cartItemList.addAll(response.body());
//                        myCartAdapter.notifyDataSetChanged();
//                        break;
//                    case 401:
//                        Toast.makeText(getApplicationContext(), "토큰 만료", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 403:
//                        Toast.makeText(getApplicationContext(), "유저만 접근 가능", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 404:
//                        Toast.makeText(getApplicationContext(), "해당 유저의 장바구니가 존재하지 않습니다", Toast.LENGTH_SHORT).show();
//                        break;
//                    default:
//                        Toast.makeText(getApplicationContext(), "서버 내부 에러입니다", Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//
//            @EverythingIsNonNull
//            @Override
//            public void onFailure(Call<List<CartItemDTO>> call, Throwable t) {
//                Toast.makeText(getApplicationContext(), "통신 에러입니다", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }

    /**
     * Handles the requesting of the camera permission.  This includes
     * showing a "Snackbar" message of why the permission is needed then
     * sending the request.
     */
    private void requestCameraPermission() {
        Log.w(TAG, "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.CAMERA};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.CAMERA)) {
            ActivityCompat.requestPermissions(this, permissions, RC_HANDLE_CAMERA_PERM);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions,
                        RC_HANDLE_CAMERA_PERM);
            }
        };

        findViewById(R.id.topLayout).setOnClickListener(listener);
        Snackbar.make(mGraphicOverlay, R.string.permission_camera_rationale,
                Snackbar.LENGTH_INDEFINITE)
                .setAction(R.string.ok, listener)
                .show();
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        boolean b = scaleGestureDetector.onTouchEvent(e);

        boolean c = gestureDetector.onTouchEvent(e);

        return b || c || super.onTouchEvent(e);
    }

    /**
     * Creates and starts the camera.  Note that this uses a higher resolution in comparison
     * to other detection examples to enable the barcode detector to detect small barcodes
     * at long distances.
     *
     * Suppressing InlinedApi since there is a check that the minimum version is met before using
     * the constant.
     */
    @SuppressLint("InlinedApi")
    private void createCameraSource(boolean autoFocus, boolean useFlash) {
        Context context = getApplicationContext();

        // A barcode detector is created to track barcodes.  An associated multi-processor instance
        // is set to receive the barcode detection results, track the barcodes, and maintain
        // graphics for each barcode on screen.  The factory is used by the multi-processor to
        // create a separate tracker instance for each barcode.
        BarcodeDetector barcodeDetector = new BarcodeDetector.Builder(context).build();
        BarcodeTrackerFactory barcodeFactory = new BarcodeTrackerFactory(mGraphicOverlay, this);
        barcodeDetector.setProcessor(
                new MultiProcessor.Builder<>(barcodeFactory).build());

        if (!barcodeDetector.isOperational()) {
            // Note: The first time that an app using the barcode or face API is installed on a
            // device, GMS will download a native libraries to the device in order to do detection.
            // Usually this completes before the app is run for the first time.  But if that
            // download has not yet completed, then the above call will not detect any barcodes
            // and/or faces.
            //
            // isOperational() can be used to check if the required native libraries are currently
            // available.  The detectors will automatically become operational once the library
            // downloads complete on device.
            Log.w(TAG, "Detector dependencies are not yet available.");

            // Check for low storage.  If there is low storage, the native library will not be
            // downloaded, so detection will not become operational.
            IntentFilter lowstorageFilter = new IntentFilter(Intent.ACTION_DEVICE_STORAGE_LOW);
            boolean hasLowStorage = registerReceiver(null, lowstorageFilter) != null;

            if (hasLowStorage) {
                Toast.makeText(this, R.string.low_storage_error, Toast.LENGTH_LONG).show();
                Log.w(TAG, getString(R.string.low_storage_error));
            }
        }

        // Creates and starts the camera.  Note that this uses a higher resolution in comparison
        // to other detection examples to enable the barcode detector to detect small barcodes
        // at long distances.
        CameraSource.Builder builder = new CameraSource.Builder(getApplicationContext(), barcodeDetector)
                .setFacing(CameraSource.CAMERA_FACING_BACK)
                .setRequestedPreviewSize(1600, 1024)
                .setRequestedFps(15.0f);

        // make sure that auto focus is an available option
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            builder = builder.setFocusMode(
                    autoFocus ? Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE : null);
        }

        mCameraSource = builder
                .setFlashMode(useFlash ? Camera.Parameters.FLASH_MODE_TORCH : null)
                .build();
    }

    /**
     * Restarts the camera.
     */
    @Override
    protected void onResume() {
        super.onResume();
        startCameraSource();
    }

    /**
     * Stops the camera.
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (mPreview != null) {
            mPreview.stop();
        }
    }

    /**
     * Releases the resources associated with the camera source, the associated detectors, and the
     * rest of the processing pipeline.
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPreview != null) {
            mPreview.release();
        }
    }

    /**
     * Callback for the result from requesting permissions. This method
     * is invoked for every call on {@link #requestPermissions(String[], int)}.
     * <p>
     * <strong>Note:</strong> It is possible that the permissions request interaction
     * with the user is interrupted. In this case you will receive empty permissions
     * and results arrays which should be treated as a cancellation.
     * </p>
     *
     * @param requestCode  The request code passed in {@link #requestPermissions(String[], int)}.
     * @param permissions  The requested permissions. Never null.
     * @param grantResults The grant results for the corresponding permissions
     *                     which is either {@link PackageManager#PERMISSION_GRANTED}
     *                     or {@link PackageManager#PERMISSION_DENIED}. Never null.
     * @see #requestPermissions(String[], int)
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != RC_HANDLE_CAMERA_PERM) {
            Log.d(TAG, "Got unexpected permission result: " + requestCode);
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            return;
        }

        if (grantResults.length != 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Camera permission granted - initialize the camera source");
            // we have permission, so create the camerasource
            boolean autoFocus = getIntent().getBooleanExtra(AutoFocus,false);
            boolean useFlash = getIntent().getBooleanExtra(UseFlash, false);
            createCameraSource(autoFocus, useFlash);
            return;
        }

        Log.e(TAG, "Permission not granted: results len = " + grantResults.length +
                " Result code = " + (grantResults.length > 0 ? grantResults[0] : "(empty)"));

        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                finish();
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Multitracker sample")
                .setMessage(R.string.no_camera_permission)
                .setPositiveButton(R.string.ok, listener)
                .show();
    }

    /**
     * Starts or restarts the camera source, if it exists.  If the camera source doesn't exist yet
     * (e.g., because onResume was called before the camera source was created), this will be called
     * again when the camera source is created.
     */
    private void startCameraSource() throws SecurityException {
        // check that the device has play services available.
        int code = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(
                getApplicationContext());
        if (code != ConnectionResult.SUCCESS) {
            Dialog dlg =
                    GoogleApiAvailability.getInstance().getErrorDialog(this, code, RC_HANDLE_GMS);
            dlg.show();
        }

        if (mCameraSource != null) {
            try {
                mPreview.start(mCameraSource, mGraphicOverlay);
            } catch (IOException e) {
                Log.e(TAG, "Unable to start camera source.", e);
                mCameraSource.release();
                mCameraSource = null;
            }
        }
    }

    /**
     * onTap returns the tapped barcode result to the calling Activity.
     *
     * @param rawX - the raw position of the tap
     * @param rawY - the raw position of the tap.
     * @return true if the activity is ending.
     */
    private boolean onTap(float rawX, float rawY) {
        // Find tap point in preview frame coordinates.
        int[] location = new int[2];
        mGraphicOverlay.getLocationOnScreen(location);
        float x = (rawX - location[0]) / mGraphicOverlay.getWidthScaleFactor();
        float y = (rawY - location[1]) / mGraphicOverlay.getHeightScaleFactor();

        // Find the barcode whose center is closest to the tapped point.
        Barcode best = null;
        float bestDistance = Float.MAX_VALUE;
        for (BarcodeGraphic graphic : mGraphicOverlay.getGraphics()) {
            Barcode barcode = graphic.getBarcode();
            if (barcode.getBoundingBox().contains((int) x, (int) y)) {
                // Exact hit, no need to keep looking.
                best = barcode;
                break;
            }
            float dx = x - barcode.getBoundingBox().centerX();
            float dy = y - barcode.getBoundingBox().centerY();
            float distance = (dx * dx) + (dy * dy);  // actually squared distance
            if (distance < bestDistance) {
                best = barcode;
                bestDistance = distance;
            }
        }

        if (best != null) {
            Intent data = new Intent();
            data.putExtra(BarcodeObject, best);
            setResult(CommonStatusCodes.SUCCESS, data);
            finish();
            return true;
        }
        return false;
    }

    private class CaptureGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            return onTap(e.getRawX(), e.getRawY()) || super.onSingleTapConfirmed(e);
        }
    }

    private class ScaleListener implements ScaleGestureDetector.OnScaleGestureListener {

        /**
         * Responds to scaling events for a gesture in progress.
         * Reported by pointer motion.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should consider this event
         * as handled. If an event was not handled, the detector
         * will continue to accumulate movement until an event is
         * handled. This can be useful if an application, for example,
         * only wants to update scaling factors if the change is
         * greater than 0.01.
         */
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            return false;
        }

        /**
         * Responds to the beginning of a scaling gesture. Reported by
         * new pointers going down.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         * @return Whether or not the detector should continue recognizing
         * this gesture. For example, if a gesture is beginning
         * with a focal point outside of a region where it makes
         * sense, onScaleBegin() may return false to ignore the
         * rest of the gesture.
         */
        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        /**
         * Responds to the end of a scale gesture. Reported by existing
         * pointers going up.
         * <p/>
         * Once a scale has ended, {@link ScaleGestureDetector#getFocusX()}
         * and {@link ScaleGestureDetector#getFocusY()} will return focal point
         * of the pointers remaining on the screen.
         *
         * @param detector The detector reporting the event - use this to
         *                 retrieve extended info about event state.
         */
        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {
            mCameraSource.doZoom(detector.getScaleFactor());
        }
    }

    @Override
    public void onBarcodeDetected(Barcode barcode) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                vibrator.vibrate(100);
                mPreview.stop();
                recyclerViewMethod.setCartItem(11111);
//                setCartItem(11111);
//                Toast.makeText(getApplicationContext(), barcode.displayValue, Toast.LENGTH_LONG).show();
                startCameraSource();
            }
        });
        //do something with barcode data returned
        // TODO 바코드가 캡처되면 여기에서 바코드 코드로 서버에서 상품 정보를 얻어온 다음 ,recyclerview에 뿌려주기
    }

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backKeyPressedTime + 2000) {
            backKeyPressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
//            return;
        } else {
            this.finishAffinity();
        }
    }
}