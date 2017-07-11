package com.ticatag.tibeconnectkit.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ticatag.tibeaconconnect.beans.TicatagBeacon;
import com.ticatag.tibeconnectkit.R;

import java.util.ArrayList;
import java.util.List;


public class BeaconsAdapter extends RecyclerView.Adapter<BeaconsAdapter.BeaconHolder> {

    private List<TicatagBeacon> mBeacons;

    public BeaconsAdapter(ArrayList<TicatagBeacon> beacons) {
        mBeacons = beacons;
    }

    @Override
    public BeaconHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_beacon, parent, false);
        return new BeaconHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(BeaconHolder holder, int position) {
        TicatagBeacon beacon = mBeacons.get(position);
        holder.bindBeacon(beacon);
    }

    @Override
    public int getItemCount() {
        return mBeacons.size();
    }

    public static class BeaconHolder extends RecyclerView.ViewHolder {

        private ImageView mItemImage;
        private TextView mDescriptionTextField;
        private TextView mDetailedTextField;
        private TicatagBeacon mBeacon;

        public BeaconHolder(View v) {
            super(v);

            mItemImage = (ImageView) v.findViewById(R.id.icon);
            mDescriptionTextField = (TextView) v.findViewById(R.id.firstLine);
            mDetailedTextField = (TextView) v.findViewById(R.id.secondLine);
        }

        public void bindBeacon(TicatagBeacon beacon) {
            mBeacon = beacon;
            mDescriptionTextField.setText(mBeacon.getMajor() + " " + mBeacon.getMinor() + " (" + mBeacon.getBluetoothAddress() + ")");
            mDetailedTextField.setText(mBeacon.getRssi() + "dB");
        }

    }

}



