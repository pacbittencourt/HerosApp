package ufjf.heros;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class DetalhesHero extends AppCompatActivity {

    private TextView txtName, txtSecretName, txtAge, txtPowers, txtSex, txtEmailCreator, txtDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhes_hero);

        Intent intent = getIntent();
        Hero hero = (Hero) intent.getSerializableExtra(MainActivity.HERO_TAG);

        txtAge = (TextView) findViewById(R.id.txtDetalhesAge);
        txtDescription = (TextView) findViewById(R.id.txtDetalhesDescricao);
        txtEmailCreator = (TextView) findViewById(R.id.txtDetalhesCriador);
        txtName = (TextView) findViewById(R.id.txtDetalhesName);
        txtPowers = (TextView) findViewById(R.id.txtDetalhesPoderes);
        txtSecretName = (TextView) findViewById(R.id.txtDetalhesSecretName);
        txtSex = (TextView) findViewById(R.id.txtDetalhesSexo);

        txtSex.setText(hero.getSex());
        txtSecretName.setText(hero.getSecret_id());
        txtPowers.setText(hero.getPowers());
        txtName.setText(hero.getName());
        txtAge.setText(hero.getAge());
        txtDescription.setText(hero.getDescription());
        txtEmailCreator.setText(hero.getEmailCreator());
    }
}
