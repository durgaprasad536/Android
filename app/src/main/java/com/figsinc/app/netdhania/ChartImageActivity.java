package com.figsinc.app.netdhania;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.ImageView;

public class ChartImageActivity extends FragmentActivity {

	@Override
	protected void onCreate(@Nullable Bundle arg0) {
		super.onCreate(arg0);

		byte[] byteArrayExtra = getIntent().getByteArrayExtra("image");
		Bitmap bitmap = BitmapFactory.decodeByteArray(byteArrayExtra, 0, byteArrayExtra.length);

		ImageView imageView = new ImageView(this);
		imageView.setImageBitmap(bitmap);
		setContentView(imageView);
	}

}
