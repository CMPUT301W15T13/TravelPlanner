package persistanceModel;

import persistanceController.DataManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * This is a network Receiver. It listens for the connection changes the connection settings.
 * WHen a change is detected, it will change the internal network status
 * 
 * Based on Meow meo's post on:
 * http://stackoverflow.com/questions/3767591/check-intent-internet-connection
 * @author eorod_000
 *
 */
public class InternetReceiver extends BroadcastReceiver {
	//TODO rewrite it so it does required task


    @Override
    public void onReceive(Context context, Intent intent) {

    	/* Do not delete*/
       // if(isConnected(context)) Toast.makeText(context, "Connected.", Toast.LENGTH_LONG).show();
        //else Toast.makeText(context, "Lost connect.", Toast.LENGTH_LONG).show();
    }
    

    public boolean isConnected(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                              activeNetwork.isConnected();
        return isConnected;
    }

}