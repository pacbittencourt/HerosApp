package ufjf.heros;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class MarvelQueryActivity extends AppCompatActivity {

    private Button btPesquisa;
    private EditText txtHero;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marvel_query);

        btPesquisa = (Button) findViewById(R.id.btPesquisar);
        txtHero = (EditText) findViewById(R.id.txtHero);

        btPesquisa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nome = txtHero.getText().toString();
                new HttpGetTask().execute(nome);
            }
        });
    }


    private class HttpGetTask extends AsyncTask<String, Void, Hero> {

        private static final String PUBKEY = "f11e16d0eecd30d3a117983d2f635586";



        private String URL = "http://gateway.marvel.com:80/v1/public/characters?name=";

        String result;

        @Override
        protected Hero doInBackground(String... nomeHeroi) {
            URL = URL + nomeHeroi + "&apikey=" + PUBKEY;

            HttpHelper request = new HttpHelper();
            try {
                result = request.doGet(URL);
                return parserJSON(result);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ;
            return null;
        }

        @Override
        protected void onPostExecute(Hero result) {
//HTTP Apache
//            if (null != mClient)
//                mClient.close();
//            setListAdapter(new ArrayAdapter<String>(
//                    Main2Activity.this,
//                    R.layout.activity_main2, result));
        }
    }

    private Hero parserJSON(String JSONResponse) throws IOException, JSONException {

        Hero heroi = new Hero();

        JSONObject responseObject = new JSONObject(JSONResponse);

        // preencher dados do heroi

        return heroi;
    }
}
