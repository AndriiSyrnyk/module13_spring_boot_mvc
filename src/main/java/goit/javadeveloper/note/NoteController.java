package goit.javadeveloper.note;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

@RequiredArgsConstructor
@RequestMapping("/note")
@Controller
public class NoteController {
    private final NoteService noteService;

    @GetMapping("/list")
    public ModelAndView getAllNotes() {
        ModelAndView result = new ModelAndView("note/list");
        result.addObject("noteList", noteService.getAll());
        return result;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getNoteById(@PathVariable("id") long id) {
        ModelAndView result = new ModelAndView("note/edit");
        result.addObject("noteById", noteService.getById(id));
        return result;
    }

    @PostMapping("/edit/{id}")
    public RedirectView updateById (@PathVariable("id") long id,
                                    @RequestParam("title") String title,
                                    @RequestParam("content") String content) {
        Note note = noteService.getById(id);
        note.setTitle(title);
        note.setContent(content);
        return new RedirectView("/note/list");
    }

    @GetMapping("/add")
    public ModelAndView addNote() {
        ModelAndView result = new ModelAndView("note/add");
        return result;
    }

    @PostMapping("/add")
    public RedirectView addNote(@RequestParam("title") String title,
                                @RequestParam("content") String content) {
        noteService.add(new Note(title, content));
        return new RedirectView("/note/list");
    }

    @PostMapping("/delete/{id}")
    public RedirectView deleteNote(@PathVariable("id") long id) {
        noteService.deleteById(id);
        return new RedirectView("/note/list");
    }
}
