package com.steadman.travis.justjava;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends ActionBarActivity {


    int quantity = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        EditText userName = (EditText) findViewById(R.id.person_Name);
        String nameValue = userName.getText().toString();
//        //String words =(quantity * 5) + "You owe this amount";
        CheckBox wCCheckBox = (CheckBox)findViewById(R.id.whipCream);
        Boolean hasWhipCream = wCCheckBox.isChecked();
//        Log.v("Main_Activity","has whip cream: "+hasWhipCream);
        CheckBox chocolateToppings = (CheckBox)findViewById(R.id.Chocolate);
        Boolean hasChocolate = chocolateToppings.isChecked();
//        Log.v("Main_Activity","has chocolate: "+chocolateToppings);
        int price = calculatePrice(hasWhipCream,hasChocolate);
        displayPrice(price);
        String message = createOrderSummary(price,hasWhipCream, hasChocolate,nameValue);
        // new added intent....
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, "just java order for: "+ nameValue);
        intent.putExtra(Intent.EXTRA_TEXT, message);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
        //displayMessage(message);

    }
//    public void composeEmail(String[] addresses, String subject) {
//        Intent intent = new Intent(Intent.ACTION_SENDTO);
//        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
//        intent.putExtra(Intent.EXTRA_EMAIL, addresses);
//        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            startActivity(intent);
//        }
//    }
    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays + & -
     */
    public void increment(View view) {
        quantity = quantity + 1;
        if(quantity == 100){
            Toast.makeText(this,"You can not have more than 100 cups", Toast.LENGTH_SHORT).show();
            return;
        }
        display(quantity);
    }

    public void decrement(View view) {
        quantity = quantity - 1;
        if(quantity == 1){
            Toast.makeText(this,"You can not have less than 1 cups", Toast.LENGTH_SHORT).show();
            return;
        }
        display(quantity);
    }

//    private void displayMessage(String message) {
//        //View orderSummaryTextView = findViewById(R.id.order_summary_text_view);
//        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
//        //orderSummaryTextView.
//        orderSummaryTextView.setText(message);
//    }
    /**
     * Calculates the price of the order.
     *
     */// @param quantity is the number of cups of coffee ordered
     /**/

    private int calculatePrice(boolean hasWhipCream, boolean hasChocolate)
    {
        int cost= 5;
        if(hasWhipCream)
        {
            cost = cost + 1;
        }
        if(hasChocolate)
        {
            cost = cost + 2;
        }
        return quantity*cost;

    }
    private String  createOrderSummary(int price, boolean hasWhipCream, boolean hasChocolate, String nameValue)
    {

        String priceMessage = "Name: "+ nameValue;
        priceMessage +="\n How about some whip cream?"+ hasWhipCream;
        priceMessage += "\n Did you say, chocolate?" + hasChocolate;
        priceMessage +=  "\nQuantity:" + quantity;
        priceMessage += "\nTotal: $" + price;
        priceMessage  += "\nThank YOU!";

        return priceMessage;
    }

    /*    final CheckBox checkBox = (CheckBox) findViewById(R.id.whipCream);
    if (checkBox.isChecked()) {
        checkBox.setChecked(false);
    }*/

}