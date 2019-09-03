package com.classtune.classtuneuni.utils;


import com.classtune.classtuneuni.R;

/**
 * Created by Muhib on 21.07.2019.
 */
public class TabMessage {
    public static String get(int menuItemId, boolean isReselection) {
        String message = "";

        switch (menuItemId) {
            case R.id.home:
                message += "home";
                break;
            case R.id.assistant:
                message += "assignment";
                break;
            case R.id.news:
                message += "resource";
                break;
            case R.id.quiz:
                message += "notice";
                break;
            case R.id.forum:
                message += "more";
                break;
        }

        if (isReselection) {
            message += "";
        }

        return message;
    }
}
