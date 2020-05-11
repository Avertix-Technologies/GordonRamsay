package org.kowlintech.utils;

import org.kowlintech.GordonRamsay;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class InsultManager {

    public Insult addInsult(String insult) throws SQLException {
        PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("INSERT INTO insults (insult) VALUES(?)");
        st.setObject(1, insult);
        st.execute();

        PreparedStatement st1 = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM insults WHERE insult=?");
        st1.setObject(1, insult);
        ResultSet rs = st1.executeQuery();
        while (rs.next()) {
            return new Insult(rs.getInt("id"), rs.getString("insult"));
        }

        return null;
    }

    public void deleteInsult(int id) throws SQLException {
        PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("DELETE FROM insults WHERE id=?");
        st.setObject(1, id);
        st.execute();
    }

    public ArrayList<Insult> getInsults() throws SQLException {
        ArrayList<Insult> insults = new ArrayList<>();
        PreparedStatement st = GordonRamsay.getDatabaseConnection().prepareStatement("SELECT * FROM insults");
        ResultSet rs = st.executeQuery();
        while (rs.next()) {
            insults.add(new Insult(rs.getInt("id"), rs.getString("insult")));
        }
        return insults;
    }
}
