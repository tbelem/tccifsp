package proj.ifsp.tcc1.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.TaskStackBuilder;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import proj.ifsp.tcc1.Controller.LoginActivity;
import proj.ifsp.tcc1.R;
import proj.ifsp.tcc1.Util.DateConverter;
import proj.ifsp.tcc1.Util.InstanceFactory;

/**
 * Created by Tiago on 25/10/2017.
 */

public class NotificationService extends Service {

    private DatabaseReference pendentesReference;
    private ChildEventListener listenerNovoAdded;

    public static boolean running = false;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        pendentesReference.removeEventListener(listenerNovoAdded);
        running = false;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        if(!running) {
            pendentesReference = InstanceFactory.getDBInstance().getReference("usuarios")
                    .child(InstanceFactory.getAuthInstance().getCurrentUser().getUid()).child("pendentes");
            montaListenerNovoAdded();
            pendentesReference.orderByValue().startAt(DateConverter.sysdateToTimestamp()).addChildEventListener(listenerNovoAdded);

            running = true;
        }

        return START_STICKY;
    }

    private void montaListenerNovoAdded(){

        listenerNovoAdded = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                notificacaoNovoPendente();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("DATABASE ERROR",databaseError.toString());
            }
        };

    }

    public void notificacaoNovoPendente(){

        String appname = getResources().getString(R.string.app_name);

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.drawable.ic_cifrao)
                        .setContentTitle(appname)
                        .setContentText("Você recebeu um novo questionário")
                        .setAutoCancel(true);

        Intent resultIntent = new Intent(this, LoginActivity.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);

        stackBuilder.addParentStack(LoginActivity.class);

        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        0,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );
        mBuilder.setContentIntent(resultPendingIntent);
        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        mNotificationManager.notify(1, mBuilder.build());
    }
}
