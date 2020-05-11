package org.kowlintech.utils;

public class Insult implements IInsult
{
    private int id;
    private String text;

    /**
     * Class constructor.
     *
     * @param id The id of this insult.
     * @param text The text of this insult.
     */
    public Insult(int id, String text)
    {
        this.id = id;
        this.text = text;
    }

    @Override
    public int getId()
    {
        return id;
    }

    @Override
    public String getText()
    {
        return text;
    }
}