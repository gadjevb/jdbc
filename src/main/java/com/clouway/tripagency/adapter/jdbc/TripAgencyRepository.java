package com.clouway.tripagency.adapter.jdbc;

/**
 * @author Borislav Gadjev <gadjevb@gmail.com>
 */
public class TripAgencyRepository {

    /*private List<Person> personList;
    private List<Destination> tripList;
    private List<String> citiesList;
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;

    public TripAgencyRepository(ConnectionProvider provider, String database, String username, String password) {
        connection = provider.getConnection(database,username,password);
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertPerson(String name, Long egn, Integer age, String email) throws Exception {
        statement.executeUpdate("INSERT INTO PEOPLE VALUES('" + name + "'," + egn + "," + age + ",'" + email + "');");
    }

    public void insertTrip(Long egn, String arrival, String department, String city) throws Exception {
        statement.executeUpdate("INSERT INTO TRIP VALUES(" + egn + ",'" + arrival + "','" + department + "','" + city + "');");
    }

    public void updatePerson(String name, Long egn, Integer age, String email) throws Exception {
        statement.executeUpdate("UPDATE PEOPLE SET Name = '" + name + "', Age = " + age + ", Email = '" + email + "' WHERE EGN = " + egn + ";");
    }

    public void updateTrip(Long egn, String arrival, String department, String city) throws Exception {
        statement.executeUpdate("UPDATE TRIP SET Arrival = '" + arrival + "', Department = '" + department + "', City = '" + city + "' WHERE EGN = " + egn + ";");
    }

    public void getPeopleContent() throws Exception {
        personList = new ArrayList();
        resultSet = statement.executeQuery("SELECT * FROM PEOPLE;");
        while (resultSet.next()) {
            Person person = new Person(resultSet.getString(1), resultSet.getLong(2), resultSet.getInt(3), resultSet.getString(4));
            personList.add(person);
        }
    }

    public void getTripContent() throws Exception {
        tripList = new ArrayList();
        resultSet = statement.executeQuery("SELECT * FROM TRIP;");
        while (resultSet.next()) {
            Destination trip = new Destination(resultSet.getLong(1), resultSet.getDate(2), resultSet.getDate(3), resultSet.getString(4));
            tripList.add(trip);
        }
    }

    public void getPeopleByFirstLetters(String letters) throws Exception {
        personList = new ArrayList();
        resultSet = statement.executeQuery("SELECT * FROM PEOPLE WHERE Name::text LIKE '" + letters + "%';");
        while (resultSet.next()) {
            Person person = new Person(resultSet.getString(1), resultSet.getLong(2), resultSet.getInt(3), resultSet.getString(4));
            personList.add(person);
        }
    }

    public void getPeopleInTheSameCity(String date, String city) throws Exception {
        personList = new ArrayList();
        resultSet = statement.executeQuery("SELECT DISTINCT * FROM PEOPLE INNER JOIN TRIP ON PEOPLE.EGN = TRIP.EGN " +
                "WHERE TRIP.Arrival <= '" + date + "' and TRIP.Department >= '" + date + "' and TRIP.City = '" + city + "';");
        while (resultSet.next()) {
            Person person = new Person(resultSet.getString(1), resultSet.getLong(2), resultSet.getInt(3), resultSet.getString(4));
            personList.add(person);
        }
    }

    public void getMostVisitedCities() throws Exception {
        citiesList = new ArrayList();
        resultSet = statement.executeQuery("SELECT City FROM TRIP GROUP BY TRIP.City ORDER BY COUNT(City) DESC;");
        while (resultSet.next()) {
            String city = resultSet.getString(1);
            citiesList.add(city);
        }
    }

    public void deleteTableContent(String table) throws Exception {
        if(table.equals("people")){
            statement.executeUpdate("DELETE FROM PEOPLE;");
        }else if(table.equals("trip")){
            statement.executeUpdate("DELETE FROM TRIP;");
        }
    }

    public void deleteTable(String table) throws Exception {
        if(table.equals("people")){
            statement.executeUpdate("DROP TABLE PEOPLE;");
        }else if(table.equals("trip")){
            statement.executeUpdate("DROP TABLE TRIP;");
        }
    }

    public List<Person> getPersonList() {
        return personList;
    }

    public List<Destination> getDestinationList() {
        return tripList;
    }

    public List<String> getCitiesList() {
        return citiesList;
    }

    public void close() throws Exception {
        if(connection != null){
            connection.close();
        }
        if(statement != null){
            statement.close();
        }
        if(resultSet != null){
            resultSet.close();
        }
    }*/
}
