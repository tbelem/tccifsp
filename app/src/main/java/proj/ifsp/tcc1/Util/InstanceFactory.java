package proj.ifsp.tcc1.Util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Tiago on 18/10/2017.
 */

public final class InstanceFactory {

    private static FirebaseAuth firebaseAuth;
    private static FirebaseDatabase firebaseDatabase;

    public static FirebaseAuth getAuthInstance(){

        if (firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }

        return firebaseAuth;
    }

    public static FirebaseDatabase getDBInstance(){

        if (firebaseDatabase == null){
            firebaseDatabase = FirebaseDatabase.getInstance();
        }

        return firebaseDatabase;
    }

}
