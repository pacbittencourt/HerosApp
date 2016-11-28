package ufjf.heros;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Pedro Antonio on 28/11/2016.
 */

public class ConfirmationDialog extends DialogFragment {

    public static final String HERO_KEY_TAG = "hero_key_tag";

    public ConfirmationDialog() {
        //Deixe vazio
    }

    public static ConfirmationDialog newInstance(String heroKey) {
        ConfirmationDialog fragment = new ConfirmationDialog();
        Bundle args = new Bundle();
        args.putString(HERO_KEY_TAG,heroKey);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        AlertDialog.Builder  builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Tem certeza que deseja deletar esse herói?").setPositiveButton("Deletar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = database.getReference().child("heros");
                databaseReference.child(getArguments().getString(HERO_KEY_TAG)).removeValue();
                Intent it = new Intent(getActivity(),MainActivity.class);
                startActivity(it);
            }
        }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        return builder.create();
    }
}
