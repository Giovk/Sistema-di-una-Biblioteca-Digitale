package DAO;

import java.sql.ResultSet;

public interface PresentazioneDAO {
    public ResultSet getPresentazioneDB(String isbn);   //ritorna i dati di tutte le presentazioni del libro on ISBN 'isbn' nel DB
    public void chiudiConnessione();    //ritorna i dati di tutti i libri nel DB
}
