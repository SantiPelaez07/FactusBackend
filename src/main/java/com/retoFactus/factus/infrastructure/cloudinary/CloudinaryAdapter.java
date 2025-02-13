package com.retoFactus.factus.infrastructure.cloudinary;

import java.util.Map;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;

public class CloudinaryAdapter {

    private final Cloudinary cloudinary;

    // Configuración de credenciales para cloudinary
    public CloudinaryAdapter(){
        this.cloudinary = new Cloudinary(ObjectUtils.asMap(
            "cloud_name", "dt9wxgpzu",
            "api_key", "625934147415478",
            "api_secret", "FP_vofGflSeKfABayrcH20lWoyE",
            "secure", true
        ));
    }

    public String uploadImage(String filePath){
        try{
            Map uploadResult = cloudinary.uploader().upload(filePath, ObjectUtils.emptyMap());
            return (String) uploadResult.get("secure_url");
        } catch (Exception e) {
            throw new RuntimeException("Error al cargar la imágen a Cloudinary: ", e);
        }
    }
}
