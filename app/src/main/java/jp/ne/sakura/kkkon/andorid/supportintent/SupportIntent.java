package jp.ne.sakura.kkkon.andorid.supportintent;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

public class SupportIntent
{
    private static final String TAG = "SupportIntent";

    public static final String DEFAULT_PLAYSTORE_PACKAGENAME = "com.android.vending";
    public static final String DEFAULT_PLAYSTORE_CLASSNAME = "com.google.android.finsky.activities.MainActivity";

    public static void startActivity( final Activity activity, final Intent intent )
    {
        try
        {
            activity.startActivity(intent);
        }
        catch ( ActivityNotFoundException e )
        {
            Log.d( TAG, "", e );
            Toast.makeText(activity.getApplicationContext(), "Not found", Toast.LENGTH_SHORT).show();
        }
    }

    public static void launch(final Activity activity, final String url, final String packageName, final String defaultClassName )
    {
        final Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));

        final PackageManager pm = activity.getPackageManager();
        if ( null == pm )
        {
            return;
        }

        {
            final ComponentName componentName = intent.resolveActivity(pm);
            if (null != componentName)
            {
                Log.d( TAG, "componentName=" + componentName );
                if (0 == packageName.compareTo(componentName.getPackageName()))
                {
                    startActivity( activity, intent );
                    return;
                }
            }
        }

        {
            final String activityClassName = findActivityClassName( intent, pm, packageName );
            final String className = (null!=activityClassName)?(activityClassName):(defaultClassName);

            intent.setComponent( new ComponentName(packageName,className) );

            startActivity( activity, intent );
        }
    }


    public static String findActivityClassName( final Intent intent, final PackageManager pm, final String packageName )
    {
        String result = null;
        {
            final List<ResolveInfo> list = pm.queryIntentActivities( intent, 0 );
            if ( null != list )
            {
                for ( final ResolveInfo info : list )
                {
                    Log.d( TAG, "info=" + info );
                    if ( null != info.activityInfo )
                    {
                        Log.d(TAG, "  info.activityInfo.packageName=" + info.activityInfo.packageName);
                        Log.d(TAG, "  info.activityInfo.name=" + info.activityInfo.name);
                    }
                }
                for ( final ResolveInfo info : list )
                {
                    if ( null != info.activityInfo )
                    {
                        if ( 0 == packageName.compareTo(info.activityInfo.packageName) )
                        {
                            result = info.activityInfo.name;
                            break;
                        }
                    }
                }
            }
        }

        return result;
    }
}
