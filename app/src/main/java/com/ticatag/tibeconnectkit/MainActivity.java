package com.ticatag.tibeconnectkit;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;

import com.ticatag.tibeaconconnect.TiBeaconConnectManager;
import com.ticatag.tibeaconconnect.beans.TicatagBeacon;
import com.ticatag.tibeaconconnect.callback.OnAttachBeaconScanCallback;
import com.ticatag.tibeaconconnect.consumer.BeaconConsumer;
import com.ticatag.tibeconnectkit.adapter.BeaconsAdapter;
import com.ticatag.tibeconnectkit.adapter.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.iwgang.countdownview.CountdownView;


public class MainActivity extends AppCompatActivity implements BeaconConsumer, CountdownView.OnCountdownEndListener {

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @BindView(R.id.bt_main_scan_beacon)
    Button btscan;

    @BindView(R.id.bt_main_attach_beacon)
    Button btAttach;

    @BindView(R.id.cdv_main_count_attach)
    CountdownView countdownView;

    private BeaconsAdapter mBeaconsAdapter;
    private ArrayList<TicatagBeacon> mBeacons = new ArrayList<>();
    boolean toogleScan = false;

    private TiBeaconConnectManager tiBeaconConnectManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        tiBeaconConnectManager = TiBeaconConnectManager.create(this, true);
        tiBeaconConnectManager.checkPermission(this);

        tiBeaconConnectManager.addDeviceToConnect("F8:40:3C:6A:1C:34");

        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        mBeaconsAdapter = new BeaconsAdapter(mBeacons);
        mRecyclerView.setAdapter(mBeaconsAdapter);

        btscan.setEnabled(false);
        btAttach.setEnabled(false);

        countdownView.setOnCountdownEndListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        tiBeaconConnectManager.bindDeviceScan(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        tiBeaconConnectManager.unbindDeviceScan(this);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        tiBeaconConnectManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void unbindService() {
        tiBeaconConnectManager.scanTibeaconAround(false);
    }

    @Override
    public void bindService(int mode) {
        btAttach.setEnabled(true);
        btscan.setEnabled(true);
    }

    @Override
    public void onBeaconsInRange(List<TicatagBeacon> beacons) {
        Log.d("test" , "size : " + beacons.size());
        mBeacons.clear();
        mBeacons.addAll(beacons);
        mBeaconsAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.bt_main_attach_beacon)
    void attach(){
        countdownView.start(15000);
        tiBeaconConnectManager.scanForAttachDevice(15000, false, false, new OnAttachBeaconScanCallback() {
            @Override
            public void OnBeaconResultAttach(TicatagBeacon ticatagBeacon) {

                mBeacons.clear();
                mBeacons.add(ticatagBeacon);
                mBeaconsAdapter.notifyDataSetChanged();

                countdownView.stop();
            }

            @Override
            public void onError(Error errorCode) {

            }
        });
    }

    @OnClick(R.id.bt_main_scan_beacon)
    void scan(){
        toogleScan = !toogleScan;

        tiBeaconConnectManager.scanTibeaconAround(toogleScan);

        btscan.setText(String.valueOf(toogleScan));

        mBeacons.clear();
        mBeaconsAdapter.notifyDataSetChanged();
    }

    @Override
    public void onEnd(CountdownView cv) {

    }
}
