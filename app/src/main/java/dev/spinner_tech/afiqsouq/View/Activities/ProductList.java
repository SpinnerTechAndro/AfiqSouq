package dev.spinner_tech.afiqsouq.View.Activities;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import dev.spinner_tech.afiqsouq.R;

public class ProductList extends AppCompatActivity {

    RecyclerView recyclerView  ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        recyclerView = findViewById(R.id.pList) ;


    }
}