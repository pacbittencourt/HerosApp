package ufjf.heros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button btnCreateHeroActivity;
    private Button btMyHeroesActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnCreateHeroActivity = (Button) findViewById(R.id.btnCreateHeroActivity);
        btnCreateHeroActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(),CreateHeroActivity.class);
                startActivity(it);
            }
        });

        btMyHeroesActivity = (Button) findViewById(R.id.btMyHeroes);
        btMyHeroesActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(),MyHeroesActivity.class);
                startActivity(it);
            }
        });
    }
}
