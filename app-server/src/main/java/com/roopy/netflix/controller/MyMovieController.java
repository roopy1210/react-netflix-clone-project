package com.roopy.netflix.controller;

import com.roopy.dto.MovieDTO;
import com.roopy.dto.UserDTO;
import com.roopy.entity.FavoriteMovie;
import com.roopy.entity.FavoriteMovieIdentity;
import com.roopy.netflix.service.MyMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@RestController
@RequestMapping("/mymovie")
public class MyMovieController {

    private static final Logger logger = LoggerFactory.getLogger(MyMovieController.class);

    @Autowired
    private MyMovieService myMovieService;

    @PostMapping("/likes")
    public ResponseEntity<FavoriteMovie> likeMovie(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , UserDTO userDTO
            , @RequestBody MovieDTO movieDTO) {

        FavoriteMovieIdentity favoriteMovieIdentity = new FavoriteMovieIdentity(userDTO.getUserId(), movieDTO.getId());
        FavoriteMovie favoriteMovie = FavoriteMovie.builder().favoriteMovieIdentity(favoriteMovieIdentity)
                .title(movieDTO.getTitle())
                .img(movieDTO.getImg()).build();

        FavoriteMovie retObj = myMovieService.addOrRemoveLikeMovie(favoriteMovie);

        return new ResponseEntity<>(retObj, HttpStatus.OK);
    }

    @PostMapping("/favorite/movies")
    public ResponseEntity<List<FavoriteMovie>> favoriteMovies(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , UserDTO userDTO) {

        List<FavoriteMovie> favoriteMovieList = myMovieService.findFavoriteMoviesByUserId(userDTO);

        return new ResponseEntity<>(favoriteMovieList, HttpStatus.OK);
    }

    @PostMapping("/favorite/movie/{movieId}")
    public ResponseEntity<List<FavoriteMovie>> deleteFavoriteMovie(HttpServletRequest httpServletRequest
            , HttpServletResponse httpServletResponse
            , @PathVariable Long movieId
            , UserDTO userDTO) {

        FavoriteMovieIdentity favoriteMovieIdentity = new FavoriteMovieIdentity(userDTO.getUserId(), movieId);
        FavoriteMovie favoriteMovie = new FavoriteMovie();
        favoriteMovie.setFavoriteMovieIdentity(favoriteMovieIdentity);

        myMovieService.addOrRemoveLikeMovie(favoriteMovie);

        List<FavoriteMovie> favoriteMovieList = myMovieService.findFavoriteMoviesByUserId(userDTO);

        return new ResponseEntity<>(favoriteMovieList, HttpStatus.OK);
    }


}
