package com.example.esercitazionebonus;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;

public class PersonaFactory {

    private static PersonaFactory istanza;

    public static  PersonaFactory getInstance(){
        if(istanza == null){
            istanza = new PersonaFactory();
        }

        return istanza;
    }

    public Persona creaPersona(String username, String password, String cittaProvenienza, String dataNascita){
        Persona utente = new Persona();

        utente.setUsername(username);
        utente.setPassword(password);
        utente.setCittaProvenienza(cittaProvenienza);

        return utente;
    }

    public void aggiungiUtente(HashMap<String, Persona> map, Persona persona){
        map.put(persona.getUsername(), persona);
    }

    public ArrayList<Persona> getUtentiArray(HashMap <String, Persona> map){
        Collection <Persona> set = map.values();
        ArrayList <Persona> utenti = new ArrayList<>(set);

        return utenti;
    }

    public  void setPersoneDefault(HashMap<String,Persona> map){
        Persona persona = new Persona();

        persona.setUsername("angio");
        persona.setPassword("123");
        persona.setCittaProvenienza("uta");
        persona.setData(Calendar.getInstance());
        persona.getData().set(Calendar.YEAR,1998);
        persona.getData().set(Calendar.MONTH, Calendar.JANUARY);
        persona.getData().set(Calendar.DAY_OF_MONTH,30);

        map.put(persona.getUsername(),persona);
    }

    //public void rimuoviUtente....

}
