package jp.ne.sakura.kkkon.andorid.supportintent.support;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by kkkon on 2018/08/26.
 */

public class SupportGoogleRedeem
{
    private static final String TAG = "SupportGoogleRedeem";

    static final String URL_PREFIX = "https://play.google.com/redeem?code=";

    public static String makeURLfromText( final String code )
    {
        final String result = SupportUtils.makeURLfromText( URL_PREFIX, code );
        return result;
    }

    static final FilenameFilter mFilter = new FilenameFilter() {
        @Override
        public boolean accept(File dir, String name)
        {
            final String nameLower = name.toLowerCase();
            //Log.d(TAG, "  " + name );
            if ( nameLower.endsWith(".csv") || nameLower.endsWith(".txt") )
            {
                return true;
            }
            return false;
        }
    };



    public ArrayList<File> list(final Context context)
    {
        ArrayList<File> list = SupportUtils.list( context, mFilter );
        return list;
    }
}
