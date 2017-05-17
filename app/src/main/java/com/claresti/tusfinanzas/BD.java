package com.claresti.tusfinanzas;

        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;

public class BD extends SQLiteOpenHelper{

    public static final int DataBaseVersion = 1;
    public static final String DataBaseName = "TusFinanzas.db";

    public BD(Context context) {
        super(context, DataBaseName, null, DataBaseVersion);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String tablaUsuario = "CREATE TABLE Usuario(" +
                "usu_id TEXT NOT NULL PRIMARY KEY," +
                "usu_nombre TEXT DEFAULT NULL," +
                "usu_apellidos TEXT DEFAULT NULL," +
                "usu_mail TEXT NOT NULL," +
                "usu_password TEXT NOT NULL," +
                "usu_premium TEXT NOT NULL," +
                "usu_moneda TEXT DEFAULT NULL" +
                ")";

        String tablaImg = "CREATE TABLE Img(" +
                "usuImg TEXT)";

        //Crear tablas
        db.execSQL(tablaUsuario);
        db.execSQL(tablaImg);

        //ObjUsuario
        ContentValues v = new ContentValues();
        v.put("usu_id", "0");
        v.put("usu_nombre", "0");
        v.put("usu_apellidos", "0");
        v.put("usu_mail", "0");
        v.put("usu_password", "0");
        v.put("usu_premium", "0");
        v.put("usu_moneda", "0");
        db.insert("Usuario", null, v);

        ContentValues v2 = new ContentValues();
        v2.put("usuImg", "0");
        db.insert("img", null, v2);
    }

    public String updateUsuarioPrimera(Usuario usuario){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("usu_id", usuario.getUsu_id());
            v.put("usu_nombre", usuario.getUsu_nombre());
            v.put("usu_apellidos", usuario.getUsu_apellidos());
            v.put("usu_mail", usuario.getUsu_mail());
            v.put("usu_password", usuario.getUsu_password());
            v.put("usu_premium", usuario.getUsu_premium());
            v.put("usu_moneda", usuario.getUsu_moneda());
            db.update("Usuario", v, "usu_id = 0", null);
            return "1";
        }catch(Exception e){
            return e.toString();
        }
    }

    public String updateUsuario(Usuario usuario){
        try{
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues v = new ContentValues();
            v.put("usu_id", "0");
            v.put("usu_nombre", usuario.getUsu_nombre());
            v.put("usu_apellidos", usuario.getUsu_apellidos());
            v.put("usu_mail", usuario.getUsu_mail());
            v.put("usu_password", usuario.getUsu_password());
            v.put("usu_premium", usuario.getUsu_premium());
            v.put("usu_moneda", usuario.getUsu_moneda());
            db.update("Usuario", v, "usu_id = " + usuario.getUsu_id(), null);
            return "1";
        }catch(Exception e){
            return e.toString();
        }
    }

    public Usuario selectUsuario(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query("Usuario", null, null, null, null, null, null);
        if(cursor.moveToFirst()){
            Usuario usuario = new Usuario(
                    cursor.getString(cursor.getColumnIndex("usu_id")),
                    cursor.getString(cursor.getColumnIndex("usu_nombre")),
                    cursor.getString(cursor.getColumnIndex("usu_apellidos")),
                    cursor.getString(cursor.getColumnIndex("usu_mail")),
                    cursor.getString(cursor.getColumnIndex("usu_password")),
                    cursor.getString(cursor.getColumnIndex("usu_premium")),
                    cursor.getString(cursor.getColumnIndex("usu_moneda")));
            return usuario;
        }
        return null;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
