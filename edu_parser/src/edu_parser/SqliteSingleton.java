package edu_parser;

public class SqliteSingleton {

    public static final String TAG = "SqliteSingleton";

    private static SqliteSingleton database;


    public static int DATABASE_VERSION = 1;


    private SqliteSingleton(){}


    public static SqliteSingleton getInstance(){

        if(database == null){
            database = new SqliteSingleton();
        }
        return database;
    }
}
