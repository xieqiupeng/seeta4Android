package cobb.www.lib_seeta_compare;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

	// Used to load the 'native-lib' library on application startup.
	static {
		System.loadLibrary("native-lib");
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		String str = getString(R.string.action_settings);
		// Example of a call to a native method
		TextView tv = (TextView) findViewById(R.id.sample_text);
		tv.setText(stringFromJNI());

		Button btn = (Button) findViewById(R.id.seeta_face);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				HomeActivity.this.startActivity(new Intent(HomeActivity.this, MainActivity.class));
			}
		});
	}

	/**
	 * A native method that is implemented by the 'native-lib' native library,
	 * which is packaged with this application.
	 */
	public native String stringFromJNI();
}
