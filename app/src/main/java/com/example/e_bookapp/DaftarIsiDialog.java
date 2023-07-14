package com.example.e_bookapp;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import java.util.List;

public class DaftarIsiDialog extends Dialog {
    List<DaftarIsiItem> dataList;

    public DaftarIsiDialog(Context context, List<DaftarIsiItem> dataList){
        super(context);
        this.dataList = dataList;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_isi);

        ListView listView = findViewById(R.id.listView);

        ArrayAdapter<DaftarIsiItem> adapter = new ArrayAdapter<>(getContext(), R.layout.list_item, R.id.title, dataList);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                DaftarIsiItem selectedItem = dataList.get(position);

                //set halaman ketika salah satu daftar isi dipilih
                MainActivity.halaman = selectedItem.getPage();

                dismiss();
            }
        });
    }
}
