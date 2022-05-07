package com.example.blockpanda;

import android.content.Context;
import android.content.DialogInterface;

import androidx.appcompat.app.AlertDialog;


public class PromptAlertUserDialogs {


    private final static long TEN_MINUTES = 10 * 60 * 1000;


    // prompt user for usage access required
    public static void showDialogUsageAccessRequired(final Context context) {

        AlertDialog usageAccessRequiredDialog;

        String title = "USAGE ACCESS REQUIRED !!";
        String text = "The App requires USAGE ACCESS to work. Please provide permission to continue using this app.";
        String OKButton = "GIVE USAGE ACCESS";
        String CancelButton = "NO THANKS";

        usageAccessRequiredDialog = new AlertDialog.Builder(context).create();
        usageAccessRequiredDialog.setTitle(title);
        usageAccessRequiredDialog.setMessage(text);
        usageAccessRequiredDialog.setCancelable(false);
        usageAccessRequiredDialog.setCanceledOnTouchOutside(false);

        usageAccessRequiredDialog.setButton(AlertDialog.BUTTON_POSITIVE, OKButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SpecialIntents.openUsageAccessSettingsPage(context);
                    }
                });

        usageAccessRequiredDialog.setButton(AlertDialog.BUTTON_NEGATIVE, CancelButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SpecialIntents.showHomeScreen(context);
                    }
                });


        usageAccessRequiredDialog.show();

    }

}
