package esimerkki;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

// 1. TIETOKANNAN LUONTI, OLTAVA JOKIN SQLITE TIEDOSTO POHJANA
// CREATE TABLE Kaveri(Id INT, Nimi TEXT);
// 2. YHDEN RIVIN LISÄYS SQL
// INSERT INTO Kaveri(Id, Nimi) VALUES(1, 'Juhani');

public class TietokantaOhjelmaKaverit {
    public static void main(String[] args) throws Exception {
        // 1. CONNECTION STRING
        String connectionString = "jdbc:sqlite:data/Chinook_Sqlite_TEST.sqlite";
        // 2. YHTEYDEN LUONTI
        Connection yhteys = DriverManager.getConnection(connectionString);
        // 3. PREPARED STATEMENT TIEDON HAKUUN, LISÄÄMISEEN, PÄIVITTÄMISEEN JA POISTOON
        PreparedStatement lause = yhteys.prepareStatement("SELECT Nimi FROM Kaveri");
        // 4. KUN KYSELTIIN TIETOA, PITÄÄ TEHDÄ RESULTSET TULOKSILLE
        // TIETOTURVA JA FATAALI SQL INJECTION UHKA, JOS PREPARED STATEMENT
        // TEHDÄÄN VÄÄRIN
        ResultSet rivit = lause.executeQuery();
        // 5. RESULT SETIN LÄPIKÄYNTI
        while (rivit.next()) {
            System.out.println(rivit.getString("Nimi"));
        }

        Scanner lukija = new Scanner(System.in);
        System.out.println("Anna lisättävän kaverin nimi: ");
        String uudenKaverinNimi = lukija.nextLine();
        System.out.println("Anna uuden kaverin id: ");
        int id = lukija.nextInt();

        // TIETOTURVA SQL-INJECTION ON ERITYISEN TÄRKEÄ INSERT, DELETE JA
        // UPDATE TOIMINNOISSA. LISÄTTÄVÄ, POISTETTAVA TAI MUOKKAUKSEN TULOS
        // TEKSTIKENTTÄ PITÄÄ TYYPPITARKISTAA, MUUTEN TULEE HAAVOITTUVUUS

        PreparedStatement lisaaKaveri = yhteys.prepareStatement("INSERT INTO Kaveri(Id, Nimi) VALUES(?,?)");

        lisaaKaveri.setInt(1, id);
        lisaaKaveri.setString(2, uudenKaverinNimi);
        // SUORITETAAN PÄIVITÄ KOMENTON. KAIKKI DELETED, UPDATED, INSERTIT MENEVÄT
        // EXECUTEUPDATEN KAUTTA, EI PELKÄN EXECUTEQUERYN KAUTTA.
        lisaaKaveri.executeUpdate();
        System.out.println("Kaverisi tällä hetkellä.");

        lause = yhteys.prepareStatement("SELECT Nimi FROM Kaveri");
        // 4. KUN KYSELTIIN TIETOA, PITÄÄ TEHDÄ RESULTSET TULOKSILLE
        // TIETOTURVA JA FATAALI SQL INJECTION UHKA, JOS PREPARED STATEMENT
        // TEHDÄÄN VÄÄRIN
        rivit = lause.executeQuery();
        // 5. RESULT SETIN LÄPIKÄYNTI
        while (rivit.next()) {
            System.out.println(rivit.getString("Nimi"));
        }

        // 6. MUISTA AINA LAITTAA HETI ALUSTA LÄHTIEN YHTEYKSIEN SULUT
        rivit.close();
        lause.close();
        yhteys.close();
    }
}
