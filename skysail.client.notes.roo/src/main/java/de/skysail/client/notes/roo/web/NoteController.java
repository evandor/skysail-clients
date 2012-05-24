package de.skysail.client.notes.roo.web;

import de.skysail.client.notes.roo.Note;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/notes")
@Controller
@RooWebScaffold(path = "notes", formBackingObject = Note.class)
public class NoteController {
}
