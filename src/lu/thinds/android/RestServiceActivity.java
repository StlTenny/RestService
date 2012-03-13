package lu.thinds.android;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class RestServiceActivity extends Activity{
	TextView t_query1 = null;
	RestService restService;
	
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.testrest);
        
        restService = new RestService(mHandler, this, "http://10.0.2.2:3000/bars/search");
        restService.addParam("lat", "40764917");
        restService.addParam("lng", "-73983130");
        restService.addParam("range", "10");
        
        t_query1 = (TextView) findViewById(R.id.t_query1);
        t_query1.setMovementMethod(new ScrollingMovementMethod());
        
        Button b_query1 = (Button) findViewById(R.id.b_query1);
    	b_query1.setOnClickListener(new View.OnClickListener(){
    		public void onClick(View view){
    			try {
					restService.execute(RestService.GET);
				} catch (Exception e) {
					e.printStackTrace();
				}
    		}
    	});
    	
    }
    
    private final Handler mHandler = new Handler(){
    	@Override
    	public void handleMessage(Message msg){
    			t_query1.setText((String) msg.obj);
    		}		
    };

}

