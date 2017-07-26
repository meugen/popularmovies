package ua.meugen.android.popularmovies.model.responses;

import android.os.Parcel;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final BaseResponse that = (BaseResponse) o;

        if (statusCode != that.statusCode) return false;
        if (success != that.success) return false;
        return statusMessage != null ? statusMessage.equals(that.statusMessage) : that.statusMessage == null;

    }

    @Override
    public int hashCode() {
        int result = statusMessage != null ? statusMessage.hashCode() : 0;
        result = 31 * result + statusCode;
        result = 31 * result + (success ? 1 : 0);
        return result;
    }
}
