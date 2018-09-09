package jp.ne.sakura.kkkon.andorid.supportintent.support;

import org.junit.Test;

import static org.junit.Assert.*;

public class SupportUtilsTest {

    @Test
    public void makeURLfromText_redeem()
    {
        {
            final String result = SupportUtils.makeURLfromText(
                    "https://play.google.com/redeem?code="
                    , "abc"
            );
            assertEquals("https://play.google.com/redeem?code=abc", result);
        }
        {
            final String result = SupportUtils.makeURLfromText(
                    "https://play.google.com/redeem?code="
                    , "https://play.google.com/redeem?code=abc"
            );
            assertEquals("https://play.google.com/redeem?code=abc", result);
        }
        {
            final String result = SupportUtils.makeURLfromText(
                    "https://play.google.com/redeem?code="
                    , "http://play.google.com/redeem?code=abc"
            );
            assertEquals("https://play.google.com/redeem?code=abc", result);
        }
        {
            final String result = SupportUtils.makeURLfromText(
                    "http://play.google.com/redeem?code="
                    , "https://play.google.com/redeem?code=abc"
            );
            assertEquals("http://play.google.com/redeem?code=abc", result);
        }
    }

    @Test
    public void makeURLfromText_apps()
    {
        {
            final String result = SupportUtils.makeURLfromText(
                    "https://play.google.com/apps/"
                    , "testing/com.example"
            );
            assertEquals("https://play.google.com/apps/testing/com.example", result);
        }
        {
            final String result = SupportUtils.makeURLfromText(
                    "https://play.google.com/apps/"
                    , "https://play.google.com/apps/testing/com.example"
            );
            assertEquals("https://play.google.com/apps/testing/com.example", result);
        }
        {
            final String result = SupportUtils.makeURLfromText(
                    "https://play.google.com/apps/"
                    , "http://play.google.com/apps/testing/com.example"
            );
            assertEquals("https://play.google.com/apps/testing/com.example", result);
        }
        {
            final String result = SupportUtils.makeURLfromText(
                    "http://play.google.com/apps/"
                    , "https://play.google.com/apps/testing/com.example"
            );
            assertEquals("http://play.google.com/apps/testing/com.example", result);
        }

    }


}
