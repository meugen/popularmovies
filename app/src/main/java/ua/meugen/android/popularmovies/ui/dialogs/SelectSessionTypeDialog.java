package ua.meugen.android.popularmovies.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.UUID;

import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.ui.helpers.ListenersCollector;
import ua.meugen.android.popularmovies.ui.utils.BundleUtils;


public class SelectSessionTypeDialog extends DialogFragment
        implements DialogInterface.OnClickListener {

    private static final String PARAM_LISTENER_UUID = "listenerUUID";

    public static SelectSessionTypeDialog newInstance(final UUID listenerUUID) {
        final Bundle arguments = new Bundle();
        BundleUtils.putUUID(arguments, PARAM_LISTENER_UUID, listenerUUID);

        final SelectSessionTypeDialog dialog = new SelectSessionTypeDialog();
        dialog.setArguments(arguments);
        return dialog;
    }

    private UUID listenerUUID;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState == null) {
            restoreInstanceState(getArguments());
        } else {
            restoreInstanceState(savedInstanceState);
        }
    }

    private void restoreInstanceState(final Bundle state) {
        listenerUUID = BundleUtils.getUUID(state, PARAM_LISTENER_UUID);
    }

    @Override
    public void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        BundleUtils.putUUID(outState, PARAM_LISTENER_UUID, listenerUUID);
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(final Bundle savedInstanceState) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.select_session_type_dialog_title);
        builder.setItems(R.array.session_types, this);
        builder.setNegativeButton(R.string.button_cancel, this);
        return builder.create();
    }

    @Override
    public void onClick(final DialogInterface dialogInterface, final int which) {
        final ListenersCollector collector = ListenersCollector.from(getActivity());
        final OnSessionTypeSelectedListener listener = collector.retrieveListener(listenerUUID);
        if (listener != null && which == 0) {
            listener.onUserSessionSelected();
        } else if (listener != null && which == 1) {
            listener.onGuestSessionSelected();
        }
    }

    public interface OnSessionTypeSelectedListener {

        void onUserSessionSelected();

        void onGuestSessionSelected();
    }
}
