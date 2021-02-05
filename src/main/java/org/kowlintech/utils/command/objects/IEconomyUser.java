package org.kowlintech.utils.command.objects;

public interface IEconomyUser {

    /**
     * @return The User object for EconomyUser.
     */
    long getUserId();

    /**
     * @return The economy balance of the user.
     */
    long getBalance();

    /**
     * @return The guild id of the user.
     */
    long getGuildId();

    /**
     * Sets the user's balance.
     */
    EconomyUser setBalance(long balance);

    /**
     * Adds to the user's balance.
     */
    EconomyUser addToBalance(long amount);

    /**
     * Takes away from the user's balance.
     */
    EconomyUser removeFromBalance(long amount);
}
