package com.mnewell.game;


import com.badlogic.gdx.utils.Array;



public class ResultData {

    public String name;
    public int score;
    public int questionsCorrect;
   public  int questionsIncorrect;
    public int answered;
    public int averageTimePerQuestion;

    public Array<Integer> times;

    public ResultData(String name, int score, int questionsCorrect, int questionsIncorrect, Array<Integer> times){
    this.name = name;
        this.score = score;
        this.questionsCorrect = questionsCorrect;
        this.questionsIncorrect = questionsIncorrect;
        this.times = times;
        answered = questionsCorrect + questionsIncorrect;
        averageTimePerQuestion = getAverageTimePerQuestion();


    }

    public ResultData(int score, int questionsCorrect, int questionsIncorrect, Array<Integer> times){
        this.score = score;
        this.questionsCorrect = questionsCorrect;
        this.questionsIncorrect = questionsIncorrect;
        this.times = times;
        answered = questionsCorrect + questionsIncorrect;
        averageTimePerQuestion = getAverageTimePerQuestion();


    }

    private int getAverageTimePerQuestion(){
        int total = 0;

        for(Integer i: times){
            total += i;
        }

        if(total == 0){
            return 0;
        }
        if(times.size == 0){
            return 0;
        }

        return  total / times.size;
    }

}
