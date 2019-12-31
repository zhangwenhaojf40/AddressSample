package zz.hao.myapplication;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class StreamTools {

	public static String getStringFromStream(String fileName, Context context) {
		String body = "";

		try {
			AssetManager manager = context.getAssets();
			InputStream is = manager.open(fileName);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int len = -1;
			byte[] buf = new byte[1024];
			while ((len = is.read(buf)) != -1) {
				baos.write(buf, 0, len);
			}
			body = baos.toString();
			// 因为这是写到内存中 关不关无所谓
			baos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return body;
	}

}
