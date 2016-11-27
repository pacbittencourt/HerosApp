package ufjf.heros;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class CreateHeroActivity extends AppCompatActivity {

    private EditText edtName;
    private EditText edtSecretName;
    private EditText edtPowers;
    private RadioGroup radioSex;
    private EditText edtIdade;
    private EditText edtDescription;
    private Button btnCriarHero;

    private DatabaseReference mDatabase;

    private FirebaseUser mUser;

    private Context ctx = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_hero);

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        edtName = (EditText) findViewById(R.id.edtName);
        edtSecretName = (EditText) findViewById(R.id.edtSecretName);
        edtPowers = (EditText) findViewById(R.id.edtPowers);
        radioSex = (RadioGroup) findViewById(R.id.radioSex);
        edtIdade = (EditText) findViewById(R.id.edtIdade);
        edtDescription = (EditText) findViewById(R.id.edtDescription);
        btnCriarHero = (Button) findViewById(R.id.btnCriarHero);

        btnCriarHero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (verificarCampos()) {
                    Hero hero = heroiCriado();
                    registerNewHeroToFirebase(hero);
                    heroiCriadoComSucesso();
                }
            }
        });
    }

    private void heroiCriadoComSucesso() {
        Intent it = new Intent();
        setResult(RESULT_OK,it);
        finish();
    }

    private Hero heroiCriado() {
        Hero hero = new Hero();

        hero.setName(edtName.getText().toString());
        hero.setSecret_id(edtSecretName.getText().toString());
        hero.setDescription(edtDescription.getText().toString());
        hero.setAge(edtIdade.getText().toString());
        int radioButtonId = radioSex.getCheckedRadioButtonId();
        RadioButton r = (RadioButton) findViewById(radioButtonId);
        hero.setSex(r.getText().toString());
        hero.setPowers(edtPowers.getText().toString());
        hero.setEmailCreator(mUser.getEmail());

        return hero;
    }

    private void registerNewHeroToFirebase(Hero hero) {
        String heroKey = mDatabase.child("/heros/").push().getKey();
        Map<String, Object> heroInfos = hero.toMap();

        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/heros/" + heroKey, heroInfos);

        mDatabase.updateChildren(childUpdates);
    }

    public boolean verificarCampos() {
        if (edtName.getText().toString().trim().isEmpty()) {
            Toast.makeText(ctx, "Nome não pode ser vazio. Tente novamente", Toast.LENGTH_LONG).show();
            return false;
        }
        if (edtSecretName.getText().toString().trim().isEmpty()) {
            Toast.makeText(ctx, "Identidade secreta não pode ser vazia. Tente novamente", Toast.LENGTH_LONG).show();
            return false;
        }
        if (edtPowers.getText().toString().trim().isEmpty()) {
            Toast.makeText(ctx, "Poderes não pode ser vazio. Tente novamente", Toast.LENGTH_LONG).show();
            return false;
        }
        if (edtIdade.getText().toString().trim().isEmpty()) {
            Toast.makeText(ctx, "Idade não pode ser vazia. Tente novamente", Toast.LENGTH_LONG).show();
            return false;
        }
        if (edtDescription.getText().toString().trim().isEmpty()) {
            Toast.makeText(ctx, "Desctrição não pode ser vazia. Tente novamente", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }
}
