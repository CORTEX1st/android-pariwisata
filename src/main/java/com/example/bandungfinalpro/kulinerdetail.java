package com.example.bandungfinalpro;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.picasso.Picasso;

public class kulinerdetail extends AppCompatActivity implements OnMapReadyCallback {

    TextView mTitle, mDetail, mAlamat, mWaktu,mcalls;
    ImageView mImage, direction,calls;
    private GoogleMap mMap;
    private Double latitude, longitude;
    private String judul_lokasi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kulinerdetail);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mTitle = findViewById(R.id.title);
        mDetail = findViewById(R.id.desc);
        mImage = findViewById(R.id.image);
        mAlamat = findViewById(R.id.alamat);
        mWaktu = findViewById(R.id.waktu);
        mcalls = findViewById(R.id.calls);


        String image = getIntent().getStringExtra("gambar");
        String title = getIntent().getStringExtra("nama");
        String desc = getIntent().getStringExtra("keterangan");
        String alamat = getIntent().getStringExtra("alamat");
        String waktu = getIntent().getStringExtra("waktuoperasi");
        String jam = getIntent().getStringExtra("call");
        judul_lokasi  = getIntent().getExtras().getString("Nama Tempat");
        latitude = Double.parseDouble(getIntent().getExtras().getString("latitude"));
        longitude = Double.parseDouble(getIntent().getExtras().getString("longitude"));

        final String notelp = getIntent().getExtras().getString("Call");
        calls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (notelp.equals(" ")) {
                    Toast.makeText(kulinerdetail.this, "Nomor Telepon Tidak Tersedia", Toast.LENGTH_SHORT).show();
                } else {
                    Intent i = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + notelp));
                    if (ActivityCompat.checkSelfPermission(kulinerdetail.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    startActivity(i);
                }
            }
        });


        direction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i= new Intent(Intent.ACTION_VIEW, Uri.parse("https://wwww.google.co.id/maps/dir/?api=1&destination=" + latitude.toString()+","+longitude.toString()));
                startActivity(i);
            }
        });


        mcalls.setText(jam);
        mTitle.setText(title);
        mAlamat.setText(alamat);
        mDetail.setText(desc);
        mWaktu.setText(waktu);
        Picasso.get().load(image).into(mImage);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        LatLng lokasi = new LatLng(latitude,longitude);
        mMap.addMarker(new MarkerOptions().position(lokasi).title(judul_lokasi));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(lokasi));
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lokasi, 15));

    }
}
