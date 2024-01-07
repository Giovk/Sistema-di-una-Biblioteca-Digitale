package ImplementazionePostgresDAO;

import DAO.RecensioneDAO;
import Database.ConnessioneDatabase;

import java.sql.*;
import java.util.ArrayList;

public class RecensioneImplementazionePostgresDAO implements RecensioneDAO {
    private Connection connection;

    public  RecensioneImplementazionePostgresDAO(){
        try {
            connection = ConnessioneDatabase.getInstance().connection;
        }
        catch (SQLException var2){
            var2.printStackTrace();
        }
    }

    @Override
    public float valutazioneMediaLibroDB(String isbn) { //ritorna la media delle valutazioni del libro con isbn 'isbn'
        ResultSet rs = null;    //contiene la media trovata
        float vm = 0;   //valore medio delle valutazioni

        try {
            PreparedStatement valutazioneMediaLibroPS = connection.prepareStatement(
                    "SELECT AVG(valutazione) FROM recensione_l WHERE isbn = '"+isbn+"'" //prepara la query che calcola il valore medio delle valutazioni del libro con isbn 'isbn'
            );

            rs = valutazioneMediaLibroPS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs' contenente la media
                vm = rs.getFloat(1);    //aggiorna 'vm'
            }

            rs.close(); //chiude 'rs'
            connection.close(); //chiude la connessione al DB
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return vm;
    }//fine valutazioneMediaLibroDB

    @Override
    public boolean likeLibroDB(String isbn, String user) { //controlla se l'utente 'user' ha il libro con ISBN 'isbn' tra i preferiti
        ResultSet rs = null;    //valore trovato
        boolean like = false;   //risultato

        try {
            PreparedStatement likeLibroPS = connection.prepareStatement(
                    "SELECT preferito FROM recensione_l WHERE isbn = '"+isbn+"' AND username = '"+user+"'" //prepara la query che controlla se l'utente ha il libro nei preferiti
            );

            rs = likeLibroPS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs'
                like = rs.getBoolean(1);    //aggiorna 'like'
            }

            rs.close(); //chiude 'rs'
            connection.close(); //chiude la connessione al DB
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return like;
    }//fine likeLibroDB

    @Override
    public boolean changeLikeLibroDB(boolean like, String isbn, String user) {  //toglie/mette nei preferiti dell'utente 'user' il libro con ISBN 'isbn' e ritorna l'opposto di 'like'
        ResultSet rs = null;    //contiene il numero di recensioni fatte da 'user' al libro con ISBN 'isbn'
        int item = 1;   //numero di recensioni fatte da 'user' al libro con ISBN 'isbn'

        if (like == true){  //controlla se 'user' ha il libro con ISBN 'isbn' nei preferiti
            like = false;   //aggiorna 'like'
        }else{
            like = true;    //aggiorna 'like'
        }

        try {
            PreparedStatement changeLikeLibroPS = connection.prepareStatement(
                    "SELECT COUNT (*) FROM recensione_l WHERE isbn = '"+isbn+"' AND username = '"+user+"'"  //prepara la query che conta il numero di recensioni fatte da 'user' al libro con ISBN 'isbn'
            );

            rs = changeLikeLibroPS.executeQuery();  //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs' contenente il numero di recensioni fatte da 'user' al libro con ISBN 'isbn'
                item = rs.getInt(1);    //aggiorna 'item'
            }

            rs.close(); //chiude 'rs'
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        if(item < 1) { //controlla se non c'è una recensione del libro con ISBN 'isbn' fatta da 'user'
            try {
                PreparedStatement changeLikePS = connection.prepareStatement(
                        "INSERT INTO recensione_l(username, isbn, preferito) " +
                                "VALUES ('"+user+"', '"+isbn+"', '"+like+"')"   //prepara la query che aggiuge una nuova recensione del libro con ISBN 'isbn' fatta da 'user'
                );

                changeLikePS.executeUpdate(); //esegue la query
                connection.close(); //chiude la connessione al DB
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        } else {
            if(like == false) { //controlla se 'user' non ha messo il libro con ISBN 'isbn' nei preferiti
                try {
                    PreparedStatement changeLikePS = connection.prepareStatement(
                            "DELETE from recensione_l WHERE isbn = '"+isbn+"' AND username = '"+user+"' AND valutazione IS NULL AND testo IS NULL"  //elimina la recensione del libro con ISBN 'isbn' fatta da 'user'
                    );

                    changeLikePS.executeUpdate(); //esegue la query
                } catch (SQLException var2) {
                    var2.printStackTrace();
                }
            }

            try {
                PreparedStatement changeLikePS = connection.prepareStatement(
                        "UPDATE recensione_l SET preferito = '" + like + "' WHERE isbn = '" + isbn + "' AND username = '" + user + "'" //prepara la query che aggiunge il libro con ISBN 'isbn' nei preferiti di 'user'
                );

                changeLikePS.executeUpdate();   //esegue la query
                connection.close(); //chiude la connessione al DB
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }

        return like;
    }//fine changeLikeLibroDB

    @Override
    public void addRecensioneLibroDB(int valutazione, String text, String isbn, String user){   //aggiunge/aggiorna una recensione con 'valutazione' e 'testo' fatta dall'utente 'user' al libro con ISBN 'isbn'
        ResultSet rs = null;
        int item = 1;   //numero di tuple in "recensione_l" con 'user' e 'isbn'

        try {
            PreparedStatement likeLibroPS = connection.prepareStatement(
                    "SELECT COUNT (*) FROM recensione_l WHERE isbn = '"+isbn+"' AND username = '"+user+"'" //prepara la query che conta il numero di tuple con 'user' e 'isbn'
            );

            rs = likeLibroPS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs'
                item = rs.getInt(1);    //aggiorna 'item'
            }

            rs.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        if(item >= 1) { //controlla se c'è già una tupla con 'user' e 'isbn' in "recensione_l"
            try {
                String query = "UPDATE recensione_l SET valutazione = ?, testo = ? WHERE isbn = ? AND username = ?"; //prepara la query di aggiornamento
                PreparedStatement addRecensioneLibroPS = connection.prepareStatement(query);

                addRecensioneLibroPS.setInt(1, valutazione);    //inserisce la valutazione nella query

                if(text.isBlank()){
                    addRecensioneLibroPS.setNull(2, Types.NULL); //se il testo è vuoto inserisce NULL nella query
                } else {
                    addRecensioneLibroPS.setString(2, text);   //altrimenti inserise 'text' nella query
                }

                addRecensioneLibroPS.setString(3, isbn);    //inserisce l'isbn nella query
                addRecensioneLibroPS.setString(4, user);    //inserisce l'username nella query

                addRecensioneLibroPS.executeUpdate();   //esegue la query
                connection.close();
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        } else {
            try {
                String query = "INSERT INTO recensione_l(username, isbn, valutazione, testo) VALUES (?, ?, ?, ?)"; //prepara la query di inserimento
                PreparedStatement addRecensioneLibroPS = connection.prepareStatement(query);

                addRecensioneLibroPS.setInt(3, valutazione);    //prepara la query di aggiornamento

                if(text.isBlank()){
                    addRecensioneLibroPS.setNull(4, Types.NULL); //se il testo è vuoto inserisce NULL nella query
                }else{
                    addRecensioneLibroPS.setString(4, text);    //altrimenti inserise 'text' nella query
                }

                addRecensioneLibroPS.setString(2, isbn);     //inserisce l'isbn nella query
                addRecensioneLibroPS.setString(1, user);     //inserisce l'username nella query

                addRecensioneLibroPS.executeUpdate();   //esegue la query
                connection.close();
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }
    }

    public ResultSet allRecWithCommentLibroDB(String isbn){  //ritorna tutte le recensioni con un testo fatte al libro 'isbn'
        ResultSet rs = null;    //contiene le recensioni

        try {
            PreparedStatement allRecWithCommentPS = connection.prepareStatement(
                    "SELECT * FROM recensione_l WHERE isbn = '"+isbn+"' AND testo IS NOT NULL AND VALUTAZIONE IS NOT NULL"   //prepara la query che cerca tutte le recensioni del libro
            );

            rs = allRecWithCommentPS.executeQuery(); //esegue la query
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return rs;
    }

    @Override
    public float valutazioneMediaSerieDB(String isbn) { //ritorna la media delle valutazioni del libro con isbn 'isbn'
        ResultSet rs = null;    //media trovata
        float vm = 0;   //valore medio delle valutazioni

        try {
            PreparedStatement valutazioneMediaSeriePS = connection.prepareStatement(
                    "SELECT AVG(valutazione) FROM recensione_s WHERE isbn = '"+isbn+"'" //prepara la query che calcola il valore medio delle valutazioni del libro
            );
            rs = valutazioneMediaSeriePS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs' contenente la media
                vm = rs.getFloat(1);
            }

            rs.close();
            connection.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return vm;
    }

    @Override
    public boolean likeSerieDB(String isbn, String user) { //controlla se l'utente 'user' ha il libro 'isbn' tra i preferiti
        ResultSet rs = null;    //valore trovato
        boolean like = false;   //risultato

        try {
            PreparedStatement likeSeriePS = connection.prepareStatement(
                    "SELECT preferito FROM recensione_s WHERE isbn = '"+isbn+"' AND username = '"+user+"'" //prepara la query che controlla se l'utente ha il libro nei preferiti
            );
            rs = likeSeriePS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs'
                like = rs.getBoolean(1);    //pone il risultato in 'like'
            }

            rs.close();
            connection.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return like;
    }

    @Override
    public boolean changeLikeSerieDB(boolean like, String isbn, String user) { //toglie/mette nei preferiti dell'utente 'user' il libro 'isbn' e ritone l'opposto di 'like'
        ResultSet rs = null;
        int item = 1;   //numero di tuple in "recensione_l" con 'user' e 'isbn'

        if (like == true) like = false; //se 'like' è true lo pone a false
        else like = true;   //altrimenti lo pone a true

        try {
            PreparedStatement changeLikeSeriePS = connection.prepareStatement(
                    "SELECT COUNT (*) FROM recensione_s WHERE isbn = '"+isbn+"' AND username = '"+user+"'" //prepara la query che conta il numero di tuple con 'user' e 'isbn'
            );
            rs = changeLikeSeriePS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs' contenente il numero di tuple trovate dalla query
                item = rs.getInt(1);    //aggiorna 'item'
            }

            rs.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        if(item >= 1) { //controlla se c'è già una tupla con 'user' e 'isbn' in "recensione_l"
            if(like == false) {
                try {
                    PreparedStatement changeLikePS = connection.prepareStatement(
                            "DELETE from recensione_s WHERE isbn = '"+isbn+"' AND username = '"+user+"' AND valutazione IS NULL AND testo IS NULL"
                    );
                    changeLikePS.executeUpdate(); //esegue la query
                } catch (SQLException var2) {
                    var2.printStackTrace();
                }
            }
            try {
                PreparedStatement changeLikeSeriePS = connection.prepareStatement(
                        "UPDATE recensione_s SET preferito = '" + like + "' WHERE isbn = '" + isbn + "' AND username = '" + user + "'" //prepara la query che aggiorna la tupla con 'isbn' e 'user'
                );
                changeLikeSeriePS.executeUpdate(); //esegue la query
                connection.close();
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        } else {
            try {
                PreparedStatement changeLikeSeriePS = connection.prepareStatement(
                        "INSERT INTO recensione_s(username, isbn, preferito) " +
                                "VALUES ('"+user+"', '"+isbn+"', '"+like+"')"   //prepara la query che aggiuge una nuova tupla in "recensione_l" con 'user', 'isbn' e 'like'
                );
                changeLikeSeriePS.executeUpdate(); //esegue la query
                connection.close();
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }

        return like;
    }

    @Override
    public void addRecensioneSerieDB(int valutazione, String text, String isbn, String user){   //aggiunge/aggiorna una recensione con 'valutazione' e 'testo' fatta dall'utente 'user' al libro 'isbn'
        ResultSet rs = null;
        int item = 1;   //numero di tuple in "recensione_l" con 'user' e 'isbn'

        try {
            PreparedStatement addRecensioneSeriePS = connection.prepareStatement(
                    "SELECT COUNT (*) FROM recensione_s WHERE isbn = '"+isbn+"' AND username = '"+user+"'" //prepara la query che conta il numero di tuple con 'user' e 'isbn'
            );
            rs = addRecensioneSeriePS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs'
                item = rs.getInt(1);    //aggiorna 'item'
            }

            rs.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        if(item >= 1) { //controlla se c'è già una tupla con 'user' e 'isbn' in "recensione_l"
            try {
                String query = "UPDATE recensione_s SET valutazione = ?, testo = ? WHERE isbn = ? AND username = ?"; //prepara la query di aggiornamento
                PreparedStatement addRecensioneSeriePS = connection.prepareStatement(query);

                addRecensioneSeriePS.setInt(1, valutazione);    //inserisce la valutazione nella query

                if(text.isBlank()) addRecensioneSeriePS.setNull(2, Types.NULL); //se il testo è vuoto inserisce NULL nella query
                else addRecensioneSeriePS.setString(2, text);   //altrimenti inserise 'text' nella query

                addRecensioneSeriePS.setString(3, isbn);    //inserisce l'isbn nella query
                addRecensioneSeriePS.setString(4, user);    //inserisce l'username nella query

                addRecensioneSeriePS.executeUpdate();   //esegue la query
                connection.close();
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        } else {
            try {
                String query = "INSERT INTO recensione_s(username, isbn, valutazione, testo) VALUES (?, ?, ?, ?)"; //prepara la query di inserimento
                PreparedStatement addRecensioneSeriePS = connection.prepareStatement(query);

                addRecensioneSeriePS.setInt(3, valutazione);    //prepara la query di aggiornamento

                if(text.isBlank()) addRecensioneSeriePS.setNull(4, Types.NULL); //se il testo è vuoto inserisce NULL nella query
                else addRecensioneSeriePS.setString(4, text);    //altrimenti inserise 'text' nella query

                addRecensioneSeriePS.setString(2, isbn);     //inserisce l'isbn nella query
                addRecensioneSeriePS.setString(1, user);     //inserisce l'username nella query

                addRecensioneSeriePS.executeUpdate();   //esegue la query
                connection.close();
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }
    }

    @Override
    public ResultSet allRecWithCommentSerieDB(String isbn){  //ritorna tutte le recensioni con un testo fatte al libro 'isbn'
        ResultSet rs = null;    //contiene le recensioni

        try {
            PreparedStatement allRecWithCommentPS = connection.prepareStatement(
                    "SELECT * FROM recensione_s WHERE isbn = '"+isbn+"' AND testo IS NOT NULL AND VALUTAZIONE IS NOT NULL"   //prepara la query che cerca tutte le recensioni del libro
            );

            rs = allRecWithCommentPS.executeQuery(); //esegue la query
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return rs;
    }

    @Override
    public float valutazioneMediaFascicoloDB(int numero, String titolo) { //ritorna la media delle valutazioni del libro con isbn 'isbn'
        ResultSet rs = null;    //media trovata
        float vm = 0;   //valore medio delle valutazioni

        try {
            PreparedStatement valutazioneMediaFascicoloPS = connection.prepareStatement(
                    "SELECT AVG(valutazione) FROM recensione_f NATURAL JOIN fascicolo NATURAL JOIN rivista WHERE rivista.titolo = '"+titolo+"' AND fascicolo.numero = '"+numero+"';" //prepara la query che calcola il valore medio delle valutazioni del libro
            );
            rs = valutazioneMediaFascicoloPS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs' contenente la media
                vm = rs.getFloat(1);
            }

            rs.close();
            connection.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return vm;
    }

    @Override
    public boolean likeFascicoloDB(int numero, String titolo, String user) { //controlla se l'utente 'user' ha il libro 'isbn' tra i preferiti
        ResultSet rs = null;    //valore trovato
        boolean like = false;   //risultato

        try {
            PreparedStatement likeFascicoloPS = connection.prepareStatement(
                    "SELECT preferito FROM recensione_f NATURAL JOIN fascicolo NATURAL JOIN rivista WHERE rivista.titolo = '"+titolo+"' AND fascicolo.numero = '"+numero+"' AND username = '"+user+"'" //prepara la query che controlla se l'utente ha il libro nei preferiti
            );
            rs = likeFascicoloPS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs'
                like = rs.getBoolean(1);    //pone il risultato in 'like'
            }

            rs.close();
            connection.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return like;
    }

    @Override
    public boolean changeLikeFascicoloDB(boolean like, int numero, String titolo, String user) { //toglie/mette nei preferiti dell'utente 'user' il libro 'isbn' e ritone l'opposto di 'like'
        ResultSet rs = null;
        int item = 1;   //numero di tuple in "recensione_l" con 'user' e 'isbn'
        int codF = -1;

        if (like == true) like = false; //se 'like' è true lo pone a false
        else like = true;   //altrimenti lo pone a true

        try {
            PreparedStatement changeLikeFascicoloPS = connection.prepareStatement(
                    "SELECT COUNT (*) FROM recensione_f NATURAL JOIN fascicolo NATURAL JOIN rivista WHERE rivista.titolo = '"+titolo+"' AND fascicolo.numero = '"+numero+"' AND username = '"+user+"'" //prepara la query che conta il numero di tuple con 'user' e 'isbn'
            );
            rs = changeLikeFascicoloPS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs' contenente il numero di tuple trovate dalla query
                item = rs.getInt(1);    //aggiorna 'item'
            }

            rs.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        try {
            PreparedStatement changeLikeFascicoloPS = connection.prepareStatement(
                    "SELECT * FROM fascicolo NATURAL JOIN rivista WHERE rivista.titolo = '"+titolo+"' AND fascicolo.numero = '"+numero+"'" //prepara la query che conta il numero di tuple con 'user' e 'isbn'
            );
            rs = changeLikeFascicoloPS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs'
                codF = rs.getInt("codf");    //aggiorna 'item'
            }

            rs.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        if(item >= 1) { //controlla se c'è già una tupla con 'user' e 'isbn' in "recensione_l"
            if(like == false) {
                try {
                    PreparedStatement changeLikePS = connection.prepareStatement(
                            "DELETE from recensione_f WHERE codf = '"+codF+"' AND username = '"+user+"' AND valutazione IS NULL AND testo IS NULL"
                    );
                    changeLikePS.executeUpdate(); //esegue la query
                } catch (SQLException var2) {
                    var2.printStackTrace();
                }
            }
            try {
                PreparedStatement changeLikeFascicoloPS = connection.prepareStatement(
                        "UPDATE recensione_f SET preferito = '" + like + "' WHERE codf = '" + codF + "' AND username = '" + user + "'" //prepara la query che aggiorna la tupla con 'isbn' e 'user'
                );
                changeLikeFascicoloPS.executeUpdate(); //esegue la query
                connection.close();
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        } else {
            try {
                PreparedStatement changeLikeFascicoloPS = connection.prepareStatement(
                        "INSERT INTO recensione_f(username, codf, preferito) " +
                                "VALUES ('"+user+"', '"+codF+"', '"+like+"')"   //prepara la query che aggiuge una nuova tupla in "recensione_l" con 'user', 'isbn' e 'like'
                );
                changeLikeFascicoloPS.executeUpdate(); //esegue la query
                connection.close();
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }

        return like;
    }

    @Override
    public void addRecensioneFascicoloDB(int valutazione, String text, int numero, String titolo, String user){   //aggiunge/aggiorna una recensione con 'valutazione' e 'testo' fatta dall'utente 'user' al libro 'isbn'
        ResultSet rs = null;
        int item = 1;   //numero di tuple in "recensione_l" con 'user' e 'isbn'
        int codF = -1;

        try {
            PreparedStatement addRecensioneFascicoloPS = connection.prepareStatement(
                    "SELECT COUNT (*) FROM recensione_f NATURAL JOIN fascicolo NATURAL JOIN rivista WHERE rivista.titolo = '"+titolo+"' AND fascicolo.numero = '"+numero+"' AND username = '"+user+"'" //prepara la query che conta il numero di tuple con 'user' e 'isbn'
            );
            rs = addRecensioneFascicoloPS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs'
                item = rs.getInt(1);    //aggiorna 'item'
            }

            rs.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }


        try {
            PreparedStatement addRecensioneFascicoloPS = connection.prepareStatement(
                    "SELECT * FROM recensione_f NATURAL JOIN fascicolo NATURAL JOIN rivista WHERE rivista.titolo = '"+titolo+"' AND fascicolo.numero = '"+numero+"' AND username = '"+user+"'" //prepara la query che conta il numero di tuple con 'user' e 'isbn'
            );
            rs = addRecensioneFascicoloPS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs'
                codF = rs.getInt("codF");    //aggiorna 'item'
            }

            rs.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        if(item >= 1) { //controlla se c'è già una tupla con 'user' e 'isbn' in "recensione_l"
            try {
                String query = "UPDATE recensione_f SET valutazione = ?, testo = ? WHERE codf = ? AND username = ?"; //prepara la query di aggiornamento
                PreparedStatement addRecensioneFascicoloPS = connection.prepareStatement(query);

                addRecensioneFascicoloPS.setInt(1, valutazione);    //inserisce la valutazione nella query

                if(text.isBlank()) addRecensioneFascicoloPS.setNull(2, Types.NULL); //se il testo è vuoto inserisce NULL nella query
                else addRecensioneFascicoloPS.setString(2, text);   //altrimenti inserise 'text' nella query

                addRecensioneFascicoloPS.setInt(3, codF);    //inserisce l'isbn nella query
                addRecensioneFascicoloPS.setString(4, user);    //inserisce l'username nella query

                addRecensioneFascicoloPS.executeUpdate();   //esegue la query
                connection.close();
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        } else {
            try {
                String query = "INSERT INTO recensione_f(username, codF, valutazione, testo) VALUES (?, ?, ?, ?)"; //prepara la query di inserimento
                PreparedStatement addRecensioneFascicoloPS = connection.prepareStatement(query);

                addRecensioneFascicoloPS.setInt(3, valutazione);    //prepara la query di aggiornamento

                if(text.isBlank()) addRecensioneFascicoloPS.setNull(4, Types.NULL); //se il testo è vuoto inserisce NULL nella query
                else addRecensioneFascicoloPS.setString(4, text);    //altrimenti inserise 'text' nella query

                addRecensioneFascicoloPS.setInt(2, codF);     //inserisce l'isbn nella query
                addRecensioneFascicoloPS.setString(1, user);     //inserisce l'username nella query

                addRecensioneFascicoloPS.executeUpdate();   //esegue la query
                connection.close();
            } catch (SQLException var2) {
                var2.printStackTrace();
            }
        }
    }

    public ResultSet allRecWithCommentFascicoloDB(int numero, String titolo){
        ResultSet rs = null;    //contiene le recensioni

        try {
            PreparedStatement allRecWithCommentPS = connection.prepareStatement(
                    "SELECT * FROM recensione_f NATURAL JOIN fascicolo NATURAL JOIN rivista WHERE fascicolo.numero = '"+numero+"' AND rivista.titolo = '"+titolo+"' AND recensione_f.testo IS NOT NULL AND recensione_f.valutazione IS NOT NULL"   //prepara la query che cerca tutte le recensioni del libro
            );

            rs = allRecWithCommentPS.executeQuery(); //esegue la query
        } catch (SQLException var2) {
            var2.printStackTrace();
        }

        return rs;
    }

    @Override
    public ArrayList<String> getLibriISBNPreferitiDB(String user){
        ArrayList<String> isbn = new ArrayList<>();
        ResultSet rs = null;

        try {
            PreparedStatement getLibriISBNPreferitiPS = connection.prepareStatement(
                    "SELECT isbn FROM recensione_l WHERE username = '"+user+"' AND preferito = true" //prepara la query che conta il numero di tuple con 'user' e 'isbn'
            );
            rs = getLibriISBNPreferitiPS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs'
                isbn.add(rs.getString("isbn"));
            }

            rs.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }
        chiudiConnessione();

        return isbn;
    }

    @Override
    public ArrayList<String> getSerieISBNPreferitiDB(String user){
        ArrayList<String> isbn = new ArrayList<>();
        ResultSet rs = null;

        try {
            PreparedStatement getLibriISBNPreferitiPS = connection.prepareStatement(
                    "SELECT isbn FROM recensione_s WHERE username = '"+user+"' AND preferito = true" //prepara la query che conta il numero di tuple con 'user' e 'isbn'
            );
            rs = getLibriISBNPreferitiPS.executeQuery(); //esegue la query

            while(rs.next()){    //scorre il ResultSet 'rs'
                isbn.add(rs.getString("isbn"));
            }

            rs.close();
        } catch (SQLException var2) {
            var2.printStackTrace();
        }
        chiudiConnessione();

        return isbn;
    }

    @Override
    public void chiudiConnessione(){    //chiude la connessione al DB
        try{
            if (connection != null && !connection.isClosed()){
                connection.close();
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}