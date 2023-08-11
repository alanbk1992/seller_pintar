package project.kimora.sellerpintar.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;
import android.util.SparseArray;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.signature.ObjectKey;
import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.face.Face;
import com.google.android.gms.vision.face.FaceDetector;
import project.kimora.sellerpintar.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Calendar;

/**
 * Created by msinfo on 13/02/23.
 */

public class ImageProcessor {

    public static void loadImage(Context context, ImageView imageView, String url) {
        Log.d("loadImage", url);
        RequestOptions options = new RequestOptions()
                .placeholder(R.drawable.no_image)
                .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
                .signature(new ObjectKey(Calendar.getInstance().get(Calendar.HOUR_OF_DAY)));
        Glide.with(context).load(url).apply(options).into(imageView);
    }

    public static String saveImageToFile(Context context, Bitmap image, String fileName, String filePath) {
        String savedImagePath = null;
        String path = Environment.getExternalStorageDirectory() + filePath;
        File storageDir = new File(path);
        boolean success = true;
        if (!storageDir.exists()) {
            success = storageDir.mkdirs();
        }
        if (success) {
            File imageFile = new File(storageDir, fileName);
            savedImagePath = imageFile.getAbsolutePath();
            try {
                OutputStream fOut = new FileOutputStream(imageFile);
                image.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return path+"/"+fileName;
    }

   // public static Bitmap getCompressedImage(Context context, String path) {
       // Bitmap bitmap = null;
       // try {
           // File file = new File(path);
            //bitmap = new Compressor(context).setMaxWidth(640)
                    //.setMaxHeight(480).setQuality(50).compressToBitmap(file);
      //  } catch (IOException e) {e.printStackTrace();}
       // return bitmap;
   // }

   // public static Bitmap getCustomCompressedImage(Context context, String path, int maxWidth, int maxHeight, int quality) {
       // Bitmap bitmap = null;
      //  try {
            //File file = new File(path);
            //bitmap = new Compressor(context).setMaxWidth(maxWidth)
                   // .setMaxHeight(maxHeight).setQuality(quality).compressToBitmap(file);
       // } catch (IOException e) {e.printStackTrace();}
       // return bitmap;
   // }

    public static byte[] bitmapToBytes(Bitmap bmp) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();

        return byteArray;
    }

    public static boolean isFaceDetected(Context context, Bitmap bitmap) {
        FaceDetector detector = new FaceDetector.Builder(context)
                .setMode(FaceDetector.ACCURATE_MODE)
                .setTrackingEnabled(false)
                .setLandmarkType(FaceDetector.ALL_LANDMARKS)
                .build();
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<Face> faces = detector.detect(frame);
        detector.release();
        return faces.size() > 0;
    }
}
