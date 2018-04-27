package wegepunkte.sabel.com.wegepunkte;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.Serializable;

public class ListViewActivity extends AppCompatActivity {

    private ListView listView;
    private WegepunkteRepo wegepunkteRepo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);
        listView = findViewById(R.id.lvListe);

        Serializable serializable = getIntent().getSerializableExtra("WEGEPUNKTE");

        if (serializable instanceof WegepunkteRepo) {
            wegepunkteRepo = (WegepunkteRepo) serializable;
            ArrayAdapter<Wegepunkt> arrayAdapter = new ArrayAdapter<Wegepunkt>(this, android.R.layout.simple_list_item_1,wegepunkteRepo.getWegepunkte());
            listView.setAdapter(arrayAdapter);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(ListViewActivity.this,listView.getItemAtPosition(i).toString(),  Toast.LENGTH_LONG).show();

                Wegepunkt wegepunkt = wegepunkteRepo.getWegepunkt(i);

                double lat = wegepunkt.getLat();
                double lon = wegepunkt.getLon();

                Intent bwoserInten = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/maps/search/?api=1&query" + lat + "," + lon));

                startActivity(bwoserInten);

            }
        });

    }
}
