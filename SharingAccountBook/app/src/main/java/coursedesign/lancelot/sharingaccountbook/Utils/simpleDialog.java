package coursedesign.lancelot.sharingaccountbook.Utils;

import android.app.AlertDialog;
import android.content.Context;
import android.widget.Toast;

import coursedesign.lancelot.sharingaccountbook.R;

import static android.widget.Toast.LENGTH_SHORT;


public class simpleDialog {

    public static void show(Context ctx,String title,String msg ){
        AlertDialog alert = new AlertDialog.Builder(ctx).create();
        alert.setIcon(R.drawable.warning);
        alert.setTitle(title);
        alert.setMessage(msg);
        alert.show();
    }
}
