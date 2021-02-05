package org.kowlintech.utils.command.objects;

import org.kowlintech.GordonRamsay;

import java.sql.SQLException;

public class EconomyUser implements IEconomyUser {

    private long user;
    private long balance;
    private long guildid;

    public EconomyUser(long user, long balance, long guildid) {
        this.user = user;
        this.balance = balance;
        this.guildid = guildid;
    }

    /**
     * @return The User object for EconomyUser.
     */
    @Override
    public long getUserId() {
        return user;
    }

    /**
     * @return The economy balance of the user.
     */
    @Override
    public long getBalance() {
        return balance;
    }

    /**
     * @return The guild id of the user.
     */
    @Override
    public long getGuildId() {
        return guildid;
    }

    /**
     * Sets the user's balance.
     *
     * @param balance
     */
    @Override
    public EconomyUser setBalance(long balance) {
        try {
            GordonRamsay.getDatabaseConnection().prepareStatement("UPDATE economy SET coins=" + balance + " WHERE guildid=" + getGuildId() + " AND userid=" + getUserId()).execute();

            EconomyUser newuser = new EconomyUser(user, balance, guildid);

            GordonRamsay.getEconomyManager().USERS.add(newuser);
            GordonRamsay.getEconomyManager().USERS.remove(this);
            return newuser;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Adds to the user's balance.
     *
     * @param amount
     */
    @Override
    public EconomyUser addToBalance(long amount) {
        try {
            GordonRamsay.getDatabaseConnection().prepareStatement("UPDATE economy SET coins=" + (this.balance + amount) + " WHERE guildid=" + getGuildId() + " AND userid=" + getUserId()).execute();

            EconomyUser newuser = new EconomyUser(user, (this.balance + amount), guildid);

            GordonRamsay.getEconomyManager().USERS.add(newuser);
            GordonRamsay.getEconomyManager().USERS.remove(this);
            return newuser;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * Takes away from the user's balance.
     *
     * @param amount
     */
    @Override
    public EconomyUser removeFromBalance(long amount) {
        try {
            GordonRamsay.getDatabaseConnection().prepareStatement("UPDATE economy SET coins=" + (this.balance - amount) + " WHERE guildid=" + getGuildId() + " AND userid=" + getUserId()).execute();

            EconomyUser newuser = new EconomyUser(user, (this.balance - amount), guildid);

            GordonRamsay.getEconomyManager().USERS.add(newuser);
            GordonRamsay.getEconomyManager().USERS.remove(this);
            return newuser;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}
