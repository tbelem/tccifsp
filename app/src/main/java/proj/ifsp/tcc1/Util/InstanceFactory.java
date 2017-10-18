package proj.ifsp.tcc1.Util;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Tiago on 18/10/2017.
 */

public final class InstanceFactory {

    private static FirebaseAuth firebaseAuth;

    public static FirebaseAuth getAuthInstance(){

        if (firebaseAuth == null){
            firebaseAuth = FirebaseAuth.getInstance();
        }

        return firebaseAuth;
    }

}
