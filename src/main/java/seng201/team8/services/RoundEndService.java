package seng201.team8.services;

import java.util.Random;

public class RoundEndService {
    public boolean isRandomEventPlayed(){
        Random rand = new Random();
        return rand.nextInt(0, 10) < 3;

    }
}
