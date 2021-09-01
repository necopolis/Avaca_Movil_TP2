package com.avaca.avaca_tp_2;

import android.annotation.SuppressLint;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.IBinder;
import android.provider.Telephony;
import android.util.Log;

public class ServisMensajes extends Service {
    private Thread tarea;
    public ServisMensajes() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }


    //
    @SuppressLint("Range")
    @Override
    // AQUI EN EL STARTCOMAND ES DONDE SE GENERA EL PEDIDO DE RECURSOS Y COMIENZA LA VIDA DEL HILO
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("mensaje","Paso por aca 03");
        // SE DECLARO EL HILO DE FORMA ANONIMA
        tarea = new Thread(new Runnable() {
            @Override
            public void run() {
                //-----LUGAR SIMIL BS DONDE SE ALMACENA LOS SMS-------
                Uri mensajes = Uri.parse("content://sms/inbox");
                //----CURSOR QUE VA APUNTAR Y RECORRER ESTA SIMIL BD-----
                ContentResolver cr = getContentResolver();
                //-----HACEMOS INFINITO EL WHILE---------
                while (true) {
                    Cursor c = cr.query(mensajes, null, null, null, "date desc");
                    String celular = null;
                    String mensaje = null;
                    if (c != null && c.getCount() > 0) {
                        int i = 1;
                        while (c.moveToNext() && i < 6) {
                            celular = c.getString((c.getColumnIndex(Telephony.Sms.ADDRESS)));
                            mensaje = c.getString(c.getColumnIndex(Telephony.Sms.BODY));
                            Log.d("mensajes", "Celular: "+celular + " SMS: " + mensaje);
                            Log.d("mensajes", "Paso por aca");
                            i++;
                        }
                        try {
                            //---DORMIMOS EL HILO 9 SEGUNDOS Y VOLVEMOS A LEER
                            Thread.sleep(9000);
                        } catch (InterruptedException e) {
                            Log.d("mensaje", e.getMessage());
                            //e.printStackTrace();
                            break;
                        }
                    }
                    //---CERRAMOS EL CURSOR
                    c.close();
                }
            }
        });
        // RUNEABLE Y RUNNING
        // ACA SE PONER EN EJECUCION EL HILO
        tarea.start();
        return START_STICKY;
    }
}
