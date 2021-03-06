package hub.brainee.masterfirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class SomeActivity extends AppCompatActivity {

    private static final String TAG = "SomeActivity";
    private static final String KEY_TITLE = "title";
    private static final String KEY_DESCRIPTION = "description";

    private EditText title, description, eTxtpriority;
    private FirebaseFirestore firestore;

    private Button btnSave, btnLoad, btnUpdateDesc, btnDelDesc, btnDelNote;

    private TextView titleTxt, descTxt;

    private CollectionReference collectionReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some);

        title = findViewById(R.id.title);
        description = findViewById(R.id.desc);
        btnSave = findViewById(R.id.btnSave);
        btnLoad = findViewById(R.id.loadBtn);
        titleTxt = findViewById(R.id.txtData);

        eTxtpriority = findViewById(R.id.priority);
        // descTxt = findViewById(R.id.txtDesc);

        /*btnUpdateDesc = findViewById(R.id.btnUpdateDesc);
        
        btnDelDesc = findViewById(R.id.btnDelDesc);
        btnDelNote = findViewById(R.id.btnDelNote);*/

        firestore = FirebaseFirestore.getInstance();
        collectionReference = firestore.collection("Notebook");

       /* btnDelNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delNote();
            }
        });

        btnDelDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // delDescription();
            }
        });

        btnUpdateDesc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // updateDesc();
            }
        });
*/
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        btnLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadData();
            }
        });
    }

   /* private void delNote() {
        firestore.collection("Notebook").document("My First Note")
                .delete();
    }

    private void delDescription() {
        firestore.collection("Notebook").document("My First Note")
                .update(KEY_DESCRIPTION, FieldValue.delete());
    }

    private void updateDesc() {

        String upDesc = description.getText().toString().trim();
        firestore.collection("Notebook").document("My First Note")
                .update(KEY_DESCRIPTION, upDesc);
    }*/

    @Override
    protected void onStart() {
        super.onStart();

        collectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                if (error != null) {
                    return;
                }

                String data = "";

                for (QueryDocumentSnapshot snapshot : value) {

                    Note note = snapshot.toObject(Note.class);
                    note.setDocumentId(snapshot.getId());

                    String documentId = note.getDocumentId();
                    String title = note.getTitle();
                    String description = note.getDescription();

                    int priority = note.getPriority();

                    data += "ID: " + documentId + "\nTitle: " + title + "\n" + "Description: " + description + "\n" +
                            "Priority: " + priority + "\n\n\n";

                }

                titleTxt.setText(data);
            }
        });
       /* firestore.collection("Notebook").document("My First Note")
                .addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (null != error) {
                            Toast.makeText(SomeActivity.this, "Error while loading", Toast.LENGTH_SHORT).show();
                        }

                        if (value.exists()) {

                            Note note = value.toObject(Note.class);

                            String titleText = note.getTitle();
                            String descText = note.getDescription();

                            titleTxt.setText("Title: " + titleText);
                            descTxt.setText("Description: " + descText);
                        } else {
                            titleTxt.setText("");
                            descTxt.setText("");
                        }
                    }
                });*/
    }


    private void loadData() {
        collectionReference
                .whereGreaterThanOrEqualTo("priority",2)
                .orderBy("priority", Query.Direction.DESCENDING)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {

                        String data = "";

                        for (QueryDocumentSnapshot snapshot : queryDocumentSnapshots) {

                            Note note = snapshot.toObject(Note.class);
                            note.setDocumentId(snapshot.getId());

                            String documentId = note.getDocumentId();
                            String title = note.getTitle();
                            String description = note.getDescription();
                            int priority = note.getPriority();

                            data += "ID: " + documentId + "\nTitle: " + title + "\n" + "Description: " + description + "\n" +
                                    "Priority: " + priority + "\n\n\n";

                        }

                        titleTxt.setText(data);
                    }
                });
       /* firestore.collection("Notebook").document("My First Note")
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot snapshot) {

                        if (snapshot.exists()) {

                           *//* String titleText = snapshot.getString(KEY_TITLE);
                            String descText = snapshot.getString(KEY_DESCRIPTION);
*//*
                            Note note = snapshot.toObject(Note.class);

                            String titleText = note.getTitle();
                            String descText = note.getDescription();

                            titleTxt.setText("Title: " + titleText);
                            descTxt.setText("Description: " + descText);
                        } else {
                            Toast.makeText(SomeActivity.this, "Document does not exist", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(SomeActivity.this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/
    }

    private void saveData() {
        String titleEt = title.getText().toString().trim();
        String descEt = description.getText().toString().trim();

        if (eTxtpriority.length() == 0) {
            eTxtpriority.setText("0");
        }

        int priority = Integer.parseInt(eTxtpriority.getText().toString().trim());

        /*Note class in place*/
        Note note = new Note(titleEt, descEt, priority);

        /* Saving multiple data collection*/
        collectionReference.add(note);

        /*Map<String, Object> note = new HashMap<>();
        note.put(KEY_TITLE, titleEt);
        note.put(KEY_DESCRIPTION, descEt);*/



        /* Saving/Adding Single data collection*/
        /*firestore.collection("Notebook").document("My First Note")
                .set(note)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        Toast.makeText(SomeActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(SomeActivity.this, "Error: " + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });*/


    }
}