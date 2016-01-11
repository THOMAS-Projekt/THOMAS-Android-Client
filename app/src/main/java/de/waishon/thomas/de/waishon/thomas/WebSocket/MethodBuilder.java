package de.waishon.thomas.de.waishon.thomas.WebSocket;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.UUID;

/**
 * Created by Sören on 10.01.2016.
 */
public class MethodBuilder {

    private String action = "callMethod";

    private String methodName;
    private UUID uuid = null;
    private Arguments args = null;

    /**
     * Setzt den Methodennamen dieser Methode
     * @param methodName Der Methodenname
     * @return Diese Klasse
     */
    public MethodBuilder setMethodName(String methodName) {
        this.methodName = methodName;
        return this;
    }

    /**
     * Setzt die eindeutige ReponseID dieser Methode
     * @param uuid eindeutige UUID
     * @return Diese Klasse
     */
    public MethodBuilder setReponseID(UUID uuid) {
        this.uuid = uuid;
        return this;
    }

    /**
     * Setzt die Argumente dieser Methode
     * @param args ArgumentsObjekt mit den Argumenten
     * @return Diese Klasse
     */
    public MethodBuilder setArgs(Arguments args) {
        this.args = args;
        return this;
    }

    /**
     * Gibt die erstellen Parameter als JSON Objekt zurück
     * @return JSON String mit allen Parametern
     */
    public String getJSON() {
        // Objekte definieren
        JSONObject completeObject = new JSONObject();

        try {
            // Aktion hinzufügen
            completeObject.put("action", this.action);

            // Methodennamen hinzufügen
            completeObject.put("methodName", this.methodName);

            // ReponseID hinzufügen
            completeObject.put("responseId", (this.uuid == null) ? "0" : this.uuid.toString());

            // Argumente parsen und hinzufügen
            JSONObject argsObject = new JSONObject();

            if (this.args != null) {
                for (AbstractArgument arg : args.getArgs()) {
                    argsObject.put(arg.getKey(), arg.getValue());
                }
            }

            completeObject.put("args", argsObject);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return completeObject.toString();
    }
}
