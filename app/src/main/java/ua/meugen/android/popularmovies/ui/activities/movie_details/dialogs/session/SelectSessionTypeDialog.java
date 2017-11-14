package ua.meugen.android.popularmovies.ui.activities.movie_details.dialogs.session;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import java.util.UUID;

import javax.inject.Inject;

import dagger.android.support.AndroidSupportInjection;
import ua.meugen.android.popularmovies.R;
import ua.meugen.android.popularmovies.ui.activities.base.fragment.BaseDialogFragment;
import ua.meugen.android.popularmovies.ui.utils.BundleUtils;


public class SelectSessionTypeDialog extends DialogFragment
        implements DialogInterface.OnClickListener {

    @Inject OnSessionTypeSelectedListener listener;

    @Override
    public void onAttach(final Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
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
    public void onClick(
            final DialogInterface dialogInterface,
            final int which) {
        if (which == 0) {
            listener.onUserSessionSelected();
        } else if (which == 1) {
            listener.onGuestSessionSelected();
        }
    }

}
