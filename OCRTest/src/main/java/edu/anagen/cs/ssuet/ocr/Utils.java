package edu.anagen.cs.ssuet.ocr;

import android.app.ProgressDialog;
import android.content.Context;
import android.support.annotation.Nullable;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hp on 10/8/2017.
 */

public class Utils {

    public static boolean isValidEmail(String email) {

        if (email == null) {
            return false;
        }

        final String emailPattern = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        Matcher matcher;
        Pattern pattern = Pattern.compile(emailPattern);

        matcher = pattern.matcher(email);

        if (matcher != null) {
            return matcher.matches();
        } else {
            return false;
        }
    }

    public static ProgressDialog MorseProgressDialog(final Context ctx, @Nullable String _title, String _message/*, @DrawableRes @Nullable int icon*/) {

        ProgressDialog  pd = new ProgressDialog(ctx);
        pd.setTitle(_title);
        pd.setMessage(_message);
        pd.setIndeterminate(false);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setCancelable(false);
        return pd;
    }
}
