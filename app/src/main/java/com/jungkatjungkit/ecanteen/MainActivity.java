package com.jungkatjungkit.ecanteen;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize and display the RecyclerView (category section)
        initCategorySection();

        RecyclerView outletList = findViewById(R.id.outletList);
        outletList.setLayoutManager(new GridLayoutManager(this, 1));

        // Set a custom span size lookup to make items span two rows
        ((GridLayoutManager) outletList.getLayoutManager()).setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                return 1; // Each item occupies one span (column)
            }
        });
        outletList.setAdapter(new OutletAdapter());
    }

    // Example method to initialize and display the category section
    private void initCategorySection() {
        RecyclerView categoryRecyclerView = findViewById(R.id.categoryRecyclerView);

        // Create a list of categories (replace these with your actual data)
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(R.drawable.food));
        categories.add(new Category(R.drawable.drink));
        categories.add(new Category(R.drawable.snack));

        // Set up the RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        categoryRecyclerView.setLayoutManager(layoutManager);
        categoryRecyclerView.setHasFixedSize(true); // Disable scrolling

        // Create and set the adapter for the RecyclerView
        CategoryAdapter categoryAdapter = new CategoryAdapter(categories, this);
        categoryRecyclerView.setAdapter(categoryAdapter);
    }
}
