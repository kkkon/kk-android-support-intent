package jp.ne.sakura.kkkon.andorid.supportintent.support;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;

import jp.ne.sakura.kkkon.andorid.supportintent.BuildConfig;

public class SupportUtils
{
    private static final String TAG = "SupportUtils";


    public static boolean isPackageInstalled(final Context context, final String packageName )
    {
        boolean isInstalled = false;

        {
            final PackageManager pm = context.getPackageManager();
            if ( null != pm )
            {
                try
                {
                    final PackageInfo info = pm.getPackageInfo(packageName, 0);
                    if ( null != info )
                    {
                        isInstalled = true;
                    }
                }
                catch ( PackageManager.NameNotFoundException e )
                {
                    isInstalled = false;
                }
            }
        }

        return isInstalled;
    }

    public static String makeURLfromText( final String prefix, final String text )
    {
        if ( text.startsWith(prefix) )
        {
            return text;
        }

        {
            String prefixWithoutScheme = prefix;
            final int indexSchemePrefix = prefix.indexOf("://");
            {
                if ( 0 < indexSchemePrefix )
                {
                    final String schemePrefix = prefix.substring( 0, indexSchemePrefix );
                    prefixWithoutScheme = prefix.substring( indexSchemePrefix + "://".length() );
                }
            }
            String textWithoutScheme = text;
            final int indexSchemeText = text.indexOf("://");
            {
                if ( 0 < indexSchemeText )
                {
                    final String schemePrefix = text.substring( 0, indexSchemeText );
                    textWithoutScheme = text.substring( indexSchemeText + "://".length() );
                }
            }
            if ( textWithoutScheme.startsWith(prefixWithoutScheme) )
            {
                final StringBuilder sb = new StringBuilder();
                sb.append( prefix.substring( 0, indexSchemePrefix ) );
                sb.append( text.substring( indexSchemeText ) );
                final String result = sb.toString();
                return result;
            }
        }

        final String result = prefix + text;
        return result;
    }

    public static ArrayList<String> readFile(final File file )
    {
        ArrayList<String> list = new ArrayList<String>();

        BufferedReader in = null;
        try
        {
            in = new BufferedReader( new FileReader(file) );
            String line = null;
            while( null != (line = in.readLine() ) )
            {
                list.add( line.trim() );
            }
        }
        catch ( IOException e )
        {
            Log.e( TAG, "", e );
        }
        finally
        {
            if ( null != in )
            {
                try { in.close(); } catch ( Exception e ) { }
            }
        }

        return list;
    }




    public static ArrayList<File> list(
            final Context context
            , final FilenameFilter filter
    )
    {
        ArrayList<File>     list = new ArrayList<File>();
        if ( BuildConfig.DEBUG )
        {
            {
                final File dir = Environment.getExternalStoragePublicDirectory(""); // API8
                Log.d(TAG, "Environment.getExternalStoragePublicDirectory(\"\"):" + ((null!=dir)?(dir.getAbsolutePath()):("null")));
            }
            {
                final File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); // API8
                Log.d(TAG, "Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS):" + ((null!=dir)?(dir.getAbsolutePath()):("null")));
            }
            if ( 19 <= Build.VERSION.SDK_INT )
            {
                final File[] dirs = context.getExternalFilesDirs(Environment.DIRECTORY_DOWNLOADS);
                final StringBuffer sb = new StringBuffer();
                if ( null == dirs )
                {
                    sb.append("null");
                }
                else
                {
                    for ( final File dir : dirs )
                    {
                        if ( null == dir )
                        {
                            continue;
                        }
                        sb.append('\n');
                        sb.append( dir.getAbsolutePath() );
                    }
                }
                Log.d(TAG, "Context.getExternalFilesDirs(Environment.DIRECTORY_DOWNLOADS):" + sb.toString() );
            }
            else
            {
                final File dir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                Log.d(TAG, "Context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS):" + ((null!=dir)?(dir.getAbsolutePath()):("null")));
            }
            {
                android.os.storage.StorageManager sm = (android.os.storage.StorageManager)context.getSystemService(Context.STORAGE_SERVICE);
                if ( 24 <= Build.VERSION.SDK_INT )
                {
                    final java.util.List<android.os.storage.StorageVolume> listVolume = sm.getStorageVolumes();
                    final StringBuffer sb = new StringBuffer();
                    if ( null == list )
                    {
                        sb.append("null");
                    }
                    else
                    {
                        for ( final android.os.storage.StorageVolume vol : listVolume )
                        {
                            if ( null == vol )
                            {
                                continue;
                            }
                            sb.append('\n');
                            sb.append( vol.toString() );
                        }
                    }
                    Log.d(TAG, "StorageManager.getStorageVolumes():" + sb.toString() );
                }
            }
        }


        {
            final File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); // API8
            if ( null != dir )
            {
                final File[] files = dir.listFiles(filter);
                if ( null == files )
                {
                    Log.d(TAG, " files null" );
                }
                else
                {
                    for ( final File file : files )
                    {
                        if ( null == file )
                        {
                            continue;
                        }
                        //Log.d(TAG, file.getName() );
                        if ( !list.contains(file) )
                        {
                            list.add(file);
                        }
                    }
                    //list.addAll( Arrays.asList(files) );
                }
            }
        }

        /*
        {
            final File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS); // API8
            //Log.d(TAG, dir.getAbsolutePath());

            if ( null != dir )
            {
                final File[] files = dir.listFiles(mFilter);
                if ( null == files )
                {
                    Log.d(TAG, " files null" );
                }
                else
                {
                    for ( final File file : files )
                    {
                        if ( null == file )
                        {
                            continue;
                        }
                        //Log.d(TAG, file.getName() );
                        if ( !list.contains(file) )
                        {
                            list.add(file);
                        }
                    }
                    //list.addAll( Arrays.asList(files) );
                }
            }
        }
        */
        /**/
        {
            if ( 19 <= Build.VERSION.SDK_INT )
            {
                final File[] dirs = context.getExternalFilesDirs(Environment.DIRECTORY_DOWNLOADS);
                if ( null == dirs )
                {
                    Log.d(TAG, " dirs null" );
                }
                else
                {
                    for ( final File dir : dirs )
                    {
                        if ( null == dir )
                        {
                            continue;
                        }
                        //Log.d(TAG, dir.getAbsolutePath());
                        final File[] files = dir.listFiles(filter);
                        if ( null == files )
                        {
                            Log.d(TAG, " files null" );
                        }
                        else
                        {
                            for ( final File file : files )
                            {
                                if ( null == file )
                                {
                                    continue;
                                }
                                //Log.d(TAG, file.getName() );
                                if ( !list.contains(file) )
                                {
                                    list.add(file);
                                }
                            }
                            //list.addAll( Arrays.asList(files) );
                        }
                    }
                }
            }
            else
            {
                final File dir = context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
                if ( null != dir )
                {
                    //Log.d(TAG, dir.getAbsolutePath());
                    final File[] files = dir.listFiles(filter);
                    if ( null == files )
                    {
                        Log.d(TAG, " files null" );
                    }
                    else
                    {
                        for ( final File file : files )
                        {
                            if ( null == file )
                            {
                                continue;
                            }
                            //Log.d(TAG, file.getName() );
                            if ( !list.contains(file) )
                            {
                                list.add(file);
                            }
                        }
                        //list.addAll( Arrays.asList(files) );
                    }
                }
            }
        }
        /**/

        if ( BuildConfig.DEBUG )
        {
            //final File[] files = list.toArray(new File[]{});
            for (final File file : list)
            {
                Log.d(TAG, "  " + file.getPath());
            }
        }
        return list;
    }

}
