package tutu.ru.hotelfinder;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Looper;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.support.test.uiautomator.By;
import android.support.test.uiautomator.UiDevice;
import android.support.test.uiautomator.UiSelector;
import android.support.test.uiautomator.Until;
import android.view.KeyEvent;

import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;

@RunWith(AndroidJUnit4.class)
public class UiInstrumentationTest {
    private String packageName;

    @Test
    public void useAppContext() throws Exception {
        final UiDevice mDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation());
        mDevice.pressHome();

        final String launcherPackage = mDevice.getLauncherPackageName();
        assertNotNull(launcherPackage);
        mDevice.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), 10_000);

        final Context context = InstrumentationRegistry.getContext();
        packageName = "tutu.ru.hotelfinder";
        final Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);

        mDevice.wait(Until.hasObject(By.pkg(packageName).depth(0)), 10_000);
        sleep(4000);
        mDevice.findObject(new UiSelector().resourceId("tutu.ru.hotelfinder:id/search_button")).click();
        sleep(1000);
        Looper.prepare();
        final ClipboardManager clipboard = (ClipboardManager) InstrumentationRegistry.getTargetContext().getSystemService(Context.CLIPBOARD_SERVICE);
        clipboard.setPrimaryClip(ClipData.newHtmlText("simple text", "Питер", "Питер"));
        mDevice.pressKeyCode(KeyEvent.KEYCODE_V, KeyEvent.META_CTRL_ON);
        sleep(2000);
        mDevice.pressEnter();
        sleep(7000);
        mDevice.findObject(new UiSelector().resourceId("tutu.ru.hotelfinder:id/list_hotel_title")).click();
        sleep(7000);
        mDevice.pressBack();
        sleep(3000);
    }

    private void sleep(int timeMs) {
        try {
            Thread.sleep(timeMs);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
