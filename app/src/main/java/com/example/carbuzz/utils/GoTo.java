package com.example.carbuzz.utils;

import android.content.Context;
import android.content.Intent;

public class GoTo {
    public static final GoTo go = new GoTo();

    private GoTo() {
    }
    public void to(Context context, Class<?> className){
        Intent intent=new Intent(context,className);
        context.startActivity(intent);
    }
}
