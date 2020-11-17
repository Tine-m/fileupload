package fileupload;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;

@Controller
public class FileUploadController {

    private FileHandler fh = new FileHandler();

    @GetMapping(value = "/")
    public String displayForm() {
        return "upload";
    }

    @PostMapping("/upload")
    public String upload(@RequestParam("file") MultipartFile file, Model model) {
        model.addAttribute("file", file);
        fh = new FileHandler();
        fh.uploadAsPath(file);
        return "redirect:/show";
    }
    @GetMapping("/show")
    public String show(Model model) {
        File[] images = fh.getAllImages();
        String[] names = new String[images.length];
        for (int i = 0; i < images.length; i++) {
             names[i] = images[i].getName();
        }
        model.addAttribute("names", names);
        return "show";
    }
}
