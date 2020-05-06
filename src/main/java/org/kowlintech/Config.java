package org.kowlintech;

import java.io.*;
import java.net.URL;
import java.util.Scanner;

public class Config {
    private String token;
    private String prefix;

    public Config() {
        try {
            FileInputStream fis = new FileInputStream("config.txt");
            if (fis == null)
                System.out.println("No file found!");
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));

            String s = br.readLine();
            s = s.replaceAll("\r|\n", "");

            this.token =  s;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        this.prefix = "gg.";
    }

    public String getToken() {
        return token;
    }

    public String getPrefix() {
        return prefix;
    }
}