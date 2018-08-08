package com.example.mivi.miviapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;


public class MainActivity extends AppCompatActivity {

    TextView txt_name,txt_price,txt_databalnce,txt_expirydate,txt_paymenttype;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_name = findViewById(R.id.txt_name);
        txt_price = findViewById(R.id.txt_price);
        txt_databalnce = findViewById(R.id.txt_databalnce);
        txt_expirydate = findViewById(R.id.txt_expirydate);
        txt_paymenttype = findViewById(R.id.txt_paymenttype);

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset()); // Consume Json
            JSONObject data  = obj.getJSONObject("data");
            String type = data.getString("type");
            String id = data.getString("id");
            JSONObject attributes  = data.getJSONObject("attributes");
            String paymenttype = attributes.getString("payment-type");
            txt_paymenttype.setText(paymenttype);
            String unbilledcharges = attributes.getString("unbilled-charges");
            String nextbillingdate = attributes.getString("next-billing-date");
            String title = attributes.getString("title");
            String firstname = attributes.getString("first-name");
            String lastname = attributes.getString("last-name");
            String dateofbirth = attributes.getString("date-of-birth");
            String contactnumber = attributes.getString("contact-number");
            String emailaddress = attributes.getString("email-address");
            String emailaddressverified = attributes.getString("email-address-verified");
            String emailsubscriptionstatus = attributes.getString("email-subscription-status");
            JSONObject links = data.getJSONObject("links");
            String self = links.getString("self");
            JSONObject relationships = data.getJSONObject("relationships");
            JSONObject services = relationships.getJSONObject("services");
            JSONObject links1 = services.getJSONObject("links");
            String related = links1.getString("related");
            JSONArray included  = obj.getJSONArray("included");
            for(int i=0; i<included.length(); i++)
            {
                String type2 = included.getJSONObject(i).getString("type").toString();
                String idnew = included.getJSONObject(i).getString("id").toString();
                JSONObject attributesNew = included.getJSONObject(i).getJSONObject("attributes");
                if (type2.equalsIgnoreCase("services")) {
                    String msn = attributesNew.getString("msn");
                    String credit = attributesNew.getString("credit");
                    String creditexpiry = attributesNew.getString("credit-expiry");
                    String datausagethreshold = attributesNew.getString("data-usage-threshold");
                }
                if (type2.equalsIgnoreCase("subscriptions")) {

                    String includeddatabalance = attributesNew.getString("included-data-balance");
                    txt_databalnce.setText(includeddatabalance);
                    String expirydate = attributesNew.getString("expiry-date");
                    txt_expirydate.setText(expirydate);
                }
                if (type2.equalsIgnoreCase("products")) {
                    String name = attributesNew.getString("name");
                    txt_name.setText(name);
                    String includeddata = attributesNew.getString("included-data");
                    String includedcredit = attributesNew.getString("included-credit");
                    String includedinternationaltalk = attributesNew.getString("included-international-talk");
                    String unlimitedtext = attributesNew.getString("unlimited-text");
                    String unlimitedinternationaltext = attributesNew.getString("unlimited-international-text");
                    String price = attributesNew.getString("price");
                    txt_price.setText(price);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = getAssets().open("collection.json");// Consume Json
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
