package ufjf.heros;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    public final int CREATE_HERO_REQUEST = 1;
    public static final String HERO_TAG = "hero_tag";

    private Button btnCreateHeroActivity;

    private RecyclerView recyclerView;
    private List<Hero> heros;
    private ItemHeroAdapter mAdapter;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference().child("heros");

        heros = new ArrayList<>();

        btnCreateHeroActivity = (Button) findViewById(R.id.btnCreateHeroActivity);
        btnCreateHeroActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it = new Intent(view.getContext(), CreateHeroActivity.class);
                startActivityForResult(it, CREATE_HERO_REQUEST);
            }
        });

        Button btBuscarMarvel = (Button) findViewById(R.id.btBuscarMarvel);
        btBuscarMarvel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(v.getContext(), MarvelQueryActivity.class);
                startActivity(it);
            }
        });

        mAdapter = new ItemHeroAdapter(this,heros);

        recyclerView.setAdapter(mAdapter);


        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Hero hero = dataSnapshot.getValue(Hero.class);
                hero.setHeroKey(dataSnapshot.getKey());
                heros.add(hero);
                mAdapter.notifyDataSetChanged();
                Log.v("PACB",dataSnapshot.toString());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        myRef.addChildEventListener(childEventListener);
        mAdapter.setOnItemClickListener(new ItemHeroAdapter.MyClickListener() {
            @Override
            public void onItemClick(int position, View v) {
                abrirDetalhesHeroi(position);
            }
        });
    }

    private void abrirDetalhesHeroi(int position) {
        Hero hero = heros.get(position);
        Intent it = new Intent(this,DetalhesHero.class);
        it.putExtra(HERO_TAG,hero);
        startActivity(it);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CREATE_HERO_REQUEST) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Herói criado com sucesso", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnMenuSair:
                FirebaseAuth.getInstance().signOut();
                Intent it = new Intent(this,LoginActivity.class);
                startActivity(it);
                break;
        }
        return true;
    }
}
