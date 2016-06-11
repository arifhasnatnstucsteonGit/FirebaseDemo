package currentlocatio.android.arifhasnat.firebasedemo.Activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import currentlocatio.android.arifhasnat.firebasedemo.Constants.Config;
import currentlocatio.android.arifhasnat.firebasedemo.Models.Person;
import currentlocatio.android.arifhasnat.firebasedemo.R;

public class MainActivity extends AppCompatActivity {


    EditText editTextName,editTextAdress;
    Button buttonSave;
    TextView textViewDisplay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Firebase.setAndroidContext(this);


        editTextName = (EditText) findViewById(R.id.editTextName);
        editTextAdress = (EditText) findViewById(R.id.editTextAddress);
        buttonSave = (Button) findViewById(R.id.buttonSave);
        textViewDisplay = (TextView) findViewById(R.id.textViewDisplay);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Firebase firebase= new Firebase(Config.FireBase_url);

                String name = editTextName.getText().toString();
                String address= editTextAdress.getText().toString();

                Person person = new Person();
                person.name = name;
                person.address = address;

                firebase.child("Person").setValue(person);

                firebase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot data : dataSnapshot.getChildren()) {
                            Person person = data.getValue(Person.class);

                            String stringValue = "Name : " + person.name + "\nAddress : " + person.address + "\n\n";
                            textViewDisplay.setText(stringValue);
                        }
                    }

                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                        System.out.print("The read Fail "+ firebaseError.getMessage());

                    }
                });

            }
        });





    }


}
