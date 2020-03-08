package core.model;

public class User {

    private String username;

    private String email;

    private String name;

    private String surname;

    private boolean company;

    private String sport;

    private String city;

    private int hour;

    private String date;

    private int minute;

    private String role;



    public User() {
        super();
    }

    public String getSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean getCompany() {
        return company;
    }

    public void setCompany(boolean company) {
        this.company = company;
    }

    public String getCity() {
        return city;
    }

    public boolean isCompany() {
        return company;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date)
    {
        this.date=date;
    }

    public void setPreferredHour(int hourOfDay, int minute) {
        this.hour=hourOfDay;
        this.minute=minute;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
