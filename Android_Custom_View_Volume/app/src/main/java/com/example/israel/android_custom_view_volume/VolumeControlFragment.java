package com.example.israel.android_custom_view_volume;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class VolumeControlFragment extends Fragment {
    private OnFragmentInteractionListener mListener;

    public VolumeControlFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // create volume control view
        VolumeControlView volumeControlView = new VolumeControlView(getActivity()) {
            @Override
            protected void onVolumeLevelChanged(float volumeLevel) {
                super.onVolumeLevelChanged(volumeLevel);
                ((MainActivity) VolumeControlFragment.this.getActivity()).showVolumeLevel(volumeLevel);
            }
        };

        // set volume control view params
        volumeControlView.setMinRot(-60.f);
        volumeControlView.setMaxRot(100.f);
        volumeControlView.setVolumeLevel(30.f);
        volumeControlView.setKnobColor(Color.argb(255,255,255,0));
        volumeControlView.setKnobPointerColor(Color.argb(255,255,0,0));
        volumeControlView.setLevelDeltaScale(1.f);

        return volumeControlView;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
