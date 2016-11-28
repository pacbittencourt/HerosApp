package ufjf.heros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DetalhesHero extends AppCompatActivity {

    public static final String TAG = "tag_dialog";

    private TextView txtName, txtSecretName, txtAge, txtPowers, txtSex, txtEmailCreator, txtDescription;
    private Button btnDeletarHeroi;



    private String heroKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_hero);

        final Intent intent = getIntent();
        Hero hero = (Hero) intent.getSerializableExtra(MainActivity.HERO_TAG);

        txtAge = (TextView) findViewById(R.id.txtDetalhesAge);
        txtDescription = (TextView) findViewById(R.id.txtDetalhesDescricao);
        txtEmailCreator = (TextView) findViewById(R.id.txtDetalhesCriador);
        txtName = (TextView) findViewById(R.id.txtDetalhesName);
        txtPowers = (TextView) findViewById(R.id.txtDetalhesPoderes);
        txtSecretName = (TextView) findViewById(R.id.txtDetalhesSecretName);
        txtSex = (TextView) findViewById(R.id.txtDetalhesSexo);

        btnDeletarHeroi = (Button) findViewById(R.id.btnDeletarHero);

        heroKey = hero.getHeroKey();

        btnDeletarHeroi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfirmationDialog dialog = ConfirmationDialog.newInstance(heroKey);
                dialog.show(getFragmentManager().beginTransaction(),TAG);
                //databaseReference.child(heroKey).removeValue();
            }
        });

        txtSex.setText(hero.getSex());
        txtSecretName.setText(hero.getSecret_id());
        txtPowers.setText(hero.getPowers());
        txtName.setText(hero.getName());
        txtAge.setText(hero.getAge());
        txtDescription.setText(hero.getDescription());
        txtEmailCreator.setText(hero.getEmailCreator());

    }

}
