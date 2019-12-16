package firstdream.rm;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.main.activities.PantallaPrincipal;
import com.main.res;
import com.main.webServerCon.loginConn;

import java.util.concurrent.ExecutionException;




public class Login extends Activity {


    Login l;
    TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void autenticar(View view){

        EditText user = (EditText) findViewById(R.id.user);
        EditText pass = (EditText) findViewById(R.id.pass);
        result = (TextView) findViewById(R.id.result);
        String username = user.getText().toString();
        String password = pass.getText().toString();

        if(username == null || username.isEmpty() || password == null || password.isEmpty()){
            result.setText(R.string.errorAlAutenticar);}
        else {
            l = this;
            loginConn login = new loginConn(res.IP,"8080",username,password);

            try {
                if (login.authenticate()){
                    result.setTextColor(Color.GREEN);
                    result.setText(R.string.autenticacionCorrecta);

                   Intent launch = new Intent(l, PantallaPrincipal.class);
                    launch.putExtra(String.valueOf(R.string.user),username);
                    startActivity(launch);

                }
                else{

                    result.setTextColor(Color.RED);
                    result.setText(R.string.errorAlAutenticar);
                }
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


        }




    }

    public void prueba (View view){

    }
}
