package com.fundy.application.devnote.out;

import com.fundy.application.devnote.out.command.SaveDevNoteCommand;


public interface SaveDevNotePort {
    Long saveDevNote(SaveDevNoteCommand command);
}
