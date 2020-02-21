package com.example.esercitazionebonus;

import androidx.annotation.Nullable;

import java.io.Serializable;
import java.util.Calendar;

public class Persona implements Serializable {

    private String username, password, cittaProvenienza;
    private Calendar dataNascita;

    public Persona(String username, String password, String cittaProvenienza, String dataNascita){
        this.setUsername(username);
        this.setPassword(password);
        this.setCittaProvenienza(cittaProvenienza);
        //this.setDataNascita(dataNascita);
    }

    public Persona(){
        this.setUsername("");
        this.setPassword("");
        this.setCittaProvenienza("");
        //this.setDataNascita("");
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCittaProvenienza() {
        return cittaProvenienza;
    }

    public void setCittaProvenienza(String cittaProvenienza) {
        this.cittaProvenienza = cittaProvenienza;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setData(Calendar data){
        this.dataNascita = data;
    }

    public Calendar getData(){
        return dataNascita;
    }


    /*
    public String getDataNascita() {
        return dataNascita;
    }

    public void setDataNascita(String dataNascita) {
        this.dataNascita = dataNascita;
    }
*/

    @Override
    public boolean equals(@Nullable Object obj){
        if((obj instanceof Persona)){
            Persona temp = (Persona) obj;
            if(this.getUsername().equals(temp.getUsername())){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

}
