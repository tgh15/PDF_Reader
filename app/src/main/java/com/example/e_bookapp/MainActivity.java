package com.example.e_bookapp;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public static Integer halaman = 0; //variabel penampung halaman
    public EditText cariHal;
    public Button btnCari;
    ImageButton btnPrev, btnNext, btnDaftarIsi;
    AlertDialog.Builder dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //load list halaman
        List<DaftarIsiItem> dataList = getDataFromJson();

        //cari halaman
        cariHal = findViewById(R.id.cari);
        cariHal.setText(halaman.toString());

        btnCari = findViewById(R.id.btnCari);
        btnCari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hal = cariHal.getText().toString();
                halaman = Integer.parseInt(hal);
                display(halaman + 5);
            }
        });

        //kembali
        btnPrev = findViewById(R.id.prev);
        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(halaman == 0) return;
                halaman--;
                cariHal.setText(halaman.toString());
                display(halaman+5);
            }
        });

        //selanjutnya
        btnNext = findViewById(R.id.btnNext);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                halaman++;
                cariHal.setText(halaman.toString());
                display(halaman+5);
            }
        });

        //daftar isi
        btnDaftarIsi = findViewById(R.id.btnDaftarIsi);
        btnDaftarIsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DaftarIsiDialog dialog = new DaftarIsiDialog(MainActivity.this, dataList);
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        cariHal.setText(halaman.toString());
                        if (halaman == 0) {
                            display(halaman);
                        }else{
                            display(halaman+5);
                        }
                    }
                });
                dialog.show();
            }
        });

        display(halaman);
    }
    //fungsi menampilkan PDF
    public void display(int hal){
        PDFView pdfView = findViewById(R.id.pdfView);

        pdfView.fromAsset("bindo.pdf")
                .defaultPage(hal )
                .swipeHorizontal(true)
                .pageSnap(true)
                .autoSpacing(true)
                .pageFling(true)
                .load();
    }
    //fungsi membaca file JSON
    private List<DaftarIsiItem> getDataFromJson() {
        List<DaftarIsiItem> dataList = new ArrayList<>();

        try {
            InputStream inputStream = getAssets().open("daftarisi.json");
            int size = inputStream.available();
            byte[] buffer = new byte[size];
            inputStream.read(buffer);
            inputStream.close();
            String json = new String(buffer, StandardCharsets.UTF_8);

            JSONArray jsonArray = new JSONArray(json);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String title = jsonObject.getString("title");
                String description = jsonObject.getString("description");
                int page = jsonObject.getInt("page");
                DaftarIsiItem daftarIsiItem = new DaftarIsiItem(title, description, page);
                dataList.add(daftarIsiItem);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        return dataList;
    }

}