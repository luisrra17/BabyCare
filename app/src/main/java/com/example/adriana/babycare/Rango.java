package com.example.adriana.babycare;

    import java.util.ArrayList;
    import java.util.HashMap;
    import java.util.List;
    import android.app.Activity;
    import android.os.Bundle;
    import android.view.Menu;
    import android.view.View;
    import android.widget.AdapterView;
    import android.widget.AdapterView.OnItemClickListener;
    import android.widget.LinearLayout;
    import android.widget.ListView;
    import android.widget.SimpleAdapter;
    import android.widget.TextView;
    import android.widget.Toast;

    public class Rango extends Activity {

        // Array of strings storing country names
        String[] countries = new String[]{
                "3 - 6 MESES",
                "6 - 9 MESES",
                "9 - 12 MESES",
                "12 - 18 MESES",
                "18 - 24 MESES",
                "24 - 36 MESES",
                "36+ MESES",
        };

        // Array of integers points to images stored in /res/drawable-ldpi/
        int[] flags = new int[]{
                R.drawable.b1,
                R.drawable.b2,
                R.drawable.b3,
                R.drawable.b4,
                R.drawable.b5,
                R.drawable.b6,
                R.drawable.b7,
        };


        /**
         * Called when the activity is first created.
         */
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_rango);

            // Each row in the list stores country name, currency and flag
            List<HashMap<String,String>> aList = new ArrayList<HashMap<String,String>>();

            for (int i = 0; i < 7; i++) {
                HashMap<String, String> hm = new HashMap<String, String>();
                hm.put("txt", countries[i]);
                hm.put("cur", "");
                hm.put("flag", Integer.toString(i+1));
                aList.add(hm);
            }

            // Keys used in Hashmap
            String[] from = {"flag", "txt", "cur"};

            // Ids of views in listview_layout
            int[] to = {R.id.flag, R.id.txt, R.id.cur};

            // Instantiating an adapter to store each items
            // R.layout.listview_layout defines the layout of each item
            SimpleAdapter adapter = new SimpleAdapter(getBaseContext(), aList, R.layout.listview_layout, from, to);

            // Getting a reference to listview of main.xml layout file
            ListView listView = (ListView) findViewById(R.id.listviews);

            // Setting the adapter to the listView
            listView.setAdapter(adapter);

            // Item Click Listener for the listview
            AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View container, int position, long id) {
                    // Getting the Container Layout of the ListView
                    LinearLayout linearLayoutParent = (LinearLayout) container;

                    // Getting the inner Linear Layout
                    LinearLayout linearLayoutChild = (LinearLayout) linearLayoutParent.getChildAt(1);

                    // Getting the Country TextView
                    TextView tvCountry = (TextView) linearLayoutChild.getChildAt(0);

                    Toast.makeText(getBaseContext(), tvCountry.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            };

            // Setting the item click listener for the listview
            listView.setOnItemClickListener(itemClickListener);
        }

    }