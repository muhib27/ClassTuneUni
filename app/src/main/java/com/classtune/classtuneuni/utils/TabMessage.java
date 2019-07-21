package com.classtune.classtuneuni.utils;


import com.classtune.classtuneuni.R;

/**
 * Created by Muhib on 21.07.2019.
 */
public class TabMessage {
    public static String get(int menuItemId, boolean isReselection) {
        String message = "Content for ";

        switch (menuItemId) {
            case R.id.home:
                message += "home";
                break;
            case R.id.assistant:
                message += "assistant";
                break;
            case R.id.news:
                message += "news";
                break;
            case R.id.quiz:
                message += "quiz";
                break;
            case R.id.forum:
                message += "forum";
                break;
        }

        if (isReselection) {
            message += " WAS RESELECTED! YAY!";
        }

        return message;
    }
}
