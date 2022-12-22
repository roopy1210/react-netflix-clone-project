package com.roopy.netflix.service;

import com.roopy.dto.UserDTO;
import com.roopy.entity.FavoriteMovie;

import java.util.List;

public interface MyMovieService {

    FavoriteMovie addOrRemoveLikeMovie(FavoriteMovie favoriteMovie);

    List<FavoriteMovie> findFavoriteMoviesByUserId(UserDTO user);
}
