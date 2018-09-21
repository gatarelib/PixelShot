package com.muddzdev.pixelshot;

import android.Manifest;
import android.graphics.Color;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.GrantPermissionRule;
import android.support.test.runner.AndroidJUnit4;
import android.view.View;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import muddz.pixelshotlibrary.PixelShot;

import static android.view.View.MeasureSpec.EXACTLY;


@RunWith(AndroidJUnit4.class)
public class PixelShotTest {

    @Rule
    public GrantPermissionRule permissionRule = GrantPermissionRule.grant(Manifest.permission.WRITE_EXTERNAL_STORAGE);
    private View testView;

    @Before
    public void setup() {
        testView = generateTestView();
    }


    @Test
    public void testCallbackPathNotNull() {
        PixelShot.of(testView).setResultListener(new PixelShot.PixelShotListener() {
            @Override
            public void onPixelShotSuccess(String path) {
                Assert.assertNotNull("Test path was null", path);
            }

            @Override
            public void onPixelShotFailed() {
            }
        }).save();
        sleepThread();
    }


    @Test
    public void testIfSavedInJPG() {
        PixelShot.of(testView).setResultListener(new PixelShot.PixelShotListener() {
            @Override
            public void onPixelShotSuccess(String path) {
                Assert.assertTrue("Test for saving in JPG failed", path.contains(".jpg"));
            }

            @Override
            public void onPixelShotFailed() {
            }
        }).save();
        sleepThread();
    }

    @Test
    public void testIfSavedInPNG() {
        PixelShot.of(testView).toPNG().setResultListener(new PixelShot.PixelShotListener() {
            @Override
            public void onPixelShotSuccess(String path) {
                Assert.assertTrue("Test for saving in .PNG failed", path.contains(".png"));
            }

            @Override
            public void onPixelShotFailed() {
            }
        }).save();

        sleepThread();
    }

    @Test
    public void testIfSavedInNomedia() {
        PixelShot.of(testView).toNomedia().setResultListener(new PixelShot.PixelShotListener() {
            @Override
            public void onPixelShotSuccess(String path) {
                Assert.assertTrue("Test for saving in .nomedia failed", path.contains(".nomedia"));
            }

            @Override
            public void onPixelShotFailed() {
            }
        }).save();
        sleepThread();
    }


    @Test
    public void testIfDirectoryExist() {
        PixelShot.of(testView).setPath("PixelShotTestDirectory").setResultListener(new PixelShot.PixelShotListener() {
            @Override
            public void onPixelShotSuccess(String path) {
                File file = new File(path);
                File directory = new File(file.getParent());
                boolean isDirectory = directory.exists() && directory.isDirectory();
                Assert.assertTrue(isDirectory);
            }

            @Override
            public void onPixelShotFailed() {

            }
        }).save();
        sleepThread();
    }


    @Test
    public void testIfFileExist() {
        PixelShot.of(testView).setPath("PixelShotTestDirectory").setResultListener(new PixelShot.PixelShotListener() {
            @Override
            public void onPixelShotSuccess(String path) {
                File file = new File(path);
                boolean doFileExists = file.exists() && !file.isDirectory();
                Assert.assertTrue(doFileExists);
            }

            @Override
            public void onPixelShotFailed() {

            }
        }).save();
        sleepThread();
    }

    private View generateTestView() {
        int width = 950;
        int height = 950;

        int widthMS = View.MeasureSpec.makeMeasureSpec(width, EXACTLY);
        int heightMS = View.MeasureSpec.makeMeasureSpec(height, EXACTLY);

        View view = new View(InstrumentationRegistry.getTargetContext());
        view.measure(widthMS, heightMS);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        view.setBackgroundColor(Color.GRAY);

        return view;
    }

    private void sleepThread() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
