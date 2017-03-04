package com.example.mananpatel.lao;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int quantity = 2;
    String selection = "None Selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void submitOrder(View view) {
        EditText text = (EditText) findViewById(R.id.name_text_view);
        String nameEntered = text.getText().toString();

        EditText userEntry = (EditText) findViewById(R.id.contribution_text_view);
        String contribution = userEntry.getText().toString();
        double value = Double.parseDouble(contribution);

        EditText refreshText = (EditText) findViewById(R.id.refreshments_text_view);
        String refreshments = refreshText.getText().toString();

        EditText addInfo = (EditText) findViewById(R.id.description_text_view);
        String info = addInfo.getText().toString();

        String message = createOrderSummary(value, selection, nameEntered, refreshments, info);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "Lao Order from " + nameEntered);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public String createOrderSummary(double value, String temp, String name, String refreshments, String info)
    {
        String priceMessage = "Name: " + name;
        priceMessage += "\nCoffee or Tea? " + temp;
        priceMessage += "\nAdditional Info: " + info;
        priceMessage += "\nQuantity: " + quantity;
        priceMessage += "\nRefreshments: " + refreshments;
        priceMessage += "\nContribution: $" + value;
        priceMessage += "\nThank you! I appreciate it!";
        return priceMessage;
    }

    public void increment(View view){
        if(quantity == 100)
        {
            Toast.makeText(this, "You cannot have more than 100 items!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity+1;
        display(quantity);

    }

    public void decrement(View view){
        if(quantity == 1) {
            Toast.makeText(this, "You must have atleast 1 items!", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.coffee:
                if (checked)
                    selection = "coffee";
                break;
            case R.id.tea:
                if (checked)
                    selection = "Tea";
                break;
        }
    }

    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }
}
