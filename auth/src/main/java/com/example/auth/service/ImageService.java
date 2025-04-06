package com.example.auth.service;

import com.example.auth.dto.image.ImageCreateDto;
import com.example.auth.model.ImageEntity;
import com.example.auth.model.RestaurantEntity;
import com.example.auth.repository.ImageRepository;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    private final ImageRepository imageRepository;
    private final RestaurantService restaurantService;
    private final CloudinaryService cloudinaryService;

    public ImageService(
            ImageRepository imageRepository,
            RestaurantService restaurantService,
            CloudinaryService cloudinaryService
    ) {
        this.imageRepository = imageRepository;
        this.restaurantService = restaurantService;
        this.cloudinaryService = cloudinaryService;
    }

    public ImageEntity uploadNewImageToRestaurant(long restaurantId, ImageCreateDto imageCreateDto) throws FileUploadException {

        if (imageCreateDto.getFile().isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        RestaurantEntity restaurant = restaurantService.getRestaurantById(restaurantId);

        ImageEntity image = new ImageEntity();
        image.setRestaurant(restaurant);
        image.setUrl(cloudinaryService.uploadFile(imageCreateDto.getFile()));

        return imageRepository.save(image);
    }

}
