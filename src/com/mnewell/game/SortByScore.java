package com.mnewell.game;

import java.util.Comparator;

public class SortByScore  implements Comparator<ResultData> {

    public int compare(ResultData a, ResultData b){
        return a.score - b.score;
    }
}
