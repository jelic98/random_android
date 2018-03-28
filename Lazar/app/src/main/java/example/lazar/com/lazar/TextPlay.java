package example.lazar.com.lazar;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ToggleButton;
import java.util.Random;

/**
 * Created by Lazar on 02.01.2015.
 */
public class TextPlay extends Activity implements View.OnClickListener {

    EditText input;
    ToggleButton passTog;
    TextView display;
    Button chkCmd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.text);

        baconAndEggs();

        passTog.setOnClickListener(this);

        chkCmd.setOnClickListener(this);

    }

    private void baconAndEggs() {
        chkCmd = (Button) findViewById(R.id.bResults);
        passTog = (ToggleButton) findViewById(R.id.tbPassword);
        input = (EditText) findViewById(R.id.etCommands);
        display = (TextView) findViewById(R.id.tvResults);
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.bResults:

                String check = input.getText().toString();

                display.setText(check);

                if (check.contentEquals("left")){
                    display.setGravity(Gravity.LEFT);
                }

                else if(check.contentEquals("center")){
                    display.setGravity(Gravity.CENTER);
                }

                else if (check.contentEquals("right")){
                    display.setGravity(Gravity.RIGHT);
                }

                else if (check.contentEquals("blue")){
                    display.setTextColor(Color.BLUE);
                }

                else if (check.contentEquals("red")){
                    display.setTextColor(Color.RED);
                }

                else if (check.contentEquals("green")){
                    display.setTextColor(Color.GREEN);
                }

                else if (check.contains("WTF")){
                    Random crazy = new Random();
                    display.setText("Where is The Food!?");
                    display.setTextSize(crazy.nextInt(75));
                    display.setTextColor(Color.rgb(crazy.nextInt(256),crazy.nextInt(256),crazy.nextInt(256)));

                    switch(crazy.nextInt(3)){
                        case 0:
                            display.setGravity(Gravity.LEFT);
                            break;
                        case 1:
                            display.setGravity(Gravity.CENTER);
                            break;
                        case 2:
                            display.setGravity(Gravity.RIGHT);
                            break;
                    }
                }

                else {
                    display.setText("invalid");
                    display.setGravity(Gravity.CENTER);
                    display.setTextSize(35);
                    display.setTextColor(Color.BLACK);
                }

                break;
            case R.id.tbPassword:

                if (passTog.isChecked()){
                    input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                else {
                    input.setInputType(InputType.TYPE_CLASS_TEXT);
                }

                break;
        }
    }
}
