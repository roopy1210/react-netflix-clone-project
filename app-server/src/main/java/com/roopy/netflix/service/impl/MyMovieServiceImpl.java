package com.roopy.netflix.service.impl;

import com.roopy.dto.UserDTO;
import com.roopy.entity.FavoriteMovie;
import com.roopy.netflix.repository.MyMovieRepository;
import com.roopy.netflix.service.MyMovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class MyMovieServiceImpl implements MyMovieService {

    private static final Logger logger = LoggerFactory.getLogger(MyMovieService.class);

    @Autowired
    private MyMovieRepository myMovieRepository;

    @Transactional
    @Override
    public FavoriteMovie addOrRemoveLikeMovie(FavoriteMovie favoriteMovie) {

        Optional<FavoriteMovie> oldFavoriteMovie = myMovieRepository.findById(favoriteMovie.getFavoriteMovieIdentity());

        if (oldFavoriteMovie.isPresent()) {
            myMovieRepository.deleteById(favoriteMovie.getFavoriteMovieIdentity());
        }
        else {
            myMovieRepository.save(favoriteMovie);
        }

        return favoriteMovie    ;
    }

    @Override
    public List<FavoriteMovie> findFavoriteMoviesByUserId(UserDTO user) {
        return myMovieRepository.findFavoriteMovies(user.getUserId());
    }
}
