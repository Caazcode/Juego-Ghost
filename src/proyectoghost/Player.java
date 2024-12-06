/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyectoghost;

/**
 *
 * @author adrianaguilar
 */
// File: src/main/java/Player.java
public class Player {
    private String username;
    private String password;
    private int points;
    private String[] gameLogs;
    private int logIndex;

    public Player(String username, String password) {
        this.username = username;
        this.password = password;
        this.points = 0;
        this.gameLogs = new String[10];
        this.logIndex = 0;
    }

    public String getUsername() {
        return username;
    }

    public boolean validatePassword(String password) {
        return this.password.equals(password);
    }

    public void addLog(String log) {
        gameLogs[logIndex] = log;
        logIndex = (logIndex + 1) % gameLogs.length;
    }

    public String[] getLogs() {
        return gameLogs;
    }

    public void changePassword(String newPassword) {
        this.password = newPassword;
    }

    public int getPoints() {
        return points;
    }

    public void addPoints(int points) {
        this.points += points;
    }
}

