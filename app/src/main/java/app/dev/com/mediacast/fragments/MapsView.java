package app.dev.com.mediacast.fragments;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import app.dev.com.mediacast.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapsView extends Fragment implements OnMapReadyCallback{

    MapView mMapView;
    View mView;
    String latlong;
    public static GoogleMap mapa;
    public MapsView() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public MapsView(String latlong) {
        this.latlong = latlong;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_maps_view, container, false);


        /*SupportMapFragment mfragment = (SupportMapFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.mapa);
        mfragment.getMapAsync(this);*/
        //setLocationPoint();
        return mView;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMapView = (MapView) mView.findViewById(R.id.mapavista);
        if(mMapView!=null){
            mMapView.onCreate(null);
            mMapView.onResume();
            mMapView.getMapAsync(this);
        }



    }

    @Override
    public void onMapReady(GoogleMap map) {
        mapa = map;
        //LatLng santiago = new LatLng(-33.400601, -70.651336);
        //mapa.moveCamera(CameraUpdateFactory.newLatLngZoom(santiago,15));

        setLocationPoint(this.latlong);

        /*map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));*/
    }

    public void setLocationPoint(String latlng){

        String[] parts = latlng.trim().split(",");
        Double lat_ = Double.parseDouble(parts[0]);
        Double long_ = Double.parseDouble(parts[1]);

        LatLng pos = new LatLng(lat_,long_);
        mapa.clear();

        mapa.addMarker(new MarkerOptions()
                .position(pos)
                .title("Punto GPS"));

        mapa.animateCamera(CameraUpdateFactory.newLatLngZoom(pos,14));
    }
}

