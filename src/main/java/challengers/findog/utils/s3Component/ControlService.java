package challengers.findog.utils.s3Component;

import com.amazonaws.services.s3.model.ObjectMetadata;

import java.io.InputStream;

public interface ControlService {
    void uploadFile(InputStream inputStream, ObjectMetadata objectMetadata, String fileName);
    String getFileUrl(String fileName);
    void deleteFile(String fileUrl);
}
