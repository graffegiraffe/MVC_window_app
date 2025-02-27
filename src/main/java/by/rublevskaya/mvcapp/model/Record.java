package by.rublevskaya.mvcapp.model;

import java.time.LocalDate;

public class Record {

    private final int id;
    private final String fullName;
    private final LocalDate birthDate;
    private final String team;
    private final String homeCity;
    private final String squad;
    private final String position;

    public Record(int id, String fullName, LocalDate birthDate, String team, String homeCity, String squad, String position) {
        this.id = id;
        this.fullName = fullName;
        this.birthDate = birthDate;
        this.team = team;
        this.homeCity = homeCity;
        this.squad = squad;
        this.position = position;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String getTeam() {
        return team;
    }

    public String getHomeCity() {
        return homeCity;
    }

    public String getSquad() {
        return squad;
    }

    public String getPosition() {
        return position;
    }
}