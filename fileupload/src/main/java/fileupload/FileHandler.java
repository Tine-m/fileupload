package fileupload;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileHandler {

    private final String IMAGE_PATH = "src/main/resources/static/img/uploaded-images";

    public void uploadAsPath(MultipartFile file){
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            saveFile(IMAGE_PATH,fileName,file);
            //saveFilePathInDatabase(); //put transaction handling around these 2 transactions!
        }
        catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    public void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
        Path uploadPath = Paths.get(uploadDir);
        if (!Files.exists(uploadPath)) {
            Files.createDirectories(uploadPath);
        }

        try (InputStream inputStream = multipartFile.getInputStream()) {
            Path filePath = uploadPath.resolve(fileName);
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ioe) {
            throw new IOException("Could not save image file: " + fileName, ioe);
        }
    }

    public File[] getAllImages(){
        File folder = new File(IMAGE_PATH);
        return folder.listFiles();
    }

    /* public static void saveFilePathInDatabase() {
           Insert path into database
            PreparedStatement ps = establishConnection().prepareStatement("insert into my_company.imgPath (description, path) VALUES(?,?)");
            ps.setString(1,fileName);
            ps.setString(2, "description");
            ps.execute();
    } */
}
