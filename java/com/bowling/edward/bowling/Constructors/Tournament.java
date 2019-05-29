package com.bowling.edward.bowling.Constructors;

public class Tournament {

    private String creatorID, tournamentName, password;
    private int noOfPlayers;

    public Tournament(String creatorID, String tournamentName, String password, int noOfPlayers){
        this.creatorID = creatorID;
        this.tournamentName = tournamentName;
        this.password = password;
        this.noOfPlayers = noOfPlayers;
    }

    public String getCreatorID() {
        return creatorID;
    }

    public void setCreatorID(String creatorID) {
        this.creatorID = creatorID;
    }

    public String getTournamentName() {
        return tournamentName;
    }

    public void setTournamentName(String tournamentName) {
        this.tournamentName = tournamentName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getNoOfPlayers() {
        return noOfPlayers;
    }

    public void setNoOfPlayers(int noOfPlayers) {
        this.noOfPlayers = noOfPlayers;
    }



}
