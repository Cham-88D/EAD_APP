package com.ead.fuelpass.nav;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.ead.fuelpass.AddTank;
import com.ead.fuelpass.CreateStationActivity;
import com.ead.fuelpass.CustomerHome;
import com.ead.fuelpass.DashBoardCustomer;
import com.ead.fuelpass.DashBoardOwner;
import com.ead.fuelpass.LoginActivity;
import com.ead.fuelpass.OpenScreen;
import com.ead.fuelpass.OwnerHome;
import com.ead.fuelpass.PasswordReset;
import com.ead.fuelpass.database.DBHelper;

/**
 * Navigation to pages using Intents
 */
public class Navigation {

    private final DBHelper mydb;
    private final Context context;
    private Intent intent;

    public Navigation(Context c) {
        this.context = c;
        this.mydb = new DBHelper(c);
    }


    //check already logged in
    public Intent redirectLogged() {
        if (!mydb.getId().equals("")) {
            if (mydb.getType().equals("customer")) {
                intent = homeCustomer();
            } else if (mydb.getType().equals("owner")) {
                intent = homeOwner();
            }
        }

        return intent;
    }


    //Registration divider nav
    public Intent registration() {
        return new Intent(context, OpenScreen.class);
    }

    //customer home  nav
    public Intent homeCustomer() {
        return new Intent(context, CustomerHome.class);
    }

    //owner home nav
    public Intent homeOwner() {
        return new Intent(context, OwnerHome.class);
    }

    //login nav
    public Intent login() {
        return new Intent(context, LoginActivity.class);
    }

    //password reset nav
    public Intent goToReset() {
        return new Intent(context, PasswordReset.class);
    }

    //owner dashboard nav
    public Intent ownDash(String data) {
        Intent intent = new Intent(context, DashBoardOwner.class);
        intent.putExtra("data", data);
        if(!mydb.getStationId().equals(""))
        {
            mydb.deleteStation(mydb.getStationId());
            mydb.insertStation(data);
        }else{
            mydb.insertStation(data);
        }

        return intent;
    }

    //station list fragment
    public Intent goToHome() {
        return new Intent(context, OwnerHome.class);
    }

    //create station nav
    public Intent createStation() {
        return new Intent(context, CreateStationActivity.class);
    }


    //owner dashboard nav
    public Intent goToDashOw() {
        return new Intent(context, DashBoardOwner.class);
    }


     //create tank
     public Intent goToTankOw() {
         return new Intent(context, AddTank.class);
     }

    //owner dashboard nav without data
    public Intent ownDashS() {
        Intent intent = new Intent(context, DashBoardOwner.class);
        return intent;
    }


    //customer dashboard nav
    public Intent cusDash(String data) {
        Intent intent = new Intent(context, DashBoardCustomer.class);
        intent.putExtra("data", data);
        if(!mydb.getStationId().equals(""))
        {
            mydb.deleteStation(mydb.getStationId());
            mydb.insertStation(data);
        }else{
            mydb.insertStation(data);
        }

        return intent;
    }





}
