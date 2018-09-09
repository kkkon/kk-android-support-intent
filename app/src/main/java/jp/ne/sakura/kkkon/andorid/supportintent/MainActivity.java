package jp.ne.sakura.kkkon.andorid.supportintent;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.support.v7.widget.AppCompatTextView;

import java.io.File;
import java.util.ArrayList;

import jp.ne.sakura.kkkon.andorid.supportintent.support.SupportGoogleRedeem;
import jp.ne.sakura.kkkon.andorid.supportintent.support.SupportGoogleStoreApps;
import jp.ne.sakura.kkkon.andorid.supportintent.support.SupportUtils;

public class MainActivity
    extends AppCompatActivity
    implements ViewGroup.OnClickListener, ListView.OnItemClickListener
{
    private static final String TAG = "SupportActivity";

    Context             mContextApplication = null;

    View                    mCurrentView = null;

    static enum EnumMode
    {
        None,
        GoogleStoreApps,
        GoogleRedeem,
    }
    EnumMode                mCurrentMode = EnumMode.None;

    LinearLayoutCompat      mLayoutSelectMode = null;
    AppCompatTextView       mModeTextViewMode = null;
    AppCompatTextView       mModeTextViewFile = null;
    Button                  mModeButtonGoogleStoreApps = null;
    Button                  mModeButtonGoogleRedeem = null;

    LinearLayoutCompat      mLayoutSelectFile = null;
    AppCompatTextView       mFileTextViewMode = null;
    AppCompatTextView       mFileTextViewFile = null;
    ArrayList<File>         mFileArray = new ArrayList<File>(){};
    ArrayAdapter<File>      mFileAdapter = null;
    ListView                mFileListView = null;

    LinearLayoutCompat      mLayoutSelectItem = null;
    AppCompatTextView       mItemTextViewMode = null;
    AppCompatTextView       mItemTextViewFile = null;
    ArrayList<String>       mItemArray = new ArrayList<String>(){};
    ArrayAdapter<String>    mItemAdapter = null;
    ListView                mItemListView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        mContextApplication = this.getApplicationContext();

        final AppCompatActivity activity = this;
        {
            final LinearLayoutCompat layout = new LinearLayoutCompat(activity);
            layout.setOrientation( LinearLayoutCompat.VERTICAL );
            layout.setLayoutParams(
                    new LinearLayoutCompat.LayoutParams(
                            LinearLayoutCompat.LayoutParams.MATCH_PARENT
                            , LinearLayoutCompat.LayoutParams.MATCH_PARENT
                    )
            );

            {
                final AppCompatTextView text = new AppCompatTextView(activity);
                //text.setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
                text.setText("not select mode");
                text.setTextSize( 24 );

                mModeTextViewMode = text;
                layout.addView(mModeTextViewMode);
            }
            {
                final AppCompatTextView text = new AppCompatTextView(activity);
                //text.setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
                text.setText("not select file\n");

                mModeTextViewFile = text;
                layout.addView(mModeTextViewFile);
            }

            {
                final Button button = new Button(activity);
                //button.setTextAlignment(Button.TEXT_ALIGNMENT_CENTER);
                button.setText("Google Store Apps");
                button.setOnClickListener( this );
                if ( 14 <= Build.VERSION.SDK_INT )
                {
                    button.setAllCaps(false);
                }

                mModeButtonGoogleStoreApps = button;
                layout.addView(button);
            }
            {
                final Button button = new Button(activity);
                //button.setTextAlignment(Button.TEXT_ALIGNMENT_CENTER);
                button.setText("Google Redeem");
                button.setOnClickListener( this );
                if ( 14 <= Build.VERSION.SDK_INT )
                {
                    button.setAllCaps(false);
                }

                mModeButtonGoogleRedeem = button;
                layout.addView(button);
            }

            mLayoutSelectMode = layout;
        }

        {
            final LinearLayoutCompat layout = new LinearLayoutCompat(activity);
            layout.setOrientation( LinearLayoutCompat.VERTICAL );
            layout.setLayoutParams(
                    new LinearLayoutCompat.LayoutParams(
                            LinearLayoutCompat.LayoutParams.MATCH_PARENT
                            , LinearLayoutCompat.LayoutParams.MATCH_PARENT
                    )
            );

            {
                final AppCompatTextView text = new AppCompatTextView(activity);
                //text.setTextAlignment(TextView.TEXT_ALIGNMENT_CENTER);
                text.setText("not select mode");
                text.setTextSize( 24 );

                mFileTextViewMode = text;
                layout.addView(mFileTextViewMode);
            }
            {
                final AppCompatTextView text = new AppCompatTextView(activity);
                //text.setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
                text.setText("not select file\n");

                mFileTextViewFile = text;
                layout.addView(mFileTextViewFile);
            }
            {
                mFileAdapter = new ArrayAdapter<File>(activity, android.R.layout.simple_list_item_1, mFileArray );

                final ListView listView = new ListView(activity);
                listView.setAdapter( mFileAdapter );
                listView.setOnItemClickListener( this );

                mFileListView = listView;
                layout.addView( mFileListView );
            }

            mLayoutSelectFile = layout;
        }

        {
            final LinearLayoutCompat layout = new LinearLayoutCompat(activity);
            layout.setOrientation( LinearLayoutCompat.VERTICAL );
            layout.setLayoutParams(
                    new LinearLayoutCompat.LayoutParams(
                            LinearLayoutCompat.LayoutParams.MATCH_PARENT
                            , LinearLayoutCompat.LayoutParams.MATCH_PARENT
                    )
            );

            {
                final AppCompatTextView text = new AppCompatTextView(activity);
                //text.setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
                text.setText("not select mode");
                text.setTextSize( 24 );

                mItemTextViewMode = text;
                layout.addView(mItemTextViewMode);
            }
            {
                final AppCompatTextView text = new AppCompatTextView(activity);
                //text.setTextAlignment(TextView.TEXT_ALIGNMENT_TEXT_START);
                text.setText("not select file\n");

                mItemTextViewFile = text;
                layout.addView(mItemTextViewFile);
            }
            {
                mItemAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_list_item_1, mItemArray );

                final ListView listView = new ListView(activity);
                listView.setAdapter( mItemAdapter );
                listView.setOnItemClickListener( this );

                mItemListView = listView;
                layout.addView( mItemListView );
            }

            mLayoutSelectItem = layout;
        }

        mCurrentView = mLayoutSelectMode;
        this.setContentView( mCurrentView );

        /**/
        if ( 23 <= Build.VERSION.SDK_INT )
        {
            if ( PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE ) )
            {
                //Log.d( TAG, "no READ_EXTERNAL_STORAGE" );
                if ( ActivityCompat.shouldShowRequestPermissionRationale( this, Manifest.permission.READ_EXTERNAL_STORAGE) )
                {
                }
                else
                {
                    ActivityCompat.requestPermissions( this,
                            new String[] { Manifest.permission.READ_EXTERNAL_STORAGE }
                            , 1
                            );
                }
            }
        }
        /**/
    }

    @Override
    protected void onDestroy()
    {
        if ( null != mItemListView )
        {
            mItemListView.setOnItemClickListener( null );
        }
        if ( null != mFileListView )
        {
            mFileListView.setOnItemClickListener( null );
        }
        if ( null != mModeButtonGoogleRedeem )
        {
            mModeButtonGoogleRedeem.setOnClickListener(null);
        }
        if ( null != mModeButtonGoogleStoreApps )
        {
            mModeButtonGoogleStoreApps.setOnClickListener(null);
        }

        if ( null != mCurrentView )
        {
            final ViewGroup parent = (ViewGroup)mCurrentView.getParent();
            parent.removeAllViews();
        }

        super.onDestroy();
    }

    @Override
    public void onBackPressed() // API5
    {
        if ( mLayoutSelectMode == mCurrentView )
        {
            //Dialog
            super.onBackPressed();
        }
        else
        if ( mLayoutSelectFile == mCurrentView )
        {
            mCurrentMode = EnumMode.None;
            mCurrentView = mLayoutSelectMode;
            mItemTextViewMode.setText("not select mode");
            this.setContentView( mCurrentView );
        }
        else
        if ( mLayoutSelectItem == mCurrentView )
        {
            mCurrentView = mLayoutSelectFile;
            mFileTextViewFile.setText("not select file\n");
            this.setContentView( mCurrentView );
        }
    }


    @Override
    public void onClick(View view)
    {
        if ( mLayoutSelectMode == mCurrentView )
        {
            if ( mModeButtonGoogleStoreApps == view )
            {
                mCurrentMode = EnumMode.GoogleStoreApps;

                final SupportGoogleStoreApps support = new SupportGoogleStoreApps();
                mFileArray = support.list( this.getApplicationContext() );
                mFileAdapter.clear();
                if ( 11 <= Build.VERSION.SDK_INT )
                {
                    mFileAdapter.addAll( mFileArray );
                }
                else
                {
                    for ( final File item : mFileArray )
                    {
                        mFileAdapter.add( item );
                    }
                }
                mFileAdapter.notifyDataSetChanged();
                mFileListView.invalidateViews();


                mFileTextViewMode.setText("Google Store Apps");

                mCurrentView = mLayoutSelectFile;
                this.setContentView( mCurrentView );
            }
            else
            if ( mModeButtonGoogleRedeem == view )
            {
                mCurrentMode = EnumMode.GoogleRedeem;

                final SupportGoogleRedeem support = new SupportGoogleRedeem();
                mFileArray = support.list( this.getApplicationContext() );
                mFileAdapter.clear();
                if ( 11 <= Build.VERSION.SDK_INT )
                {
                    mFileAdapter.addAll( mFileArray );
                }
                else
                {
                    for ( final File item : mFileArray )
                    {
                        mFileAdapter.add( item );
                    }
                }
                mFileAdapter.notifyDataSetChanged();
                mFileListView.invalidateViews();


                mFileTextViewMode.setText("Google Redeem");

                mCurrentView = mLayoutSelectFile;
                this.setContentView( mCurrentView );
            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id)
    {
        if ( mLayoutSelectFile == mCurrentView )
        {
            if ( mFileListView == parent )
            {
                mItemTextViewMode.setText(mFileTextViewMode.getText());

                final File file = mFileArray.get( position );
                mItemTextViewFile.setText(file.getParent() + "\n" + file.getName());

                switch ( mCurrentMode )
                {
                    case GoogleStoreApps:
                        {
                            mItemArray = SupportUtils.readFile( file );
                        }
                        break;
                    case GoogleRedeem:
                        {
                            mItemArray = SupportUtils.readFile( file );
                        }
                        break;
                }
                mItemAdapter.clear();
                if ( 11 <= Build.VERSION.SDK_INT )
                {
                    mItemAdapter.addAll( mItemArray );
                }
                else
                {
                    for ( final String item : mItemArray )
                    {
                        mItemAdapter.add( item );
                    }
                }
                mItemAdapter.notifyDataSetChanged();
                mItemListView.invalidateViews();

                mCurrentView = mLayoutSelectItem;
                this.setContentView( mCurrentView );
            }
        }
        else
        if ( mLayoutSelectItem == mCurrentView )
        {
            if ( mItemListView == parent )
            {
                final String text = mItemArray.get( position );

                switch ( mCurrentMode )
                {
                    case GoogleStoreApps:
                        {
                            final String url = SupportGoogleStoreApps.makeURLfromText(text);
                            SupportIntent.launch( this, url, SupportIntent.DEFAULT_PLAYSTORE_PACKAGENAME, SupportIntent.DEFAULT_PLAYSTORE_CLASSNAME );
                        }
                        break;
                    case GoogleRedeem:
                        {
                            final String url = SupportGoogleRedeem.makeURLfromText(text);
                            SupportIntent.launch( this, url, SupportIntent.DEFAULT_PLAYSTORE_PACKAGENAME, SupportIntent.DEFAULT_PLAYSTORE_CLASSNAME );
                        }
                        break;
                }
            }
        }
    }

}
