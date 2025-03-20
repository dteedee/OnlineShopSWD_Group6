/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.UUID;
/**
 *
 * @author Tien Dung
 */
public class CloudinaryService {
    private Cloudinary cloudinary;

    public CloudinaryService() throws ServletException {
        try {
            cloudinary = new Cloudinary(ObjectUtils.asMap(
                    "cloud_name", "dzutsz5ym",
                    "api_key", "717311866718988",
                    "api_secret", "Bgrn_HbsmA6Vv7x4CWq_JZmGnpo",
                    "secure", "true"
            ));
        } catch (Exception e) {
            throw new ServletException("Không thể khởi tạo Cloudinary: " + e.getMessage(), e);
        }
    }

    public String uploadImage(Part filePart) throws IOException {
        if (filePart == null || filePart.getSize() <= 0) {
            return null;
        }

        try (InputStream inputStream = filePart.getInputStream()) {
            byte[] fileBytes = inputStream.readAllBytes();

            Map<String, Object> uploadResult = cloudinary.uploader().upload(
                    fileBytes,
                    ObjectUtils.asMap(
                            "folder", "products",
                            "public_id", UUID.randomUUID().toString(),
                            "resource_type", "auto"
                    )
            );

            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            throw new IOException("Lỗi khi upload ảnh lên Cloudinary: " + e.getMessage(), e);
        }
    }
}
