package com.example.esercitazionebonus;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.DatePicker;

import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class DatePickerFragment extends DialogFragment {

    private Calendar date;      //Calendaro

    private DatePickerFragmentListener listener;

    public Dialog onCreateDialog(Bundle savedInstanceState){
        super.onCreateDialog(savedInstanceState);

        if(date == null){       //Se l'utente non inserisce nulla
            date = Calendar.getInstance();
            date.set(Calendar.YEAR, 1995);
            date.set(Calendar.MONTH, Calendar.JANUARY);
            date.set(Calendar.DAY_OF_MONTH, 1);
        }

        final DatePicker datePicker = new DatePicker (getActivity());

        //E' il dialog (finestra vuota) su cui mettere il DatePicker, contiene anche i bottoni
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        //Qua settiamo il DatePicker nella finestra vuota(dialog)
        builder.setView(datePicker);

        //Quando premiamo Ok
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { //quando premiamo ok stiamo assegnando a date
                date.set(Calendar.YEAR, datePicker.getYear()); //mese, giorno e anno selezionato
                date.set(Calendar.MONTH, datePicker.getMonth());
                date.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());

                if(listener!=null){

                    //Genera l'evento che aggiorna i valori della data nella TextView
                    listener.onDatePickerFragmentOkButton(DatePickerFragment.this, date);
                }
            }
        });

        //Creo tramite il builder il dialog
        return builder.create();
    }

    //Interazione con il picker
    public Calendar getDate(){
        return date;
    }

    public void setDate(Calendar date){
        this.date = date;
    }

    public void setOnDatePickerFragmentChanged(DatePickerFragmentListener l){

        //Questa funzione viene chiamata nella main activity per inizializzare l'attributo listener
        //nella classe DatePickerFragment
        this.listener = l;
    }

    public interface DatePickerFragmentListener{
        public void onDatePickerFragmentOkButton(DialogFragment dialog, Calendar date);
        public void onDatePickerFragmentCancelButton (DialogFragment dialog);
    }
}
