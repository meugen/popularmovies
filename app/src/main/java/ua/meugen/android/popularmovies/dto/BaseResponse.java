package ua.meugen.android.popularmovies.dto;

import android.os.Parcel;
import android.util.JsonReader;

import java.io.IOException;

/**
 * @author meugen
 */

public class BaseResponse {

    private String statusMessage = "";
    private int statusCode = 0;
    private boolean success = true;

    public String getStatusMessage() {
        return statusMessage;
    }

    public void setStatusMessage(final String statusMessage) {
        this.statusMessage = statusMessage;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(final int statusCode) {
        this.statusCode = statusCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(final boolean success) {
        this.success = success;
    }

    final void _writeToParcel(final Parcel parcel) {
        parcel.writeString(statusMessage);
        parcel.writeInt(statusCode);
        parcel.writeBooleanArray(new boolean[] { success });
    }

    final void _readFromParcel(final Parcel parcel) {
        statusMessage = parcel.readString();
        statusCode = parcel.readInt();
        final boolean[] values = new boolean[1];
        parcel.readBooleanArray(values);
        success = values[0];
    }

    final void _readFromJson(final JsonReader reader, final String name) throws IOException {
        if ("status_message".equals(name)) {
            statusMessage = reader.nextString();
        } else if ("status_code".equals(name)) {
            statusCode = reader.nextInt();
        } else if ("success".equals(name)) {
            success = reader.nextBoolean();
        } else {
            reader.skipValue();
        }
    }
}
