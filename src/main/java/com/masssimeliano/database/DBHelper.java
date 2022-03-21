package com.masssimeliano.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBHelper {

    private final String DB_NAME = "SQLiteDB.db";
    private final String TABLE_NAME = "Users";

    private final String DB_JDBC_DRIVER = "org.sqlite.JDBC";
    private final String DB_FILE_PATH = "jdbc:sqlite:database//" + DB_NAME;

    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public void connect() {
        try {
            Class.forName(DB_JDBC_DRIVER);
            connection = DriverManager.getConnection(DB_FILE_PATH);

            System.out.println("DB: DB Connected");
        }
        catch (Exception e) {
            System.out.println("DB: Error (Connect) : " + e.getMessage());
        }
    }

    public void insert(String chatID) {
        String query = "INSERT INTO " + TABLE_NAME + " (chatID, city, latitude, longitude, lastMessageText) VALUES ('" + chatID + "', '" + "London" + "', '" + "51.509865" + "', '" + "-0.118092" + "', '" + "NOTHING" + "');";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();

            System.out.println("DB: New User");
        }
        catch (Exception e) {
            System.out.println("DB: Error (Insert User) : " + e.getMessage());
        }
    }

    public void setLastMessageText(String chatID, String lastMessageText) {
        String query = "UPDATE " + TABLE_NAME + " SET lastMessageText = '" + lastMessageText + "' WHERE chatID = " + chatID + ";";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();

            System.out.println("DB: New LM : " + lastMessageText);
        }
        catch (Exception e) {
            System.out.println("DB: Error (Insert LM) : " + e.getMessage());
        }
    }

    public void setCity(String chatID, String city) {
        String query = "UPDATE " + TABLE_NAME + " SET city = '" + city + "' WHERE chatID = " + chatID + ";";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query);
            statement.close();

            System.out.println("DB: New City : " + city);
        }
        catch (Exception e) {
            System.out.println("DB: Error (Insert City) : " + e.getMessage());
        }
    }

    public void setCoordinates(String chatID, float latitude, float longitude) {
        String query1 = "UPDATE " + TABLE_NAME + " SET latitude = '" + latitude + "' WHERE chatID = " + chatID + ";";
        String query2 = "UPDATE " + TABLE_NAME + " SET longitude = '" + longitude + "' WHERE chatID = " + chatID + ";";

        try {
            statement = connection.createStatement();
            statement.executeUpdate(query1);
            statement.executeUpdate(query2);
            statement.close();

            System.out.println("DB: New Coordinates : " + Float.toString(latitude) + " " + Float.toString(longitude));
        }
        catch (Exception e) {
            System.out.println("DB: Error (Insert Coordinates) : " + e.getMessage());
        }
    }

    public String getLastMessageText(String chatID) {
        String lastMessageText;

        String query = "SELECT lastMessageText FROM " + TABLE_NAME + " WHERE chatID = " + chatID + ";";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            lastMessageText = resultSet.getString("lastMessageText");

            resultSet.close();
            statement.close();

            System.out.println("DB (LM): LM : " + lastMessageText);

            return lastMessageText;
        }
        catch (Exception e) {
            System.out.println("DB (LM): Error (GET LM) : " + e.getMessage());

            return "";
        }
    }

    public String getCity(String chatID) {
        String city;

        String query = "SELECT city FROM " + TABLE_NAME + " WHERE chatID = " + chatID + ";";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            city = resultSet.getString("city");

            resultSet.close();
            statement.close();

            System.out.println("DB (City): City : " + city);

            return city;
        }
        catch (Exception e) {
            System.out.println("DB (City): Error (GET City) : " + e.getMessage());

            return "";
        }
    }

    public float getLatitude(String chatID) {
        float latitude;

        String query = "SELECT latitude FROM " + TABLE_NAME + " WHERE chatID = " + chatID + ";";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            latitude = resultSet.getFloat("latitude");

            resultSet.close();
            statement.close();

            System.out.println("DB (Latitude): Latitude : " + latitude);

            return latitude;
        }
        catch (Exception e) {
            System.out.println("DB (Latitude): Error (GET Latitude) : " + e.getMessage());

            return 0;
        }
    }

    public float getLongitude(String chatID) {
        float longitude;

        String query = "SELECT longitude FROM " + TABLE_NAME + " WHERE chatID = " + chatID + ";";

        try {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            longitude = resultSet.getFloat("longitude");

            resultSet.close();
            statement.close();

            System.out.println("DB (Longitude): Longitude : " + longitude);

            return longitude;
        }
        catch (Exception e) {
            System.out.println("DB (Longitude): Error (GET Longitude) : " + e.getMessage());

            return 0;
        }
    }

    public void showDB() {
        int count = 0;
        StringBuffer dataBase = new StringBuffer('\n');

        String query = "SELECT chatID FROM " + TABLE_NAME + ";";

        try {

            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                dataBase.append(Integer.toString(count + 1) + " : " + Long.toString(resultSet.getInt("chatID")) + '\n');
                count = count + 1;
            }

            resultSet.close();
            statement.close();

            System.out.println("DB: DB Showed");
        }
        catch (Exception e) {
            System.out.println("DB: Error (Show) : " + e.getMessage());
        }

        System.out.println("DB: UsersDB :" + '\n' + dataBase.toString());
    }

    public void close() {
        try {
            connection.close();

            System.out.println("DB: DB Closed");
        }
        catch (Exception e) {
            System.out.println("DB: Error (Close) : " + e.getMessage());
        }
    }

}
