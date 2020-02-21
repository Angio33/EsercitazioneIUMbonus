package com.example.esercitazionebonus;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.io.Serializable;
import java.util.HashMap;

import static com.example.esercitazionebonus.Login.PERSONA_DA_PASSARE;
import static com.example.esercitazionebonus.Login.utenti;

public class ModificaPassword extends AppCompatActivity {

    TextView username, password;
    EditText nuovaPass, confermaPass;
    Button buttonAggPass, buttonHome;

    Persona utente;

    public static final String PERSONA_PASSATA2 = "package com.example.esercitazionebonus";


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modpass);

        username = findViewById(R.id.usernameUt);
        password = findViewById(R.id.passwordUt);
        nuovaPass = findViewById(R.id.nuovaPassword);
        confermaPass = findViewById(R.id.confermaPassword);
        buttonAggPass = findViewById(R.id.buttonAggPassword);
        buttonHome = findViewById(R.id.buttonHomee);

        //Creo l'oggetto Persona
        utente = new Persona();

        //Richiamo l'intent per recuperare i dati dell'utente
        Intent intent = getIntent();
        Serializable obj = intent.getSerializableExtra(PERSONA_DA_PASSARE);

        if (obj instanceof Persona) {
            utente = (Persona) obj;
        } else {
            utente = new Persona();
        }

        username.setText(utente.getUsername());
        password.setText(utente.getPassword());

        buttonAggPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(checkInput() && checkUtente(username.getText().toString(), utenti)) {      //Controllo se ci sono errori nel form di registrazione

                    //Apre il messaggio di conferma uscita
                    AlertDialog diaBox = AskOption();
                    diaBox.show();
                }
            }
        });


        //L'utente clicca sul bottone "HOME"
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                utenti.put(utente.getUsername(), utente);

                //Crea l'oggetto di tipo Intent, ci serve per far comunicare le due activity
                Intent colleg = new Intent( ModificaPassword.this, Home.class);

                //Inserisce l'oggetto persona dentro l'Intent
                colleg.putExtra(PERSONA_DA_PASSARE, utente);

                //Richiama l'activity "activity_home"
                startActivity(colleg);
            }
        });
    }

    private boolean checkInput(){
        boolean flag = true;

        if(nuovaPass.getText() == null || nuovaPass.getText().length() == 0){
            nuovaPass.setError("Inserisci la nuova password!");
            flag = false;
        }else{
            nuovaPass.setError(null);
            confermaPass.setError(null);
        }

        if(confermaPass.getText() == null || confermaPass.getText().length() == 0 || !(nuovaPass.getText().toString().equals(confermaPass.getText().toString()))){
            confermaPass.setError("Le password non coincidono!");
            flag = false;
        }else{
            nuovaPass.setError(null);
            confermaPass.setError(null);
        }

        return flag;
    }

    private boolean checkUtente(String us, HashMap<String, Persona> map){
        boolean flag = true;
        Persona temp;

        if(map.get(us) == null){    //La password inserita è diversa dalla precedente
            nuovaPass.setError(null);
            confermaPass.setError(null);
        }else{                      //La password inserita è uguale alla precedente
            temp = map.get(us);

            if(temp.getPassword().equals(nuovaPass.getText().toString())) {
                nuovaPass.setError("La password inserita è uguale alla precedente");
                flag = false;
            }else{
                nuovaPass.setError(null);
                confermaPass.setError(null);
            }
        }

        return flag;
    }

    /*Questa funzione apre finestre di dialogo, di solito è usata per le allerte , doppia conferma*/
    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                // finestra di conferma eliminazione
                .setTitle("Modifica Password")
                .setMessage("Sei sicuro di voler modificare la password?")


                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        utenti.put(utente.getUsername(), utente);

                        //Sostituisce la password dell'utente
                        utente.setPassword(nuovaPass.getText().toString());

                        //Crea l'oggetto di tipo Intent, ci serve per far comunicare le due activity
                        Intent collegamento = new Intent(ModificaPassword.this, Home.class);

                        //Inserisce l'oggetto persona dentro l'Intent
                        collegamento.putExtra(PERSONA_DA_PASSARE, utente);

                        //Richiama l'activity "activity_home"
                        startActivity(collegamento);

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
}
