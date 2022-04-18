package com.example.myapplication.checkin_guest.viewModel.Executor;

import com.example.myapplication.checkin_guest.callback.FireStoreExcutorListener;
import com.example.myapplication.checkin_guest.data.FStoreDatabaseAttribute;
import com.google.firebase.firestore.FirebaseFirestore;


public class FireStoreExcutor {
    private final String TAG = FireStoreExcutor.class.getSimpleName();
    private FireStoreExcutorListener mListner;
    private FirebaseFirestore db;
    private FStoreDatabaseAttribute attribute;

    public FireStoreExcutor() {
        db = FirebaseFirestore.getInstance();
        attribute = FStoreDatabaseAttribute.getInstance();
    }

    public void setmListner(FireStoreExcutorListener fireStoreExcutorListener) {
        this.mListner = fireStoreExcutorListener;
    }

    public void getBanner() {
        this.db.collection(attribute.getBANNER_COLLECTION())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    mListner.onSuccessGetBanner(queryDocumentSnapshots);
                });
    }
}