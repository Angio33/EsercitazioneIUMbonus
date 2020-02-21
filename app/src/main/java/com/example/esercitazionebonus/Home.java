package com.example.esercitazionebonus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.text.SimpleDateFormat;

import static com.example.esercitazionebonus.Login.PERSONA_DA_PASSARE;
import static com.example.esercitazionebonus.Login.utenti;

public class Home extends AppCompatActivity {

    public Persona utente;

    TextView username, username2, password, cittaProv, dataN, modificaPass;
    Button logout;


    SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

    public static final String PERSONA_PASSATA = "package com.example.esercitazionebonus";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        username = findViewById(R.id.usernameUt);
        username2 = findViewById(R.id.usernameUt2);
        password = findViewById(R.id.password);
        cittaProv = findViewById(R.id.cittaProv);
        dataN = findViewById(R.id.dataN);
        logout = findViewById(R.id.buttonLogin);
        modificaPass = findViewById(R.id.linkModificaPass);

        //Richiamo l'intent per recuperare i dati dell'utente
        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra(PERSONA_DA_PASSARE);

        if (obj instanceof Persona) {
            utente = (Persona) obj;
        } else {
            utente = new Persona();
        }

        username.setText("Benvenuto, " + utente.getUsername());
        username2.setText(utente.getUsername());
        password.setText(utente.getPassword());
        cittaProv.setText(utente.getCittaProvenienza());
        dataN.setText(format.format(utente.getData().getTime()));

        //L'utente preme il bottone "LOGOUT"
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Apre il messaggio di conferma uscita
                AlertDialog diaBox = AskOption();
                diaBox.show();
            }

        });

        //L'utente preme il bottone "MODIFICA PASSWORD"
        modificaPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                utenti.put(utente.getUsername(), utente);

                //Crea il collegamento per far comunicare le activity
                Intent collegamento = new Intent(Home.this, ModificaPassword.class);

                //Inserisce la persona dentro l'intent
                collegamento.putExtra(PERSONA_DA_PASSARE, utente);

                //Richiama l'activity "activity_modpass"
                startActivity(collegamento);
            }
        });
    }

    /*Questa funzione apre finestre di dialogo, di solito è usata per le allerte , doppia conferma*/
    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // finestra di conferma eliminazione
                .setTitle("Logout")
                .setMessage("Sei sicuro di voler effettuare il logout?")


                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        Intent showLogin = new Intent(Home.this, Login.class);
                        startActivity(showLogin);
                        dialog.dismiss();
                        finish();

                    }

                })
                .setNegativeButton("annulla", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();



        return myQuittingDialogBox;
    }

    @Override
    public void onBackPressed() {

        //Apre il messaggio di conferma uscita
        AlertDialog diaBox = AskOption();
        diaBox.show();
    }
}
