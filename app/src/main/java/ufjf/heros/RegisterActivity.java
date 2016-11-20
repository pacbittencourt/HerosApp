package ufjf.heros;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnRegistrar;
    private EditText edtEmail;
    private EditText edtSenha;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Intent it = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(it);
                } else {

                }
            }
        };

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(this);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtSenha = (EditText) findViewById(R.id.edtPwd);


    }

    @Override
    public void onClick(final View view) {
        String email = edtEmail.getText().toString();
        String senha = edtSenha.getText().toString();
        createUser(email, senha);
    }

    private void createUser(String email, String senha) {
        if (!validarDados(email, senha)) {
            Toast.makeText(this, "Email inválido ou senha com menos de 6 caracteres", Toast.LENGTH_LONG);
        } else {
            final Context t = this;
            mAuth.createUserWithEmailAndPassword(email, senha).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()) {
                        Toast.makeText(t, "Algo errado ao registrar, tente mais tarde", Toast.LENGTH_LONG);
                    } else {
                        Intent it = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(it);
                    }
                }
            });
        }
    }

    private boolean validarDados(String email, String senha) {
        String EMAIL_REGEX = "^(.+)@(.+).com$";
        if (!email.matches(EMAIL_REGEX)) {
            return false;
        }
        if (senha.length() < 4) {
            return false;
        }
        return true;
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
