package com.example.israel.android_custom_view_volume;

public class CollisionStatics {

    public static boolean CircleAndPoint(float radius, float cx, float cy, float px, float py) {
        float relativeX = px - cx;
        float relativeY = py - cy;
        float distanceFromCenter = (float) Math.sqrt((relativeX*relativeX + relativeY*relativeY));
        return radius > distanceFromCenter;
    }

}
