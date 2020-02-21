package com.example.esercitazionebonus;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static com.example.esercitazionebonus.Login.utenti;

public class Registrazione extends AppCompatActivity {

    EditText username, password, confermaPass, cittaProvenienza, dataNascita;
    TextView errorText;
    Persona utente;     //Creo un nuovo oggetto Persona
    Button registrati;

    DatePickerFragment datePickerFragment;

    public static final String PERSONA_EXTRA = "package com.example.esercitazionebonus.Persona";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Creo l'oggetto calendario
        datePickerFragment = new DatePickerFragment();

        //Creo l'oggetto Persona
        utente = new Persona();

        username = findViewById(R.id.inputUsername);
        password = findViewById(R.id.inputPassword);
        confermaPass = findViewById(R.id.inputConfPassword);
        cittaProvenienza = findViewById(R.id.inputCittaProvenienza);
        dataNascita = findViewById(R.id.inputDataNascita);
        registrati = findViewById(R.id.buttonRegistrati);

        registrati.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkInput()) {      //Controllo se ci sono errori nel form di registrazione

                    //Aggiorna i dati dell'utente
                    UpdatePerson();

                    utenti.put(utente.getUsername(), utente);

                    //Crea l'oggetto di tipo Intent, ci serve per far comunicare le due activity
                    Intent collegamento = new Intent(Registrazione.this, Login.class);

                    //Inserisce l'oggetto persona dentro l'Intent
                    collegamento.putExtra(PERSONA_EXTRA, utente);

                    //Richiama l'activity Login
                    startActivity(collegamento);
                }
            }
        });

        //Configurazione eventi Dialog Calendar, vogliamo che quando si clicchi nella data
        //venga aperto il calendario
        dataNascita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //il fragment manager è colui che dirige tutti i fragment
                datePickerFragment.show(getSupportFragmentManager(),"date picker");
            }
        });

        //Questa funzione permette di bloccare l'inserimento di testo nell'editText
        dataNascita.setOnFocusChangeListener(new View.OnFocusChangeListener() { //funzione di view
            @Override
            public void onFocusChange(View v, boolean hasFocus) { //metodo chiamato quando lo stato della view cambia
                if(hasFocus){
                    datePickerFragment.show(getSupportFragmentManager(), "datePicker");
                }
            }
        });

        //Bottoni "Ok" e "Annulla"
        datePickerFragment.setOnDatePickerFragmentChanged(new DatePickerFragment.DatePickerFragmentListener() {
            @Override
            public void onDatePickerFragmentOkButton(DialogFragment dialog, Calendar date) {

                //Associa il comportamento del bottone "Ok" all'editText della data
                //Una volta che l'utente preme il bottone "Ok", viene mostrata
                //nel DatePicker la data selezionata
                SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                dataNascita.setText(format.format(date.getTime()));
            }

            @Override
            public void onDatePickerFragmentCancelButton(DialogFragment dialog) {

            }
        });
    }

    private void UpdatePerson(){
        //Aggiorna i dati della persona usando i dati inseriti nel form
        this.utente.setUsername(this.username.getText().toString());
        this.utente.setPassword(this.password.getText().toString());
        this.utente.setCittaProvenienza(this.cittaProvenienza.getText().toString());
        this.utente.setData(this.datePickerFragment.getDate());
    }

    //Controlla se l'utente ha inserito i dati giusti nel form di registrazione
    //Return true se i dati sono giusti, false altrimenti
    private boolean checkInput() {
        boolean flag = false;

        //CONTROLLO INSERIMENTO USERNAME
        if (username.getText() == null || username.getText().length() == 0) {
            username.setError("Inserisci l'username!");
            flag = true;
        } else {
            username.setError(null);
        }

        //CONTROLLO INSERIMENTO PASSWORD
        if (password.getText() == null || password.getText().length() == 0) {
            password.setError("Inserrisci la password!");
            flag = true;
        } else {
            password.setError(null);
        }

        //CONTROLLO INSERIMENTO CONFERMA PASSWORD
        if ((confermaPass.getText() == null) || (confermaPass.getText().length()) == 0 || !(confermaPass.getText().toString().equals(password.getText().toString()))) {
            confermaPass.setError("Le password non coincidono!");
            flag = true;
        } else {
            confermaPass.setError(null);
        }

        //CONTROLLO INSERIMENTO CITTA' DI PROVENIENZA
        if ((cittaProvenienza.getText() == null) || (cittaProvenienza.getText().length()) == 0) {
            cittaProvenienza.setError("Inserisci la citta' di provenienza!");
            flag = true;
        } else {
            cittaProvenienza.setError(null);
        }

        //CONTROLLO INSERIMENTO DATA DI NASCITA
        if ((dataNascita.getText() == null) || (dataNascita.getText().length()) == 0) {
            dataNascita.setError("Inserisci la data di nascita!");
            flag = true;
        } else {
            Date data = new Date();  //Data di oggi
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");  //Formato di visualizzazione
            String dataAttuale = (String) sdf.format(data);

            if(dataNascita.getText().toString().compareTo(dataAttuale) <  0){
                dataNascita.setError(null);
            }else{
                dataNascita.setError("Inserisci una data di nascita valida!");
                flag = true;
            }
        }

        return flag;
    }
}
