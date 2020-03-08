package com.mustafayigit.noteit.ui.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.mustafayigit.noteit.R;
import com.mustafayigit.noteit.db.entity.Note;
import com.mustafayigit.noteit.viewmodel.NoteViewModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 */
public class UpdateNoteFragment extends Fragment {

    EditText txtUpdateNoteHeader, txtUpdateNoteContent;
    RatingBar updateRatingBar;
    String noteRememberDate;
    Button btnUpdateNote;
    Calendar myCalendar;
    Switch updateSwitch;

    private NoteViewModel noteViewModel;

    public UpdateNoteFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_update_note, container, false);

        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        txtUpdateNoteHeader = root.findViewById(R.id.txtUpdateNoteHeader);
        txtUpdateNoteContent = root.findViewById(R.id.txtUpdateNoteContent);
        updateRatingBar = root.findViewById(R.id.updateRatingBar);
        btnUpdateNote = root.findViewById(R.id.btnUpdateNote);
        updateSwitch = root.findViewById(R.id.updateSwitchRemember);

        txtUpdateNoteHeader.setText(getArguments().getString("noteHeader"));
        txtUpdateNoteContent.setText(getArguments().getString("noteContent"));
        updateRatingBar.setRating(getArguments().getInt("notePriority"));
        noteRememberDate = getArguments().getString("noteRememberDate");

        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            }

        };

        updateSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    DatePickerDialog dialog = new DatePickerDialog(getContext(), date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH));
                    dialog.getDatePicker().setMinDate(myCalendar.getTimeInMillis());
                    dialog.show();


                }
            }
        });

        btnUpdateNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(txtUpdateNoteHeader.getText().toString().trim()) && !TextUtils.isEmpty(txtUpdateNoteContent.getText().toString().trim())) {
                    Note updated = new Note(txtUpdateNoteHeader.getText().toString(), txtUpdateNoteContent.getText().toString(), (int) (updateRatingBar.getRating() * 2), getDateByFormat(myCalendar.getTime()), true);
                    updated.setNoteID(getArguments().getInt("noteID"));
                    noteViewModel.update(updated);
                    Toast.makeText(v.getContext(), R.string.toast_message_note_updated, Toast.LENGTH_SHORT).show();
                    NavController navController = Navigation.findNavController(getView());
                    navController.navigate(R.id.action_navigation_update_note_to_navigation_home);
                }
            }
        });



        return root;
    }


    private String getDateByFormat(Date notFormattedDate) {
        String myFormat = "dd/MM/yy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
        return sdf.format(notFormattedDate);
    }
}
