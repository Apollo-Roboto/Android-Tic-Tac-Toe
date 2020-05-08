package com.example.tic_tac_toe;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

public class PopupDialog {

    public static void areYouSure(Context context, String msg, final Runnable action){
        String yes = context.getResources().getString(R.string.yes);
        String cancel = context.getResources().getString(R.string.cancel);

        AlertDialog.Builder builder = new AlertDialog.Builder(context)
            .setMessage(msg)
            .setCancelable(true)
            .setPositiveButton(yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    action.run();
                }
            })
            .setNegativeButton(cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public static void AreYouSureReturnMainMenu(final Context context){
        String msg = context.getResources().getString(R.string.returnToMainMenuMsg);

        areYouSure(context, msg, new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(context, MainActivity.class);
                context.startActivity(i);
            }
        });
    }

}
