package org.kowlintech.utils;

import org.kowlintech.GordonRamsay;
import org.kowlintech.utils.command.objects.EconomyPhrase;
import org.kowlintech.utils.command.objects.enums.PhraseType;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class PhraseManager {

    private Connection connection = GordonRamsay.getDatabaseConnection();
    private HashMap<PhraseType, EconomyPhrase> PHRASES;
    private List<EconomyPhrase> SHOVEL_PHRASES;
    private List<EconomyPhrase> CRIME_PHRASES;
    private List<EconomyPhrase> ASK_PHRASES;

    public PhraseManager() throws SQLException {
        this.PHRASES = new HashMap<>();
        this.SHOVEL_PHRASES = new ArrayList<>();
        this.CRIME_PHRASES = new ArrayList<>();
        this.ASK_PHRASES = new ArrayList<>();

        PHRASES = initializePhraseHashMap();

    }

    public EconomyPhrase getRandomPhrase(PhraseType type) {
        Random random = new Random();
        Object[] values = getPhraseListFromType(type).toArray();

        EconomyPhrase randomPhrase = (EconomyPhrase) values[random.nextInt(values.length)];

        return randomPhrase;
    }

    public EconomyPhrase addPhrase(PhraseType type, String text, boolean fate) {
        try {
            connection.prepareStatement("INSERT INTO " + type.getName().toLowerCase() + " (text, fate) VALUES('" + text + "', " + fate + ");").execute();

            ResultSet rs = connection.prepareStatement("SELECT * FROM " + type.getName().toLowerCase() + " WHERE text='" + text + "'").executeQuery();

            while (rs.next()) {
                EconomyPhrase phrase = new EconomyPhrase(text, fate, rs.getInt("id"), PhraseType.SHOVEL);
                PHRASES.put(PhraseType.SHOVEL, phrase);
                if(type == PhraseType.SHOVEL) {
                    SHOVEL_PHRASES.add(phrase);
                } else if(type == PhraseType.CRIME) {
                    CRIME_PHRASES.add(phrase);
                } else if(type == PhraseType.ASK) {
                    ASK_PHRASES.add(phrase);
                }
                return phrase;
            }

            return null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean deletePhrase(PhraseType type, int id) {
        try {
            ResultSet rs = connection.prepareStatement("SELECT * FROM " + type.getName().toLowerCase() + " WHERE id=" + id + ";").executeQuery();

            while (rs.next()) {

                connection.prepareStatement("DELETE FROM " + type.getName().toLowerCase() + " WHERE id=" + id + ";").execute();

                for(EconomyPhrase phrase : PHRASES.values()) {
                    if(phrase.getId() == id) {
                        PHRASES.remove(phrase);
                    }
                    if(type == PhraseType.SHOVEL) {
                        for(EconomyPhrase phrase1 : SHOVEL_PHRASES) {
                            if(phrase1.getId() == phrase.getId()) {
                                SHOVEL_PHRASES.remove(phrase1);
                            }
                        }
                    } else if(type == PhraseType.CRIME) {
                        for(EconomyPhrase phrase1 : CRIME_PHRASES) {
                            if(phrase1.getId() == phrase.getId()) {
                                CRIME_PHRASES.remove(phrase1);
                            }
                        }
                    } else if(type == PhraseType.ASK) {
                        for(EconomyPhrase phrase1 : ASK_PHRASES) {
                            if(phrase1.getId() == phrase.getId()) {
                                ASK_PHRASES.remove(phrase1);
                            }
                        }
                    }
                }
                rs.close();
                return true;
            }

            rs.close();
            return false;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    private HashMap<PhraseType, EconomyPhrase> initializePhraseHashMap() throws SQLException {
        HashMap<PhraseType, EconomyPhrase> hash = new HashMap<>();

        PreparedStatement shovel = connection.prepareStatement("SELECT * FROM shovel");
        ResultSet shovel_rs = shovel.executeQuery();
        while (shovel_rs.next()) {
            hash.put(PhraseType.SHOVEL, new EconomyPhrase(shovel_rs.getString("text"), shovel_rs.getBoolean("fate"), shovel_rs.getInt("id"), PhraseType.SHOVEL));
            SHOVEL_PHRASES.add(new EconomyPhrase(shovel_rs.getString("text"), shovel_rs.getBoolean("fate"), shovel_rs.getInt("id"), PhraseType.SHOVEL));
        }

        shovel_rs.close();
        shovel.close();

        PreparedStatement crime = connection.prepareStatement("SELECT * FROM crime");
        ResultSet crime_rs = crime.executeQuery();
        while (crime_rs.next()) {
            hash.put(PhraseType.CRIME, new EconomyPhrase(crime_rs.getString("text"), crime_rs.getBoolean("fate"), crime_rs.getInt("id"), PhraseType.CRIME));
            CRIME_PHRASES.add(new EconomyPhrase(crime_rs.getString("text"), crime_rs.getBoolean("fate"), crime_rs.getInt("id"), PhraseType.CRIME));
        }

        crime_rs.close();
        crime.close();

        PreparedStatement ask = connection.prepareStatement("SELECT * FROM ask");
        ResultSet ask_rs = ask.executeQuery();
        while (ask_rs.next()) {
            hash.put(PhraseType.ASK, new EconomyPhrase(ask_rs.getString("text"), ask_rs.getBoolean("fate"), ask_rs.getInt("id"), PhraseType.ASK));
            ASK_PHRASES.add(new EconomyPhrase(ask_rs.getString("text"), ask_rs.getBoolean("fate"), ask_rs.getInt("id"), PhraseType.ASK));
        }

        ask_rs.close();
        ask.close();

        return hash;
    }

    public HashMap<PhraseType, EconomyPhrase> getPhraseHashMap() {
        return PHRASES;
    }

    private List<EconomyPhrase> getPhraseListFromType(PhraseType type) {
        if(type == PhraseType.SHOVEL) {
            return SHOVEL_PHRASES;
        } else if(type == PhraseType.CRIME) {
            return CRIME_PHRASES;
        } else {
            return ASK_PHRASES;
        }
    }
}
