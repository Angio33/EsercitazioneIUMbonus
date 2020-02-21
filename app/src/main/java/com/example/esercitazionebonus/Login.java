package com.example.esercitazionebonus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class Login extends AppCompatActivity {

    Button loginButton;
    ImageView image;
    TextView logo_name, registrati, username, password;
    Persona utente;

    static HashMap<String, Persona> utenti = new HashMap<String, Persona>();

    public static final String PERSONA_DA_PASSARE = "package com.example.esercitazionebonus";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        utente = new Persona();

        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        image = findViewById(R.id.logo_login);
        logo_name = findViewById(R.id.logo_name);
        loginButton = findViewById(R.id.buttonLogin);
        registrati = findViewById(R.id.registrati);

        if(utenti==null || utenti.isEmpty()) {
            PersonaFactory.getInstance().setPersoneDefault(utenti);
        }


        //L'utente preme il bottone "Login"
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Controlla se l'utente ha inserito i dati o se i dati inseriti sono validi(Se l'utente esiste)
                if (checkInput() && checkUtente(username.getText().toString(), utenti)) {

                    //Prende la persona dalla lista
                    utente = utenti.get(username.getText().toString());

                    //Crea il collegamento per far comunicare le activity
                    Intent collegamento = new Intent(Login.this, Home.class);

                    //Inserisce la persona dentro l'intent
                    collegamento.putExtra(PERSONA_DA_PASSARE, utente);

                    //Richiama l'activity Home
                    startActivity(collegamento);
                }
            }
        });


        //L'utente preme il link "Registrati"
        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent colleg = new Intent( Login.this, Registrazione.class);

                //Richiama l'activity "activity_register"
                startActivity(colleg);
            }

        });
    }

    private boolean checkInput(){
        boolean flag = true;

        if(username.getText() == null || username.getText().length() == 0){
            username.setError("Inserisci l'username");
            flag = false;
        }else{
            username.setError(null);
        }

        if(password.getText() == null || password.getText().length() == 0){
            password.setError("Inserisci la password");
            flag = false;
        }else{
            password.setError(null);
        }

        return flag;
    }


    private boolean checkUtente(String us, HashMap<String, Persona> map){
        boolean flag = true;
        Persona temp;

        if(map.get(us) == null){    //Utente inesistente
            username.setError("Lo username inserito non esiste");
            flag = false;
        }else{
            temp = map.get(us);
            username.setError(null);
            username.getText();

            //Controllo se la password inserita corrisponde a quella dell'utente
            if(temp.getPassword().equals(password.getText().toString())){     //La password corrisponde
                password.setError(null);
            }else{
                password.setError("La password inserita è errata!");
                flag = false;
            }
        }

        return flag;
    }

}
