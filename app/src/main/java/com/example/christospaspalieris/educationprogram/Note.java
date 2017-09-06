package com.example.christospaspalieris.educationprogram;

/**
 * Created by peira on 11-Jul-17.
 */

public class Note {

    private int coordinatesX,coordinatesY;

    public Note()
    {

    }

    public Note(int coordinatesX, int coordinatesY) {
        this.coordinatesX = coordinatesX;
        this.coordinatesY = coordinatesY;
    }

    public int getCoordinatesX() {
        return coordinatesX;
    }

    public int getCoordinatesY() {
        return coordinatesY;
    }
}
