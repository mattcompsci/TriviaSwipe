package com.mnewell.game;

/**
 * Created by my_pc on 8/15/2017.
 */

public class Question {

    public String query;
    public String rightAnswer;
    public String wrongAnswer;
    public String path;
    public String category;

    public Question(String query, String rightAnswer, String wrongAnswer, String path, String category){
        this.query = query;
        this.rightAnswer = rightAnswer;
        this.wrongAnswer = wrongAnswer;
        this.path = path;
        this.category = category;
    }
}
