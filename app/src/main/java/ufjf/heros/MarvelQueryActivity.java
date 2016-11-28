package ufjf.heros;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.security.Timestamp;
import java.util.Date;

public class MarvelQueryActivity extends AppCompatActivity {

    private Button btPesquisa;
    private EditText txtHero;
    private TextView labelNome, labelDesc, labelQuad;
    private TextView txtNome, txtDescricao, txtNumQuadrinhos, txtCopyright;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marvel_query);

        labelNome = (TextView) findViewById(R.id.labelNome);
        labelQuad = (TextView) findViewById(R.id.labelQuad);
        labelDesc = (TextView) findViewById(R.id.labelDesc);

        btPesquisa = (Button) findViewById(R.id.btPesquisar);
        txtHero = (EditText) findViewById(R.id.txtHero);
        txtNome = (TextView) findViewById(R.id.txtNome);
        txtDescricao = (TextView) findViewById(R.id.txtDescricao);
        txtNumQuadrinhos = (TextView) findViewById(R.id.txtNumSeries);
        txtCopyright = (TextView) findViewById(R.id.txtCopyright);

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
        private static final String PRIVKEY = "f57b88659714cb1505bb66b5ffc35c40317db2d8";


        private String URL = "http://gateway.marvel.com:80/v1/public/characters?name=";

        String result;

        @Override
        protected Hero doInBackground(String... nomeHeroi) {
            int time = (int)System.currentTimeMillis();
            Date ts =  new Date(time);
            String hash = MD5(ts.getTime()+PRIVKEY+PUBKEY);
            URL = URL + nomeHeroi[0] + "&apikey=" + PUBKEY + "&ts=" + ts.getTime() + "&hash=" + hash;
            //Log.d("url", URL);
            HttpHelper request = new HttpHelper();
            try {
                result = request.doGet(URL);
                //Log.d("result", result);
                return parserJSON(result);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Hero r) {
            labelNome.setVisibility(View.VISIBLE);
            labelDesc.setVisibility(View.VISIBLE);
            labelQuad.setVisibility(View.VISIBLE);
            txtNome.setText(r.getName());
            txtDescricao.setText(r.getDescription());
            txtNumQuadrinhos.setText(String.valueOf(r.getComicsAmount()));
            txtCopyright.setText("Data provided by Marvel. © 2016 MARVEL");
        }

        public String MD5(String md5) {
            try {
                java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
                byte[] array = md.digest(md5.getBytes());
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < array.length; ++i) {
                    sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
                }
                return sb.toString();
            } catch (java.security.NoSuchAlgorithmException e) {
            }
            return null;
        }
    }

    private Hero parserJSON(String JSONResponse) throws IOException, JSONException {

        Hero heroi = new Hero();

        JSONObject json = new JSONObject(JSONResponse);

        //Log.d("parser", json.toString());

        JSONObject h = json.getJSONObject("data").getJSONArray("results").getJSONObject(0);

        Log.d("heroi", h.toString());

        heroi.setName(h.getString("name"));
        heroi.setDescription(h.getString("description"));

        Log.d("comics", String.valueOf(h.getJSONObject("comics").getInt("available")));
        heroi.setComicsAmount(h.getJSONObject("comics").getInt("available"));

        return heroi;
    }

}
