package com.example.app4autism;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class FingerPainterView extends View {

    private Context context;
    private Canvas canvas;
    private Paint paint;
    private Bitmap bitmap;
    private Path path;
    private Uri uri;
    public FingerPainterView(Context context) {
        super(context); // application context
        init(context);
    }

    public FingerPainterView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FingerPainterView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context);
    }

    private void init(Context context) {
        this.context = context;

        path = new Path();
        paint = new Paint();

        // default brush style and colour
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(20);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setARGB(255,0,0,0);
    }

    //Method to reset Canvas by redrawing it again
    public void resetCanvas() {
        draw(canvas);
    }

    public void setBrush(Paint.Cap brush) {
        paint.setStrokeCap(brush);
    }

    public Paint.Cap getBrush() {
        return paint.getStrokeCap();
    }

    public void setBrushWidth(int width) {
        paint.setStrokeWidth(width);
    }

    public int getBrushWidth() {
        return (int) paint.getStrokeWidth();
    }

    public void setColour(int colour) {
        paint.setColor(colour);
    }

    public int getColour() {
        return paint.getColor();
    }

    public void load(Uri uri) {
        this.uri = uri;
    }

    //Method to save image to Gallery
    public void save()
    {
        saveImage();
    }


    @Override
    public Parcelable onSaveInstanceState() {
        Bundle bundle = new Bundle();
        // save superclass view state
        bundle.putParcelable("superState", super.onSaveInstanceState());

        try {
            // save bitmap to temporary cache file to overcome binder transaction size limit
            File f = File.createTempFile("fingerpaint", ".png", context.getCacheDir());
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(f));
            // save temporary filename to bundle
            bundle.putString("tempfile", f.getAbsolutePath());
        } catch(IOException e) {
            Log.e("FingerPainterView", e.toString());
        } catch(Exception e) {
            Log.e("FingerPainterView", e.toString());
        }
        return bundle;
    }

    @Override
    public void onRestoreInstanceState(Parcelable state) {
        if (state instanceof Bundle) {
            Bundle bundle = (Bundle) state;

            try {
                // load cache file from bundle stored filename
                File f = new File(bundle.getString("tempfile"));
                Bitmap b = BitmapFactory.decodeStream(new FileInputStream(f));
                // need to copy the bitmap to create a mutable version
                bitmap = b.copy(b.getConfig(), true);
                b.recycle();
                f.delete();
            } catch(IOException e) {
                Log.e("FingerPainterView", e.toString());
            }

            state = bundle.getParcelable("superState");
        }
        super.onRestoreInstanceState(state);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // canvas is white with a bitmap with alpha channel drawn over the top
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap, 0, 0, paint);
        // show current drawing path
        canvas.drawPath(path, paint);

        getParent().requestDisallowInterceptTouchEvent(true);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        // called after the activity has been created when the view is inflated
        if(bitmap==null) {
            if(uri!=null) {
                try {
                    // attempt to load the uri provided, scale to fit our canvas
                    InputStream stream = context.getContentResolver().openInputStream(uri);
                    Bitmap bm = BitmapFactory.decodeStream(stream);
                    bitmap  = Bitmap.createScaledBitmap(bm, Math.max(w, h), Math.max(w, h), false);
                    stream.close();
                    bm.recycle();
                } catch(IOException e) {
                    Log.e("FingerPainterView", e.toString());
                }
            }
            else {
                // create a square bitmap so is drawable even after rotation to landscape
                bitmap = Bitmap.createBitmap(Math.max(w,h), Math.max(w,h), Bitmap.Config.ARGB_8888);
            }
        }
        canvas = new Canvas(bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.reset();
                path.moveTo(x, y);
                path.lineTo(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                canvas.drawPath(path, paint);
                path.reset();
                invalidate();
                break;
        }
        return true;
    }

    //Method to save image to Gallery
    public void saveImage()
    {
        //Obtain bitmap based on current drawing
        Bitmap bitmap_to_save = bitmap;

        //create a new bitmap to get white background
        Bitmap newBitmap = Bitmap.createBitmap(bitmap_to_save.getWidth(),
                bitmap_to_save.getHeight(),bitmap_to_save.getConfig());
        Canvas canvas = new Canvas(newBitmap);
        canvas.drawColor(Color.WHITE);
        canvas.drawBitmap(bitmap_to_save,0,0,null);

        //Get the path for external storage
        String path = Environment.getExternalStorageDirectory().getPath();
        //create a new directory if there is no folder existed
        File file_directory = new File(path + "/FingerPainterViewFile");
        if(!file_directory.exists())
        {
            file_directory.mkdirs();
        }

        //Save the file as JPEG file
        File output = new File(file_directory,"tempfile.jpg");
        OutputStream os = null;

        try{
            os = new FileOutputStream(output);
            newBitmap.compress(Bitmap.CompressFormat.JPEG,100,os);
            os.flush();
            os.close();

            //scan the image and add it to gallery
            MediaScannerConnection.scanFile(context, new String[] { output.toString() }, null,
                    new MediaScannerConnection.OnScanCompletedListener() {
                        public void onScanCompleted(String path, Uri uri) {
                            Log.d("appname", "Image is saved in gallery and gallery is refreshed.");
                        }
                    }
            );
        } catch (Exception e){}
    }
}
